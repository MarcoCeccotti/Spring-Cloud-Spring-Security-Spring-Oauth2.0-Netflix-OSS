package it.marco.stone.service.stone;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import it.marco.marco.service.log.LogService;
import it.marco.marco.utils.utilities.LogUtils;
import it.marco.marco.utils.utilities.StoneUtils;
import it.marco.stone.bean.stone.Stone_inspection_head;
import it.marco.stone.bean.stone.Stone_inspection_row;
import it.marco.stone.bean.stone.Stone_inspection_service_response;
import it.marco.stone.bean.stone.Stone_inspection_status;
import it.marco.stone.dao.stone.Coan_divisioni_DAO;
import it.marco.stone.dao.stone.Prj_activity_DAO;
import it.marco.stone.dao.stone.Stone_inspection_applicant_DAO;
import it.marco.stone.dao.stone.Stone_inspection_head_DAO;
import it.marco.stone.dao.stone.Stone_inspection_head_type_DAO;
import it.marco.stone.dao.stone.Stone_inspection_inspector_DAO;
import it.marco.stone.dao.stone.Stone_inspection_row_DAO;
import it.marco.stone.dao.stone.Stone_inspection_status_DAO;
import it.marco.stone.dao.stone.Supplier_DAO;
import it.marco.stone.dao.stone.Tab_delivery_mode_DAO;
import it.marco.stone.dao.stone.Tab_exchange_DAO;
import it.marco.stone.dao.stone.Tab_stone_finish_DAO;
import it.marco.stone.dao.stone.Tab_stone_inspection_feature_DAO;
import it.marco.stone.dao.stone.Tab_stone_inspection_quality_DAO;
import it.marco.stone.dao.stone.Tab_stone_inspection_shape_DAO;
import it.marco.stone.dao.stone.Tab_stone_quality_DAO;
import it.marco.stone.dao.stone.Tab_stone_selection_DAO;
import it.marco.stone.dao.stone.Tab_stone_type_DAO;
import it.marco.stone.dao.stone.Tab_um_DAO;

@Service
public class StoneInspectionServiceImpl implements StoneInspectionService {

	@Inject
	private Coan_divisioni_DAO coan_divisioni_DAO;
	@Inject
	private Prj_activity_DAO prj_activity_DAO;
	@Inject
	private Supplier_DAO supplier_DAO;
	
	@Inject
	private Tab_exchange_DAO tab_exchange_DAO;
	@Inject
	private Tab_delivery_mode_DAO tab_delivery_mode_DAO;
	@Inject
	private Tab_um_DAO tab_um_DAO;
	
	@Inject
	private Tab_stone_finish_DAO tab_stone_finish_DAO;
	@Inject
	private Tab_stone_quality_DAO tab_stone_quality_DAO;
	@Inject
	private Tab_stone_selection_DAO tab_stone_selection_DAO;
	@Inject
	private Tab_stone_type_DAO tab_stone_type_DAO;

	@Inject
	private Stone_inspection_applicant_DAO stone_inspection_applicant_DAO;
	@Inject
	private Stone_inspection_head_DAO stone_inspection_head_DAO;
	@Inject
	private Stone_inspection_head_type_DAO stone_inspection_head_type_DAO;
	@Inject
	private Stone_inspection_inspector_DAO stone_inspection_inspector_DAO;
	@Inject
	private Stone_inspection_row_DAO stone_inspection_row_DAO;
	@Inject
	private Stone_inspection_status_DAO stone_inspection_status_DAO;

	@Inject
	private Tab_stone_inspection_feature_DAO tab_stone_inspection_feature_DAO;
	@Inject
	private Tab_stone_inspection_quality_DAO tab_stone_inspection_quality_DAO;
	@Inject
	private Tab_stone_inspection_shape_DAO tab_stone_inspection_shape_DAO;
	
