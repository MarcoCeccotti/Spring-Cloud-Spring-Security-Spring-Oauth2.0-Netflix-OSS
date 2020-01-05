package it.marco.stone.controller.mail;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import it.marco.marco.bean.http.Outcome;
import it.marco.marco.bean.http.WrapperResponse;
import it.marco.marco.service.mail.MailService;

@RestController
@RequestMapping(value = "/stone")
@Api(value = "Mail")
//@PreAuthorize("hasRole('ROLE_ADM')")
public class MailController {

	@Inject
	private MailService mailService;
	
	@ApiOperation(value = "Add a new recipient for mailing", response = WrapperResponse.class)
	@ApiImplicitParams({@ApiImplicitParam(name = "Authorization", defaultValue = "Bearer ", value = "Authorization token", required = true, dataType = "string", paramType = "header")})
	@GetMapping(value = "/mail/to/{mail_recipient}")
	public ResponseEntity<?> addMailrecipient(@PathVariable String mail_recipient,
					   			 			  HttpServletRequest request) throws Exception {
		
		mailService.addMailRecipient(mail_recipient);
		
		return ResponseEntity
					.status(HttpStatus.OK)
					.body(new WrapperResponse(null, new Outcome(HttpStatus.OK, "Aggiunto nuovo destinatario principale.", null, null, null)));
	}
	
	@ApiOperation(value = "Add a new recipient in carbon copy for mailing", response = WrapperResponse.class)
	@ApiImplicitParams({@ApiImplicitParam(name = "Authorization", defaultValue = "Bearer ", value = "Authorization token", required = true, dataType = "string", paramType = "header")})
	@GetMapping(value = "/mail/cc/{mail_recipient_cc}")
	public ResponseEntity<?> addMailrecipientCc(@PathVariable String mail_recipient_cc,
				   			   				  	HttpServletRequest request) throws Exception {
		
		mailService.addMailRecipientCc(mail_recipient_cc);
		
		return ResponseEntity
					.status(HttpStatus.OK)
					.body(new WrapperResponse(null, new Outcome(HttpStatus.OK, "Aggiunto nuovo destinatario in cc.", null, null, null)));
	}
	
	@ApiOperation(value = "Add a new recipient in blind carbon copy for mailing", response = WrapperResponse.class)
	@ApiImplicitParams({@ApiImplicitParam(name = "Authorization", defaultValue = "Bearer ", value = "Authorization token", required = true, dataType = "string", paramType = "header")})
	@GetMapping(value = "/mail/bcc/{mail_recipient_bcc}")
	public ResponseEntity<?> addMailrecipientBcc(@PathVariable String mail_recipient_bcc,
					   						   	 HttpServletRequest request) throws Exception {
		
		mailService.addMailRecipientBcc(mail_recipient_bcc);

		return ResponseEntity
					.status(HttpStatus.OK)
					.body(new WrapperResponse(null, new Outcome(HttpStatus.OK, "Aggiunto nuovo destinatario in bcc.", null, null, null)));
	}
}