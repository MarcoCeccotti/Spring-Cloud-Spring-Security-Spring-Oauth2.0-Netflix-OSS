package it.marco.auth.controller;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.annotations.ApiOperation;
import it.marco.auth.bean.auth.AccessParameters;
import it.marco.auth.bean.auth.AccessTokens;
import it.marco.auth.bean.auth.Users;
import it.marco.auth.dao.auth.Users_DAO;
import it.marco.auth.service.auth.LoginService;
import it.marco.marco.bean.http.Outcome;
import it.marco.marco.bean.http.Type;
import it.marco.marco.bean.http.Widget;
import it.marco.marco.bean.http.WrapperResponse;
import it.marco.marco.utils.utilities.AuthUtils;

@RestController
@RequestMapping(value = "/oauth")
public class LoginController {

	@Inject
	private LoginService loginService;
	
	@Inject
	private Users_DAO users_DAO;
	
	@ApiOperation(value = "Checks the user credentials and, if authenticated, returns the access token")
	@PostMapping(value = "/login", consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML}, produces = {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public ResponseEntity<?> login(@RequestBody AccessParameters access_params,
								   @RequestHeader String api_key, @RequestHeader String api_secret, @RequestHeader String company_id) throws JsonProcessingException, UnsupportedEncodingException {
		
		String checkInput = loginService.checkInputValues(api_key, api_secret, AuthUtils.GRANT_TYPE_PASSWORD, access_params);
		if(checkInput != null) {

			return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body(new WrapperResponse(null, new Outcome(HttpStatus.BAD_REQUEST, checkInput, Type.ERROR, null, Widget.ALERT)));
		}
		
		// recupera l'utente dal db
		Optional<Users> user = users_DAO.findById(access_params.getUsername().toLowerCase() + "@" + company_id);
		
		HttpStatus check = loginService.checkCredentials(user, access_params, company_id);
		if (check != HttpStatus.OK) {
			return check == HttpStatus.BAD_REQUEST ? ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new WrapperResponse(null, new Outcome(check, "Username e/o Password invalidi.", Type.ERROR, null, Widget.ALERT))) :
													 ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new WrapperResponse(null, new Outcome(check, "Utente disabilitato.", Type.ERROR, null, Widget.ALERT)));
		}
		
		AccessTokens tokens = loginService.getAccessToken(user.get(), api_key, api_secret, company_id);
		
		if (tokens == null) {
			return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body(new WrapperResponse(null, new Outcome(HttpStatus.BAD_REQUEST, "Errore nella generazione del token. Prego contattare l'assistenza.", Type.ERROR, null, Widget.ALERT)));
		}
		
		return ResponseEntity
					.status(HttpStatus.OK)
					.body(new WrapperResponse(tokens, null));
	}
	
	@ApiOperation(value = "Refreshes access token")
	@PostMapping(value = "/refresh", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<?> refreshToken(@RequestBody AccessParameters access_params,
		     							  @RequestHeader String api_key, @RequestHeader String api_secret, @RequestHeader String company_id,
		     							  @RequestHeader String refresh_token) throws JsonProcessingException, UnsupportedEncodingException {

		String checkInput = loginService.checkInputValues(api_key, api_secret, AuthUtils.GRANT_TYPE_REFRESH_TOKEN, access_params);
		if(checkInput != null) {
			return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body(new WrapperResponse(null, new Outcome(HttpStatus.BAD_REQUEST, checkInput, Type.ERROR, null, Widget.ALERT)));
		}
		

		AccessTokens tokens = loginService.refreshAccessToken(access_params, refresh_token);
		
		if (tokens == null) {
			return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body(new WrapperResponse(null, new Outcome(HttpStatus.BAD_REQUEST, "Errore nel refreshing del token", Type.ERROR, null, Widget.ALERT)));
		}
					
		return ResponseEntity
						.status(HttpStatus.OK)
						.body(new WrapperResponse(tokens, null));
	}
	
	@ApiOperation(value = "Checks the forgotten password recovery request")
	@PostMapping(value = "/forgot_password", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<?> checkForgottenPassword(@RequestBody AccessParameters access_params,
									     			@RequestHeader String company_id) {
		
		String checkPasswordResponse = loginService.checkForgottenPassword(access_params.getUsername(), access_params.getEmail(), company_id);
		
		if (checkPasswordResponse != null) {
			return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body(new WrapperResponse(null, new Outcome(HttpStatus.BAD_REQUEST, checkPasswordResponse, Type.ERROR, null, Widget.ALERT)));
		}
		
		return ResponseEntity
					.status(HttpStatus.OK)
					.body(new WrapperResponse(null, new Outcome(HttpStatus.OK, "Recupero password avvenuto con successo. Controlla la tua posta elettronica.", null, null, null)));
	}
}