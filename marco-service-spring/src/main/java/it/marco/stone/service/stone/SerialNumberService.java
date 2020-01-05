package it.marco.stone.service.stone;

import it.marco.stone.bean.stone.Stone_inspection_details;

public interface SerialNumberService {

	/**
	 * Receives a serial number and retrieves a Stone_inspection_details based on that serial number, returning it encapsulated on a WrapperResponse.
	 * @param serial_number the serial number
	 * @return a Stone_inspection_details if it exists, null otherwise.
	 */
	public Stone_inspection_details serialNumber(Integer serial_number);
}