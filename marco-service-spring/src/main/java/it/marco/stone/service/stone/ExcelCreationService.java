package it.marco.stone.service.stone;

import java.util.List;

import it.marco.marco.bean.mail.Mail_attachment;
import it.marco.stone.bean.stone.Stone_inspection_head;

public interface ExcelCreationService {
	
	/**
	 * Creates an excel file based on a list of stone inspection.
	 * @param sIHs the list of stone inspection
	 * @param file_name the name of the file
	 * @param jsonInspection the inspection in json format
	 * @return Mail_attachment object of excel created.
	 * @throws Exception if something goes wrong during file excel creation
	 */
	public Mail_attachment createExcelFiles(List<Stone_inspection_head> sIHs, String file_name, String jsonInspection) throws Exception;
}