	@Inject
	private InspectionHelperService inspectionHelperService;
	@Inject
	private LogService logService;
	@Inject
	private ImageService imageService;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Stone_inspection_head> insertInspection(List<Stone_inspection_head> sIHs) {

		String method_name = new Throwable().getStackTrace()[0].getMethodName();
		
		String jsonInspection = new Gson().toJson(sIHs);
		
		String log_string = "Inizio inserimento collaudo - " + jsonInspection;
		logService.printAndSaveStoneLog(LogUtils.INFO, log_string, this.getClass().getName(), method_name, "", log_string);
		
		StringBuilder heads_saved_id = new StringBuilder(64);
		StringBuilder rows_saved_id = new StringBuilder(64);
		
		List<Stone_inspection_head> headsRowsSaved = new ArrayList<Stone_inspection_head>();
		
		for (Stone_inspection_head sIH : sIHs) {

			// setta a 0 l'id della testata
			sIH.setId(0);
			
			// setta l'id della company_source, il cui valore è fissato a 2
			sIH.setCompany_input_source_id(2);
			
			// ottiene l'id del fornitore passato
			String supplier_id = sIH.getSupplier_id() == null ? "" : sIH.getSupplier_id();
			
			// se il fornitore non esiste viene salvato nella rispettiva tabella del db
			if (supplier_DAO.checkSupplier(supplier_id) == null) {
				Stone_inspection_status inspectionStatus = stone_inspection_status_DAO.findByCode(StoneUtils.CODE_SUPPLIER);
				sIH.setStone_inspection_status_id(inspectionStatus.getId());
			}
			
			// salva la Stone_inspection_head e preleva l'oggetto salvato
			Stone_inspection_head headSaved = stone_inspection_head_DAO.save(sIH);
			
			heads_saved_id.append("#" + headSaved.getId() + ", ");
			
			// preleva l'id della testata salvata da inserire poi come chiave esterna nelle righe
			int headId = headSaved.getId();
			
			// salva le righe
			for (Stone_inspection_row sIR : sIH.getStone_inspection_row()) {
				
				// setta a 0 l'id della riga
				sIR.setId(0);
				
				// setta l'id della testata; viene usata come chiave esterna
				sIR.setStoneInspectionHeadId(headId);

				if (sIH.getStone_inspection_head_type_id() == 2) { // blocco -> Tonnellate o Metri Cubi (MC)
					sIR.setPieces(1);
					sIR.setCost_estimated_tot(sIR.getCost_estimated_unit().multiply(new BigDecimal(sIR.getPieces())));
					sIR.setGross_mc(sIR.getGross_mc_tot());
					sIR.setGross_tn_tot(sIR.getGross_tn());
					
				} else { // lastra -> Quintali o Metri Quadrati (MQ)
					
					sIR.setCost_estimated_unit(new BigDecimal(0));
					sIR.setGross_mq(sIR.getGross_mq_tot().divide(new BigDecimal(sIR.getPieces()), 3, RoundingMode.HALF_UP));
					sIR.setGross_mc(BigDecimal.ZERO);
					sIR.setGross_mc_tot(BigDecimal.ZERO);
					sIR.setGross_tn(sIR.getGross_tn_tot()); // stesso valore tn_tot e tn (quello non tot)
				}
				
				// calcola il costo totale
				sIR.setCost_tot(sIR.getCost_unit().multiply(new BigDecimal(sIR.getPieces())));
				
				// preleva l'id dell'unita di misura dalla testata solo se nella riga questo valore non viene valorizzato
				if (sIR.getUm_row() == null || sIR.getUm_row().equals("")) {
					sIR.setUm_row(sIH.getUm_id());
				}
				
				// preleva l'id della tab stone quality dalla testata solo se nella riga questo valore non viene valorizzato
				if (sIR.getTab_stone_quality_id() == null || sIR.getTab_stone_quality_id().equals("")) {
					sIR.setTab_stone_quality_id(sIH.getTab_stone_quality_id());
				}
				
				// salva la riga
				Stone_inspection_row rowSaved = stone_inspection_row_DAO.save(sIR);
				
				headSaved.addRow(rowSaved);
				
				rows_saved_id.append(rowSaved.getId() + ", ");
			}
			
			// la testata salvata e popolata con le righe salvate viene inserita in un apposito vettore
			headsRowsSaved.add(headSaved);
		}
		
		// il substring è per rimuovere l'ultima virgola dalla stringa
		String res = "Collaudo " + heads_saved_id.substring(0, Math.max(0, heads_saved_id.length() - 2)) + " con righe " + rows_saved_id.substring(0, Math.max(0, rows_saved_id.length() - 2)) + " salvato con successo : " + jsonInspection;
		logService.printAndSaveStoneLog(LogUtils.INFO, res,	this.getClass().getName(), method_name, "", res);
		
		return headsRowsSaved;
	}
	
