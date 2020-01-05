package it.marco.stone.service.stone;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import it.marco.marco.cloud.service.log.LogService;
import it.marco.marco.utils.utilities.LogUtils;
import it.marco.stone.bean.stone.Stone_inspection_details;
import it.marco.stone.dao.stone.Serial_number_DAO;

@Service
public class SerialNumberServiceImpl implements SerialNumberService {
	
	@Inject
	private LogService logService;
	
	@Inject
	private Serial_number_DAO serial_numberDAO;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Stone_inspection_details serialNumber(Integer serial_number) {

		String method_name = new Throwable().getStackTrace()[0].getMethodName();
		
		Stone_inspection_details stoneInspectionDetails = serial_numberDAO.getSerialNumberData(serial_number);
		
		if(stoneInspectionDetails == null) {

			String res = "Numero seriale " + serial_number + " non trovato";
			logService.printAndSaveStoneLog(LogUtils.FATAL, res, this.getClass().getName(), method_name, res, "");

			return null;
		}
		
		String jsonInspectionDetails = new Gson().toJson(stoneInspectionDetails);
		logService.printAndSaveStoneLog(LogUtils.INFO, "Numero seriale " + serial_number + " ha prodotto: " + jsonInspectionDetails, this.getClass().getName(), method_name, "", jsonInspectionDetails);

		return stoneInspectionDetails;
	}
}