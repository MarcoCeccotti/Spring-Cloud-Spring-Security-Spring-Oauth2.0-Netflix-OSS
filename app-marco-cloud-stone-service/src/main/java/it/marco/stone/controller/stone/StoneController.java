package it.marco.stone.controller.stone;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import it.marco.marco.bean.http.Outcome;
import it.marco.marco.bean.http.WrapperResponse;
import it.marco.stone.bean.stone.Stone_inspection_details;
import it.marco.stone.service.stone.SerialNumberService;

@RestController
@RequestMapping(value = "/stone")
@Api(value = "Serial number")
//@PreAuthorize("hasRole('ROLE_ADM')")
public class StoneController {

	@Inject
	private SerialNumberService serialNumberService;
	
	@ApiOperation(value = "Gets data from serial number", response = WrapperResponse.class)
	@ApiImplicitParams({@ApiImplicitParam(name = "Authorization", defaultValue = "Bearer ", value = "Authorization token", required = true, dataType = "string", paramType = "header")})
	@GetMapping(value = "/serial/{serial_number}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> serialNumber(@PathVariable Integer serial_number,
										  @RequestHeader String company_id,
										  HttpServletRequest request) throws Exception {
		
		Stone_inspection_details sID = serialNumberService.serialNumber(serial_number);
		
		if (sID == null) {
			return ResponseEntity
						.status(HttpStatus.NO_CONTENT)
						.body(new WrapperResponse(null, new Outcome(HttpStatus.NO_CONTENT, "Inspection detail not found.", null, null, null)));
		}
		
		return ResponseEntity
					.status(HttpStatus.OK)
					.body(new WrapperResponse(sID, new Outcome(HttpStatus.OK, "Data correctly returned.", null, null, null)));
	}
}