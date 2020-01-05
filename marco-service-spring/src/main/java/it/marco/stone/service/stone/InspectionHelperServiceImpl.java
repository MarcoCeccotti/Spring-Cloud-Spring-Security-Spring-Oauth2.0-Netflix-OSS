package it.marco.stone.service.stone;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import it.marco.marco.bean.mail.Mail_attachment;
import it.marco.marco.bean.mail.Mail_server;
import it.marco.marco.cloud.config.properties.StoneProperties;
import it.marco.marco.cloud.service.log.LogService;
import it.marco.marco.cloud.session.Session_infos;
import it.marco.marco.service.mail.MailService;
import it.marco.marco.utils.utilities.LogUtils;
import it.marco.stone.bean.stone.Stone_inspection_head;
import it.marco.stone.bean.stone.Stone_inspection_image;
import it.marco.stone.bean.stone.Stone_inspection_row;
import it.marco.stone.dao.stone.Stone_inspection_head_DAO;
import it.marco.stone.dao.stone.Stone_inspection_image_DAO;
import it.marco.stone.dao.stone.Stone_inspection_row_DAO;
import it.marco.stone.dao.stone.Tab_um_DAO;

@Service
public class InspectionHelperServiceImpl implements InspectionHelperService {
	
	@Inject
	private ExcelCreationService excelCreationService;
	@Inject
	private MailService mailService;
	@Inject
	private LogService logService;

	@Inject
	private Stone_inspection_head_DAO stone_inspection_head_DAO;
	@Inject
	private Stone_inspection_row_DAO stone_inspection_row_DAO;
	@Inject
	private Stone_inspection_image_DAO stone_inspection_image_DAO;
	
	@Inject
	private Tab_um_DAO tab_um_DAO;
	
	@Inject
	private Session_infos session_infos;

	@Inject
	private StoneProperties stoneProps;
	
