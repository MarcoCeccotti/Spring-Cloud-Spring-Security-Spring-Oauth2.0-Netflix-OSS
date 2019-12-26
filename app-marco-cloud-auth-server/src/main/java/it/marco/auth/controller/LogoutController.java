package it.marco.auth.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import it.marco.auth.service.auth.LogoutService;
import it.marco.marco.bean.http.Outcome;
import it.marco.marco.bean.http.Type;
import it.marco.marco.bean.http.Widget;
import it.marco.marco.bean.http.WrapperResponse;

@RestController
@RequestMapping(value = "/oauth")
public class LogoutController {
	
	@Inject
	private LogoutService logoutService;
	
	// logouts a specific user
	@ApiOperation(value = "Logouts an authenticated user.")
	@ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header")})
	@PostMapping(value = "/logout", produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<?> logout(HttpServletRequest request) {
		
		String logout_response = logoutService.logout();
		
		if (logout_response != null) {
			return ResponseEntity
						.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(new WrapperResponse(null, new Outcome(HttpStatus.INTERNAL_SERVER_ERROR, logout_response, Type.ERROR, null, Widget.ALERT)));
		}
		
		return ResponseEntity
					.status(HttpStatus.OK)
					.body(new WrapperResponse("Logout effettuato con successo", null));
	}
}