	@Override
	@Cacheable(value = "updateDB", sync = true)
	@Transactional(rollbackFor = Exception.class)
	public Stone_inspection_service_response updateDB() throws Exception {
		
		String method_name = new Throwable().getStackTrace()[0].getMethodName();
		
		Stone_inspection_service_response response = new Stone_inspection_service_response();
		
		response.setSuppliers(supplier_DAO.getSuppliers());
		response.setTabUms(tab_um_DAO.findByIdOrIdOrIdOrId("MQ", "MC", "TN", "QL"));
		response.setTabExchanges(tab_exchange_DAO.findByIdOrId("USD", "EUR"));
		response.setTabStoneTypes(tab_stone_type_DAO.findByCodeOrCode("M", "G"));
		
		response.setTabStoneInspectionQualities(tab_stone_inspection_quality_DAO.findAll());
		
		response.setStoneInspectionInspector(stone_inspection_inspector_DAO.findAll());
		response.setStoneInspectionApplicants(stone_inspection_applicant_DAO.findAll());
		response.setStoneInspectionHeadTypes(stone_inspection_head_type_DAO.findAll());
		response.setTabDeliveryModes(tab_delivery_mode_DAO.findAll());
		
		response.setTabStoneInspectionFeatures(tab_stone_inspection_feature_DAO.findAll());
		response.setTabStoneInspectionShapes(tab_stone_inspection_shape_DAO.findAll());
		response.setTabStoneQualities(tab_stone_quality_DAO.findAll());
		
		response.setTabStoneFinishes(tab_stone_finish_DAO.findAll());
		response.setPrjActivities(prj_activity_DAO.findAll());
		response.setCoanDivisioni(coan_divisioni_DAO.findAll());
		
		response.setTabStoneSelections(tab_stone_selection_DAO.findAll());
		
		String res = "Prelievo dati dal db eseguito correttamente";
		logService.printAndSaveStoneLog(LogUtils.INFO, res,	this.getClass().getName(), method_name, "", res);
		
		return response;
	}
	
	@Override
	public void checkInspectionToSendEmail(List<Stone_inspection_head> sIHs) {

		// contatore del numero di immagini trovate nel collaudo
		int total_stone_inspection_pictures = 0;
		
		for (Stone_inspection_head sIH : sIHs) {
			
			// aggiorno il contatore totale delle immagini del collaudo
			total_stone_inspection_pictures += sIH.getCount_pictures();
			
			// setta nella hash map delle immagini il suo uuid e il numero di immagini associate se ve ne è almeno una
			if (sIH.getCount_pictures() > 0) {
				imageService.setImagesMap(sIH.getUuid(), sIH.getCount_pictures());
			}
		}
		
		// se il numero totale delle immagini scattate è 0 creo la mail con soltanto il file excel dell'ispezione
		if (total_stone_inspection_pictures == 0) {
			inspectionHelperService.generateAndSendInspectionEmail(sIHs, null);
		}
	}
}