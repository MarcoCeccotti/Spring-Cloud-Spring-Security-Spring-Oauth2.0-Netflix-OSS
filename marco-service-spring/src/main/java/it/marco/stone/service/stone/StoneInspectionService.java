package it.marco.stone.service.stone;

import java.util.List;

import it.marco.stone.bean.stone.Stone_inspection_head;
import it.marco.stone.bean.stone.Stone_inspection_service_response;

public interface StoneInspectionService {
	
	/**
	 * Saves a list of inspections on database. It can save images like uploadImages service.
	 * @param sIHs the inspections to save
	 * @return a List<Stone_inspection_head> object
	 */
	public List<Stone_inspection_head> insertInspection(List<Stone_inspection_head> sIHs);
	
	/**
	 * Downloads entities of inspection.
	 * @return a Stone_inspection_service_response object
	 */
	public Stone_inspection_service_response updateDB() throws Exception;
	
	/**
	 * Checks if the inspection has no pictures. If so, an email with only excel file is sent.
	 * @param sIHs the inspection to check
	 */
	public void checkInspectionToSendEmail(List<Stone_inspection_head> sIHs);
}