	@Override
	public String checkInspection(List<Stone_inspection_head> sIHs) {
		
		String method_name = new Throwable().getStackTrace()[0].getMethodName();
		
		StringBuilder error_response = new StringBuilder(256);
		
		String jsonInspection = new Gson().toJson(sIHs);

		// controllo sulle testate
		if(sIHs == null || sIHs.size() == 0) {
			error_response.append("Nessuna testata trovata nel collaudo" + System.lineSeparator());
			
		} else for(Stone_inspection_head sIH : sIHs) {
			
			if(sIH.getCoan_divisioni_id() == null || sIH.getCoan_divisioni_id().trim().equals("")) {
				error_response.append("Valore Divisione non valido" + System.lineSeparator());
			}
			
			if(sIH.getInspection_date() == null) {
				error_response.append("Valore Data non valido" + System.lineSeparator());
			}
			
			if(sIH.getUm_id() == null || sIH.getUm_id().trim().equals("")) {
				error_response.append("Valore Unita di Misura non valido" + System.lineSeparator());
			}
			
			if(sIH.getDelivery_mode_id() == null || sIH.getDelivery_mode_id().trim().equals("")) {
				error_response.append("Valore Modalita di Consegna non valido" + System.lineSeparator());
			}
			
			if(sIH.getStone_inspector_id() == null || sIH.getStone_inspector_id() == 0) {
				error_response.append("Valore Collaudatore non valido" + System.lineSeparator());
			}
			
			if(sIH.getStone_applicant_id() == null || sIH.getStone_applicant_id() == 0) {
				error_response.append("Valore Richiedente non valido" + System.lineSeparator());
			}
			
			if(sIH.getTab_stone_quality_id() == null || sIH.getTab_stone_quality_id().trim().equals("")) {
				error_response.append("Valore Qualita non valido" + System.lineSeparator());
			}
			
			if(sIH.getStone_inspection_head_type_id() == null || sIH.getStone_inspection_head_type_id() == 0) {
				error_response.append("Valore Tipologia Materiale non valido" + System.lineSeparator());
			}
			
			if (sIH.getUm_id() == null || sIH.getUm_id().trim().equals("")) {
				error_response.append("Valore Unita di misura non valido" + System.lineSeparator());
			}
			
			if(sIH.getStone_inspection_head_type_id() == 2) { // blocco
				if (sIH.getUm_id().equals("QL") || sIH.getUm_id().equals("MQ")) {
					error_response.append("Unita di misura " + tab_um_DAO.findById(sIH.getUm_id()).get().getTitle() + " non valida per un blocco" + System.lineSeparator());
				}
			} else if (sIH.getUm_id().equals("TN") || sIH.getUm_id().equals("MC")) { // lastra
				error_response.append("Unita di misura " + tab_um_DAO.findById(sIH.getUm_id()).get().getTitle() + " non valida per una lastra" + System.lineSeparator());
			}
			
			// controllo sulle righe
			List<Stone_inspection_row> rows = sIH.getStone_inspection_row();
			if (rows == null || rows.size() == 0) {
				error_response.append("Nessuna riga trovata nel collaudo" + System.lineSeparator());
				continue;
			}

			int serial = 0;
			for(Stone_inspection_row row : rows) {

				serial++;
				int row_number = (row.getBlock_number() == null) ? serial: row.getBlock_number();
				
				String stone_type = sIH.getStone_inspection_head_type_id() == 2 ? "blocco" : "lastra";
				
				if (row.getPrj_activity_id() == null || row.getPrj_activity_id() == 0) {
					error_response.append("Valore Commessa non valido per " + stone_type + " #" + row_number + System.lineSeparator());
				}

				if (row.getSupplier_code() == null || row.getSupplier_code().trim().equals("")) {
					error_response.append("Valore Numero Origine non valido per " + stone_type + " #" + row_number + System.lineSeparator());
				}

				String block_slab_number = "Numero Lastra";
				String profondita_spessore = "Profondita";
				if (sIH.getStone_inspection_head_type_id() == 2) { // blocco
					
					block_slab_number = "Numero Blocco";
					profondita_spessore = "Spessore";
					
					if (row.getTab_stone_inspection_quality_id() == null || row.getTab_stone_inspection_quality_id() == 0) {
						error_response.append("Valore Tipo Qualita non valido per " + stone_type + " #" + row_number + System.lineSeparator());
					}
					
					if(row.getTab_stone_inspection_feature_id() == null || row.getTab_stone_inspection_feature_id() == 0) {
						error_response.append("Valore Caratteristica non valido per " + stone_type + " #" + row_number + System.lineSeparator());
					}
					
					if (row.getTab_stone_inspection_shape_id() == null || row.getTab_stone_inspection_shape_id() == 0) {
						error_response.append("Valore Forma non valido per " + stone_type + " #" + row_number + System.lineSeparator());
					}
					
					if (row.getCost_estimated_unit() != null && row.getCost_estimated_unit().compareTo(BigDecimal.ZERO) == 0) {
						error_response.append("Valore Valutazione non valido per " + stone_type + " #" + row_number + System.lineSeparator());
					}
					
					// campi non validi per un blocco
					if (row.getTab_stone_finish_id() != null && !row.getTab_stone_finish_id().equals("")) {
						error_response.append("Valore Finitura non valido per " + stone_type + " #" + row_number + System.lineSeparator());
					}
					
					if(row.getPieces() != null && row.getPieces() > 0) {
						error_response.append("Valore Numero Pezzi non valido per " + stone_type + " #" + row_number + System.lineSeparator());
					}
					
				} else if (sIH.getStone_inspection_head_type_id() != 2) { // lastra
					
					if (row.getTab_stone_finish_id() == null || row.getTab_stone_finish_id().trim().equals("")) {
						error_response.append("Valore Finitura non valido per " + stone_type + " #" + row_number + System.lineSeparator());
					}
					
					if(row.getPieces() == null || row.getPieces() == 0) {
						error_response.append("Valore Numero Pezzi non valido per " + stone_type + " #" + row_number + System.lineSeparator());
					}
					
					// campi non validi per una lastra
					if (row.getTab_stone_inspection_quality_id() != null && row.getTab_stone_inspection_quality_id() > 0) {
						error_response.append("Valore Tipo Qualita non valido per " + stone_type + " #" + row_number + System.lineSeparator());
					}
					
					if(row.getTab_stone_inspection_feature_id() != null && row.getTab_stone_inspection_feature_id() > 0) {
						error_response.append("Valore Caratteristica non valido per " + stone_type + " #" + row_number + System.lineSeparator());
					}
					
					if (row.getTab_stone_inspection_shape_id() != null && row.getTab_stone_inspection_shape_id() > 0) {
						error_response.append("Valore Forma non valido per " + stone_type + " #" + row_number + System.lineSeparator());
					}
					
					if (row.getCost_estimated_unit() != null && row.getCost_estimated_unit().compareTo(BigDecimal.ZERO) != 0) {
						error_response.append("Valore Valutazione non valido per " + stone_type + " #" + row_number + System.lineSeparator());
					}
				}
				
				if (row.getBlock_number() == null || row.getBlock_number() == 0) {
					error_response.append("Valore " + block_slab_number + " non valido per " + stone_type + " #" + row_number + System.lineSeparator());
				}
				
				if (row.getGross_dim1() == null || row.getGross_dim1().compareTo(BigDecimal.ZERO) == 0) {
					error_response.append("Valore campo Lunghezza non valido per " + stone_type + " #" + row_number + System.lineSeparator());
				}
				if (row.getGross_dim2() == null || row.getGross_dim2().compareTo(BigDecimal.ZERO) == 0) {
					error_response.append("Valore campo Altezza non valido per " + stone_type + " #" + row_number + System.lineSeparator());
				}
				if (row.getGross_dim3() == null || row.getGross_dim3().compareTo(BigDecimal.ZERO) == 0) {
					error_response.append("Valore campo " + profondita_spessore + " non valido per " + stone_type + " #" + row_number + System.lineSeparator());
				}
			}
		}
		
		if (error_response.length() > 0) { // se è stato trovato almeno un errore nel controllo del collaudo

			String error = (error_response.replace(error_response.length() - System.lineSeparator().length(), error_response.length(), "")).toString();
			logService.printAndSaveStoneLog(LogUtils.FATAL, "Richiesta eseguita da: " + session_infos.getUsername() + " - collaudo:" + jsonInspection + " - errore: " + error,
					this.getClass().getName(), method_name, error, jsonInspection);
			
			return error_response.toString();
		}
		
		String res = "Il collaudo è corretto : " + jsonInspection;
		logService.printAndSaveStoneLog(LogUtils.INFO, res, this.getClass().getName(), method_name, "", res);
		
		return null;
	}
	
