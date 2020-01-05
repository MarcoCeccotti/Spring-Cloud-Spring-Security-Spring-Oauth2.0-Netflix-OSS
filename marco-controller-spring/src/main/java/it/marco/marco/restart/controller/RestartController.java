package it.marco.marco.restart.controller;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import it.marco.marco.service.restart.RestartService;

@RestController
public class RestartController {
	
	@Inject
	private RestartService restartService;
	
	@ApiOperation(value = "Restarts the application")
	@PostMapping("/restart")
	public void restart() {
		restartService.restartApp();
	} 
}