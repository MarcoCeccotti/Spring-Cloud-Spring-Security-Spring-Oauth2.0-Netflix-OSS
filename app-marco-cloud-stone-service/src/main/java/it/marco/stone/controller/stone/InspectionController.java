package it.marco.stone.controller.stone;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import it.marco.marco.bean.http.Outcome;
import it.marco.marco.bean.http.WrapperResponse;
import it.marco.stone.bean.stone.Stone_inspection_head;
import it.marco.stone.bean.stone.Stone_inspection_image;
import it.marco.stone.service.stone.ImageService;
import it.marco.stone.service.stone.InspectionHelperService;
import it.marco.stone.service.stone.StoneInspectionServiceImpl;

@RestController
@RequestMapping(value = "/stone")
@Api(value = "Stone Inspection")
//@PreAuthorize("hasRole('ROLE_ADM')")
public class InspectionController {
	
	@Inject
	private StoneInspectionServiceImpl stoneInspectionService;
	@Inject
	private InspectionHelperService inspectionHelperService;
	@Inject
	private ImageService imageService;
	
	@ApiOperation(value = "Inserts inspections on db", response = WrapperResponse.class)
	@ApiImplicitParams({@ApiImplicitParam(name = "Authorization", defaultValue = "Bearer ", value = "Authorization token", required = true, dataType = "string", paramType = "header")})
	@PostMapping(value = "/insert", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> insert(@RequestBody List<Stone_inspection_head> sIHs,
								  	@RequestHeader String company_id) throws Exception {
		
		String resp_check = inspectionHelperService.checkInspection(sIHs);
		if (resp_check != null) {
			return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body(new WrapperResponse(null, new Outcome(HttpStatus.BAD_REQUEST, resp_check, null, null, null)));
		}
		
		// controlla se il collaudo ricevuto sia o no già presente nel db
		inspectionHelperService.checkIsInspectionOnDb(sIHs);
		
		// salva il collaudo
		List<Stone_inspection_head> headsRowSaved = stoneInspectionService.insertInspection(sIHs);
		
		// controlla che non ci sia da inviare una email con soltanto l'allegato
		stoneInspectionService.checkInspectionToSendEmail(headsRowSaved);
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(new WrapperResponse(headsRowSaved, new Outcome(HttpStatus.OK, "Inspections correctly inserted.", null, null, null)));
	}

	@ApiOperation(value = "Gets the inspection tables", response = WrapperResponse.class)
	@ApiImplicitParams({@ApiImplicitParam(name = "Authorization", defaultValue = "Bearer ", value = "Authorization token", required = true, dataType = "string", paramType = "header")})
	@GetMapping(value = "/updateDB")
	public ResponseEntity<?> updateDB(@RequestHeader String company_id) throws Exception {
		
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(new WrapperResponse(stoneInspectionService.updateDB(), new Outcome(HttpStatus.OK, "Db tables loaded correctly.", null, null, null)));
	}
	
	@ApiOperation(value = "Upload images on db", response = WrapperResponse.class)
	@ApiImplicitParams({@ApiImplicitParam(name = "Authorization", defaultValue = "Bearer ", value = "Authorization token", required = true, dataType = "string", paramType = "header")})
	@PostMapping(value = "/upload_images", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> uploadImages(@RequestBody List<Stone_inspection_image> images,
								  		  @RequestHeader String company_id) throws Exception {
		
		List<Stone_inspection_image> sII = imageService.uploadImages(images);
		
		if (sII == null) {
			return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body(new WrapperResponse(null, new Outcome(HttpStatus.BAD_REQUEST, "Errore nel salvataggio immagini", null, null, null)));
		}
		
		return ResponseEntity
					.status(HttpStatus.OK)
					.body(new WrapperResponse(sII, new Outcome(HttpStatus.OK, "Images correctly saved on db.", null, null, null)));
	}
	
	@ApiOperation(value = "Fetches an image by its name", response = WrapperResponse.class)
	@GetMapping(value = "/download_image/{image_name}")
	public ResponseEntity<?> downloadImage(@PathVariable String image_name) throws Exception {
		
		return imageService.downloadImage(image_name);
	}
}