	@Override
	public boolean checkIsInspectionOnDb(List<Stone_inspection_head> sIHs) {
		
		String method_name = new Throwable().getStackTrace()[0].getMethodName();
		
		String jsonInspection = new Gson().toJson(sIHs);

		// preleva tutti gli id dal collaudo e li inserisce in una lista
		List<String> uuids = sIHs.stream()
	                         	 .map(sIH -> sIH.getUuid())
	                         	 .collect(Collectors.toCollection(ArrayList::new));
		
		if (!stone_inspection_head_DAO.findAllByUuids(uuids).isEmpty()) {
			
			String response = "Collaudi con uuid " + String.join(", ", uuids) + " già presenti nel db";
			logService.printAndSaveStoneLog(LogUtils.INFO, response, this.getClass().getName(), method_name, response, jsonInspection);
			
			return false;
		}
		
		return true;
	}
	
	@Override
	public boolean checkAreImagesOnDb(List<Stone_inspection_image> images, String jsonImages) {

		String method_name = new Throwable().getStackTrace()[0].getMethodName();
		
		// preleva tutti gli id dalle immagini e li inserisce in una lista
		List<String> uuids = images.stream()
          	   					   .map(image -> image.getUuid())
          	   					   .collect(Collectors.toCollection(ArrayList::new));

		if (!stone_inspection_image_DAO.findAllByUuids(uuids).isEmpty()) {
			
			String response = "Immagini con uuid " + String.join(", ", uuids) + " già presenti nel db";
			logService.printAndSaveStoneLog(LogUtils.INFO, response, this.getClass().getName(), method_name, response, jsonImages);
			
			return false;
		}
		
		return true;
	}
	
