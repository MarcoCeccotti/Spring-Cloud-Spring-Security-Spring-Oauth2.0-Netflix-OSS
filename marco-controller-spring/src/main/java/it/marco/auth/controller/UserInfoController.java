package it.marco.auth.controller;

import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import it.marco.auth.bean.auth.Company;
import it.marco.auth.bean.auth.Users;
import it.marco.auth.service.auth.UserService;
import it.marco.marco.bean.http.Outcome;
import it.marco.marco.bean.http.Type;
import it.marco.marco.bean.http.Widget;
import it.marco.marco.bean.http.WrapperResponse;

@RestController
@RequestMapping(value = "/oauth/user")
public class UserInfoController {
	
	@Inject
	private UserService userService;
	
	@ApiOperation(value = "Retrieves the user information")
	@ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header")})
	@GetMapping(value = "/{username}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<?> getUser(@PathVariable String username,
									 @RequestHeader String company_id) {
		
		Optional<Users> user = userService.getUser(username.toLowerCase() + "@" + company_id);
		
		if (!user.isPresent()) {
			return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body(new WrapperResponse(null, new Outcome(HttpStatus.BAD_REQUEST, "Utente non trovato.", Type.ERROR, null, Widget.ALERT)));
		}
		
		return ResponseEntity
					.status(HttpStatus.OK)
					.body(new WrapperResponse(user.get(), new Outcome(HttpStatus.OK, null, null, null, null)));
	}
	
	@ApiOperation(value = "Retrieves the company based on user company id")
	@ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header")})
	@GetMapping(value = "/company/{company_id}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<?> getCompany(@PathVariable String company_id) {
		
		Optional<Company> company = userService.getCompany(company_id);
		
		if (!company.isPresent()) {
			return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body(new WrapperResponse(null, new Outcome(HttpStatus.BAD_REQUEST, "Company non trovata.", Type.ERROR, null, Widget.ALERT)));
		}
		
		return ResponseEntity
					.status(HttpStatus.OK)
					.body(new WrapperResponse(company.get(), new Outcome(HttpStatus.OK, null, null, null, null)));
	}
}
