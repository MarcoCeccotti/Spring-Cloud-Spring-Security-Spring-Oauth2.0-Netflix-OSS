package it.marco.stone.service.stone;

import java.util.List;

import it.marco.stone.bean.stone.Stone_inspection_head;
import it.marco.stone.bean.stone.Stone_inspection_image;

public interface InspectionHelperService {
	
	/**
	 * Checks if the inspections are ok. This is a check to admit or not the insert on db.
	 * @param sIHs the inspection object to inspect
	 * @return null if everything is ok, a String containing the errors otherwise.
	 */
	public String checkInspection(List<Stone_inspection_head> sIHs);
	
	/**
	 * Checks if the inspection is already present on db.
	 * @param sIHs the inspection to be checked
	 * @return false if the inspection is already present on db; true otherwise.
	 */
	public boolean checkIsInspectionOnDb(List<Stone_inspection_head> sIHs);
	
	/**
	 * Checks if the images are already or not present on database.
	 * @param images the images to be checked
	 * @param jsonImages the images in json format
	 * @return false if the images are already on db; true otherwise.
	 */
	public boolean checkAreImagesOnDb(List<Stone_inspection_image> images, String jsonImages);
	
	/**
	 * Generates the email based on the inspection and sends it to the recipients. This method can be used both when the entire inspection is known and when only the uuid of the head is known.
	 * @param sIHs the inspection to process. It must be valorised if this resource is known, otherwise it is fetched by the stone_inspection_head_uuid parameter
	 * @param stone_inspection_head_uuid the string representing the uuid of the inspection head. If both sIHs and stone_inspection_head_uuid are valorised the precedence is of sIHs
	 */
	public void generateAndSendInspectionEmail(List<Stone_inspection_head> sIHs, String stone_inspection_head_uuid);
}