	@Override
	public void generateAndSendInspectionEmail(List<Stone_inspection_head> sIHs, String stone_inspection_head_uuid) {
		
		// invio email in modalità asincrona
		Executors.newCachedThreadPool().submit(() -> {
			
			String method_name = new Throwable().getStackTrace()[0].getMethodName();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:S");
			
			// recupero l'intero collaudo
			Stone_inspection_head sIH = (sIHs == null) ? fetchInspection(stone_inspection_head_uuid) : sIHs.get(0);
			if (sIH == null) {
				return;
			}

			// genero il json da usare nei log in caso di errore
			String jsonInspection = new Gson().toJson(sIH);
			
			// ottengo l'allegato formato dal file excel del collaudo
			Mail_attachment mail_attachment = null;
			try {
				// genero il file excel, restituendo l'allegato con all'interno il path per il file excel generato
				SimpleDateFormat excelDateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_S");
				mail_attachment = excelCreationService.createExcelFiles(Arrays.asList(sIH), excelDateFormat.format(session_infos.getTime_st()), jsonInspection);
				
			} catch (Exception e) {
				
				String response = "Errore nella creazione del file Excel: ";
				logService.printAndSaveStoneLog(LogUtils.FATAL, response + e, this.getClass().getName(), method_name, response + e.getMessage(), jsonInspection);
				
				return;
			}
			
			// crea il text della mail
			StringBuilder mail_text = new StringBuilder(265);
			this.prepareInspectionEmailText(sIH, mail_text);
			
			// determino il titolo (subject) della mail
			String subject = sIH.getStone_inspection_head_type_id() == 2 ? "Collaudo Blocco " : "Collaudo Lastre ";
			subject += "#" + sIH.getId() + " - " + dateFormat.format(session_infos.getTime_st());
			
			Mail_server mail_server = new Mail_server(mailService.getMail_smtp_addr_sender(), mailService.getMail_smtp_addr_password(), mailService.getEmailRecipients(), subject, "", false, "Salvataggio mail di sicurezza", session_infos.getTime_st(), session_infos.getCompany_id());
			mail_server.addAttachment(mail_attachment);
			
			mailService.saveAndSendEmail(mail_server, stoneProps.getFile_path_base(), session_infos.getUsername());
		});
	}
	
	/**
	 * Fetches the entire inspection.
	 * @param stone_inspection_head_uuid the uuid of the head of the inspection used to fetch the whole inspection
	 * @return null if the DAO request produces empty results; a full Stone_inspection_head otherwise.
	 */
	private Stone_inspection_head fetchInspection(String stone_inspection_head_uuid) {
		
		String method_name = new Throwable().getStackTrace()[0].getMethodName();
		
		// recupero il collaudo sulla base dell'uuid, ordinando il tutto tramite il campo block_number
		List<Object[]> heads = stone_inspection_head_DAO.findInspectionByUuidAndOrderByBlockNumber(stone_inspection_head_uuid);
		
		if (heads == null || heads.isEmpty()) {
			
			String error_res = "Inspection head con uuid " + stone_inspection_head_uuid + " inesistente";
			logService.printAndSaveStoneLog(LogUtils.ERROR, error_res, this.getClass().getName(), method_name, error_res, "");
			
			return null;
		}
		
		List<Stone_inspection_head> testate = new ArrayList<Stone_inspection_head>();
		int curr_id = Integer.MIN_VALUE;
		
		for (Object[] head : heads) {
			if (((Stone_inspection_head) head[0]).getId() == curr_id) { // siamo ancora nella stessa head
				testate.get(testate.size() - 1).addRow(((Stone_inspection_row) head[1]));
				
			} else { // siamo in una nuova head
				
				curr_id = ((Stone_inspection_head) head[0]).getId();
				testate.add(((Stone_inspection_head) head[0]));
				testate.get(testate.size() - 1).addRow(((Stone_inspection_row) head[1]));
			}
		}

		// stampa del json del collaudo recuperato
		String res = "Fetch collaudo = " + new Gson().toJson(testate);
		logService.printAndSaveStoneLog(LogUtils.INFO, res, this.getClass().getName(), method_name, "", res);
		
		return testate.get(0);
	}
	
