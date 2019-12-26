package it.marco.stone.service.stone;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.inject.Inject;

import org.apache.tika.Tika;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import it.marco.marco.cloud.configuration.properties.StoneProperties;
import it.marco.marco.cloud.session.Session_infos;
import it.marco.marco.service.log.LogService;
import it.marco.marco.utils.utilities.ImageUtils;
import it.marco.marco.utils.utilities.LogUtils;
import it.marco.stone.bean.stone.Stone_inspection_image;
import it.marco.stone.dao.stone.Stone_inspection_image_DAO;

@Service
public class ImageServiceImpl implements ImageService {

	@Inject
	private InspectionHelperService inspectionHelperService;
	@Inject
	private LogService logService;
	
	@Inject
	private Stone_inspection_image_DAO stone_inspection_image_DAO;
	
	@Inject
	private Session_infos session_infos;

	@Inject
	private StoneProperties stoneProps;
	
	// hashmap contenente le coppie chiave valore relative all'uuid della head e il numero di immagini relative
	private ConcurrentHashMap<String, Integer> images_map;
	
	@PostConstruct
	public void init() {
		images_map = new ConcurrentHashMap<String, Integer>();
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Stone_inspection_image> uploadImages(List<Stone_inspection_image> images) {

		String method_name = new Throwable().getStackTrace()[0].getMethodName();
		
		StringBuilder images_saved_id = new StringBuilder(128);
		StringBuilder response = new StringBuilder(128);
		
		// stampa le immagini in formato json
		String json_images = new Gson().toJson(images);
		logService.printAndSaveStoneLog(LogUtils.INFO, json_images, this.getClass().getName(), method_name, "", "Immagini = " + json_images);
		
		// controlla se le immagini sono già presenti nel db
		inspectionHelperService.checkAreImagesOnDb(images, json_images);
		
		String file_name = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_S").format(session_infos.getTime_st());
		
		// seriale della coppia picture/sketch analizzata usata per identificare l'i-esima immagine
		int count_image = 0;
		// inizializzo il vettore delle immagini salvate nel db
		List<Stone_inspection_image> images_saved = new ArrayList<Stone_inspection_image>();
		
		for (Stone_inspection_image image : images) {
			
			image.setInsertTime(session_infos.getTime_st()); // uniformo il time dell'inserimento per tutte le immagini
			count_image++; // aggiorno il contatore del seriale dell'immagine
			
			byte[] picture = image.getPicture();
			if (picture != null) { // se il picture è popolato
				
				if (this.processImage(image, picture, ImageUtils.PICTURE, file_name, count_image, response)) {
					
					byte[] sketch = image.getSketch();
					if (sketch != null) { // se lo sketch è popolato
						if (this.processImage(image, sketch, ImageUtils.SKETCH, file_name, count_image, response)) {
							
							// avendo sia il picture che lo sketch posso fare la loro sovrapposizione
							try {
								byte[] overlay = this.mergeImages(stoneProps.getFile_path_base() + image.getPicture_path(), stoneProps.getFile_path_base() + image.getSketch_path());
								if (!this.processImage(image, overlay, ImageUtils.OVERLAY, file_name, count_image, response)) {
									continue;
								}
								
							} catch (IOException e) {
								
								// rimuovo la coppia chiave valore dalla map
								images_map.remove(image.getStoneInspectionHeadUuid());
								
								String error_response = "Errore nel salvataggio o nella creazione dell'overlay tra picture " + image.getPicture_path() + " e sketch " + image.getSketch_path();
								logService.printAndSaveStoneLog(LogUtils.ERROR, error_response, this.getClass().getName(), method_name, error_response + " - " + e.getMessage(), "");
								
								continue;
							}
						} else {

							// rimuovo la coppia chiave valore dalla map
							images_map.remove(image.getStoneInspectionHeadUuid());
						}
					}
				} else {

					// rimuovo la coppia chiave valore dalla map
					images_map.remove(image.getStoneInspectionHeadUuid());
				}
			}
			
			Stone_inspection_image image_saved = stone_inspection_image_DAO.save(image);
			images_saved.add(image_saved);
			
			images_saved_id.append(image_saved.getId() + ", ");
			
			// controlla che nella map siano state processate tutte le immagini relativamente alla uuid della testata di riferimento
			this.checkImagesMap(image.getStoneInspectionHeadUuid());
			
			String res = "Immagine con id " + image_saved.getId() + " con picture_path " + image_saved.getPicture_path() +  
						 ", sketch_path " + image_saved.getSketch_path() + " e overlay_path " + image_saved.getOverlayPath() + " salvata correttamente nel db";
			logService.printLog(LogUtils.INFO, res);
		}
		
		if (response.length() > 0) { // se c'è stato almeno un errore nel salvataggio immagini
			
			logService.printAndSaveStoneLog(LogUtils.FATAL, response.toString(), this.getClass().getName(), method_name, response.toString(), "");
			
			return null;
		}
		
		// il substring è per rimuovere l'ultima virgola dalla stringa
		String img_response = "Immagini con id " + images_saved_id.substring(0, Math.max(0, images_saved_id.length() - 2)) + " salvate correttamente nel db";
		logService.printAndSaveStoneLog(LogUtils.INFO, img_response, this.getClass().getName(), method_name, "", img_response);
		
		return images_saved;
	}
	
	/**
	 * It processes the image. The process consists of 3 different parts:
	 * <br>1) sets inside the stone_image the correct path of byte[] image processed. The extension of the image is obtained by Tika framework;
	 * <br>2) saves the image inside file system;
	 * <br>3) updates the hashmap containing the number of images to be processed for the referenced inspection_head. The referenced is contained inside stone_inspection_head_uuid field of stone_image.
	 * @param stone_image the Stone_inspection_image object containing the whole data
	 * @param image the byte[] of the image
	 * @param image_type the type of the image. Use LogUtils to better specify it
	 * @param file_name the name of the file
	 * @param count_image the serial of the image processed
	 * @param response the StringBuilder containing the response
	 * @return false if something goes wrong, true otherwise.
	 */
	private boolean processImage(Stone_inspection_image stone_image, byte[] image, String image_type, String file_name, int count_image, StringBuilder response) {
		
		String path = stoneProps.getFile_path_images() + file_name + "_" + image_type + "_" + count_image + "." + new Tika().detect(image).split("/")[1];
		
		if (!this.saveImageOnFileSystem(stone_image, image, response, image_type, stoneProps.getFile_path_base() + path)) {
			return false;
		}
		
		switch(image_type) {
			case ImageUtils.PICTURE:
				stone_image.setPicture_path(path);
				this.updateImagesMap(stone_image);
				break;
			case ImageUtils.SKETCH:
				stone_image.setSketch_path(path);
				break;
			case ImageUtils.OVERLAY:
				stone_image.setOverlayPath(path);
				break;
		}
		
		return true;
	}

	@Override
	public boolean saveImageOnFileSystem(Stone_inspection_image img, byte[] image, StringBuilder response, String image_type, String image_path) {
		
		String method_name = new Throwable().getStackTrace()[0].getMethodName();
		
		try {
			Files.write(Paths.get(image_path), image);
			
		} catch (IOException e) {
			
			String error_response = "Errore salvataggio " + image_type.toLowerCase() + " nel file system : " + e;
			logService.printAndSaveStoneLog(LogUtils.FATAL, error_response, this.getClass().getName(), method_name, error_response, "");

			response.append(error_response + System.lineSeparator());
			
			return false;
		}
		
		logService.printLog(LogUtils.INFO, "L'immagine " + img.getId() + " con path " + image_path + " correttamente salvata nel file system");
		
		return true;
	}
	
	/**
	 * Updates the hashmap containing the reference to the inspection head uuid.<br/>If the counter of the images related to the updated key goes to 0 it sends the email.
	 * @param image the image processed, containing the reference to inspection head
	 */
	private void updateImagesMap(Stone_inspection_image image) {
		
		String method_name = new Throwable().getStackTrace()[0].getMethodName();
		
		// aggiorno il vettore del numero delle immagini
		images_map.put(image.getStoneInspectionHeadUuid(), images_map.get(image.getStoneInspectionHeadUuid()) - 1);
		
		String res = "Immagini rimanenti per testata con uuid " + image.getStoneInspectionHeadUuid() + " = " + images_map.get(image.getStoneInspectionHeadUuid());
		logService.printAndSaveStoneLog(LogUtils.INFO, res, this.getClass().getName(), method_name, "", res);
	}
	
	/**
	 * Checks if the value based on stone_inspection_head_uuid key is less or equals 0. If it's so, the key is removed, then it sends and email based on that key.
	 * @param stone_inspection_head_uuid
	 */
	private void checkImagesMap(String stone_inspection_head_uuid) {

		// se la chiave esiste e sono state caricate tutte le immagini
		if (images_map.get(stone_inspection_head_uuid) != null && images_map.get(stone_inspection_head_uuid) <= 0) {
				
			// rimuovo la coppia chiave valore dalla map
			images_map.remove(stone_inspection_head_uuid);
			
			// avendo ricevuto tutte le immagini per quella specifica testata invoco il servizio per generare e inviare la mail
			inspectionHelperService.generateAndSendInspectionEmail(null, stone_inspection_head_uuid);
		}
	}
	
	@Override
	public byte[] mergeImages(String picture_path, String sketch_path) throws IOException {
		
		// load source images
		BufferedImage picture = ImageIO.read(new File(picture_path));
		BufferedImage sketch = ImageIO.read(new File(sketch_path));

		int w = sketch.getWidth(), h = sketch.getHeight();

		float w_ratio = (float) picture.getWidth() / (float) sketch.getWidth();
		int y = (int) Math.abs(sketch.getHeight() * w_ratio - picture.getHeight());
		
		picture = picture.getSubimage(0, y/2, picture.getWidth(), picture.getHeight() - y);
		
		if (w < h) {
			
			float h_ratio = (float) picture.getHeight() / (float) sketch.getHeight();
			int x = (int) Math.abs(sketch.getWidth() * h_ratio - picture.getWidth());
			
			picture = picture.getSubimage(x, 0, picture.getWidth() - x, picture.getHeight());
		}
		
		Dimension newMaxSize = new Dimension(w, h);
		BufferedImage resized_picture = Scalr.resize(picture, Method.QUALITY, newMaxSize.width, newMaxSize.height);
		
		// create the new image, canvas sizes are sketch's ones
		BufferedImage overlay = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		
		// paint both images, preserving the alpha channels
		Graphics g_overlay = overlay.getGraphics();
		g_overlay.drawImage(resized_picture, 0, 0, null);
		g_overlay.drawImage(sketch, 0, 0, null);
		g_overlay.dispose();
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(overlay, "png", baos);
		baos.flush();
		byte[] overlayInByte = baos.toByteArray();
		baos.close();
		
		return overlayInByte;
	}

	@Override
	public ResponseEntity<?> downloadImage(String image_name) throws IOException {
		
		String method_name = new Throwable().getStackTrace()[0].getMethodName();
		
		String image_path = stoneProps.getFile_path_base() + stoneProps.getFile_path_images() + image_name;
		
		try {
			byte[] file_byte = Files.readAllBytes(Paths.get(image_path));
		    
		    final HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.IMAGE_PNG);
		    
		    String response = "Immagine " + image_name + " caricata correttamente";
		    logService.printAndSaveStoneLog(LogUtils.INFO, response, this.getClass().getName(), method_name, "", response);
		    
			return new ResponseEntity<byte[]>(file_byte, headers, HttpStatus.OK);
			
		} catch (Exception e) {
			
			logService.printAndSaveStoneLog(LogUtils.FATAL, e.toString(), this.getClass().getName(), method_name, e.getMessage(), "Immagine " + image_name + " non trovata al path " + image_path);
			
			return new ResponseEntity<String>("Immagine " + image_name + " non trovata", null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@Override
	public void setImagesMap(String stone_inspection_head_uuid, int stone_inspection_images) {

		String method_name = new Throwable().getStackTrace()[0].getMethodName();
		
		String log_message = "Inserimento coppia <chiave, valore> = <" + stone_inspection_head_uuid + ", " + stone_inspection_images + ">";
		logService.printAndSaveStoneLog(LogUtils.INFO, log_message, this.getClass().getName(), method_name, "", log_message);
		
		images_map.put(stone_inspection_head_uuid, stone_inspection_images);
	}
}
