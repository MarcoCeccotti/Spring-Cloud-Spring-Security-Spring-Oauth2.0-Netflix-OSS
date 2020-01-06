package it.marco.marco.restart.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import it.marco.marco.service.restart.RestartService;

@RestController
public class RestartController {
	
	private RestartService restartService;
	
	public RestartController(RestartService restartService) {
		this.restartService = restartService;
	}
	
	@ApiOperation(value = "Restarts the application")
	@PostMapping("/restart")
	public void restart() {
		this.restartService.restartApp();
	} 
}