	/**
	 * Prepares the inspection mail text. Checks if there are images with overlay related to the inspection and applies links to the email text, otherwise it does anything.
	 * @param sIH the inspection
	 * @param mail_text the StringBuilder object to fill the mail text
	 */
	private void prepareInspectionEmailText(Stone_inspection_head sIH, StringBuilder mail_text) {
		
		// recupero le immagini per ottenere il path dell'immagine sovrapposta da inserire nel body della mail
		List<Stone_inspection_image> images = stone_inspection_image_DAO.findByStoneInspectionHeadUuidOrderByInsertTime(sIH.getUuid());
		if (!images.isEmpty()) { // definisco il corpo della mail soltanto se trovo almeno un immagine
			mail_text.append("<b>Link foto Blocchi / Lastre collaudati :</b>");
			mail_text.append("<br>");
			mail_text.append("<b>I link sono visibili se collegati alla rete, altrimenti dopo aver attivato la VPN.</b>");
			mail_text.append("<br><br>");
			
			// preparo gli elementi per la mail: images_map per gestire i seriali delle foto dei link
			Map<String, Integer> images_map = new HashMap<String, Integer>();

			for (Stone_inspection_image image: images) {
				
				if (images_map.get(image.getStoneInspectionRowUuid()) == null) { // se la row uuid ancora non esiste ce la inserisco
					images_map.put(image.getStoneInspectionRowUuid(), 1);
				} else { // altrimenti aggiorno il contatore dell'immagine
					images_map.put(image.getStoneInspectionRowUuid(), images_map.get(image.getStoneInspectionRowUuid()) + 1);
				}
				
				String blockSlabName = "";
				Stone_inspection_row _row = stone_inspection_row_DAO.findByUuid(image.getStoneInspectionRowUuid());
				if (_row != null) {
					blockSlabName = (sIH.getStone_inspection_head_type_id() == 2) ? "Blocco #" + _row.getBlock_number() : "Lastra #" + _row.getBlock_number();
					
					// se l'immagine ha lo sketch allora recupero la sovrapposizione (overlay), altrimenti recupero l'immagine normale (picture)
					String image_path = (image.getOverlayPath() != null) ? 
												image.getOverlayPath().replace(stoneProps.getFile_path_images(), "") : // se l'immagine ha la sovrapposizione
												image.getPicture_path().replace(stoneProps.getFile_path_images(), ""); // se l'immagine NON ha la sovrapposizione
					
					// creo e inserisco all'interno della map dei block_number l'url per la chiamata al web service per il recupero immagine
					String link_name = blockSlabName + " - Foto #" + images_map.get(image.getStoneInspectionRowUuid());
					String image_link = "<a style=\"line-height:25px; font-size:13.5pt;\" href=\"" + stoneProps.getServerUrl() + image_path + "?access_token=" + session_infos.getAccess_token() + "\">" + link_name + "</a><br>";
					mail_text.append(image_link);
				}
			}
		}
	}
}