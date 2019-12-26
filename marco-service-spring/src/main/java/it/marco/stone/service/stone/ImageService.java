package it.marco.stone.service.stone;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;

import it.marco.stone.bean.stone.Stone_inspection_image;

public interface ImageService {
	
	/**
	 * Saves images on database
	 * @param images the images to save
	 * @return the List of Stone_inspection_image saved if everuthing goew right; null otherwise.
	 */
	public List<Stone_inspection_image> uploadImages(List<Stone_inspection_image> images);
	
	/**
	 * Fetches a specific requested image to be downloaded
	 * @param image_name the name to the image to be downloaded
	 * @return the binary data of the image
	 */
	public ResponseEntity<?> downloadImage(String image_name) throws IOException;
	
	/**
	 * Saves image (sketch and/or picture and/or overlay) on file system.
	 * @param time_st the Timestamp of the method caller
	 * @param img the entire Image object
	 * @param image the specific sketch, picture or overlay
	 * @param response the response to save on logs in case of saving failure
	 * @param image_type the type of the image file. It could be "Sketch", "Picture" or "Overlay". Advise is to use ImageUtils to fill this field
	 * @param image_path the path of the image file
	 * @return false is something went wrong during saving, true otherwise.
	 */
	public boolean saveImageOnFileSystem(Stone_inspection_image img, byte[] image, StringBuilder response, String image_type, String image_path);
	
	/**
	 * Overlaps sketch image into picture one. Both images are stretched based on their minimum value of width and height.
	 * @param picture_path the path to the picture image
	 * @param sketch_path the path to the sketch image
	 * @return the overlapped image as an array of byte
	 * @throws IOException error during fetching images from path or writing on a ByteArrayOutputStream
	 */
	public byte[] mergeImages(String picture_path, String sketch_path) throws IOException;
	
	/**
	 * Sets a new couple key value in the map of images. This map is used to manage the number of images correctly sent to the server from the client.
	 * @param stone_inspection_head_uuid the uuid of the head (key)
	 * @param stone_inspection_images the number of images (pictures + sketches) (value)
	 */
	public void setImagesMap(String stone_inspection_head_uuid, int stone_inspection_images);
}