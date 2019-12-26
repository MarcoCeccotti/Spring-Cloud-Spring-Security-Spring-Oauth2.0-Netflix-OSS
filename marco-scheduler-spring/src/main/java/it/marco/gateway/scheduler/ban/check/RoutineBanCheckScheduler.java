package it.marco.gateway.scheduler.ban.check;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import it.marco.gateway.service.gateway.BlackListService;
import it.marco.marco.service.log.LogService;
import it.marco.marco.utils.utilities.LogUtils;

@Service
public class RoutineBanCheckScheduler {
	
	@Inject
	private BlackListService blackListService;
	
	@Inject
	private LogService logService;
	
	@PostConstruct
	private void init() {
		
		String method_name = new Throwable().getStackTrace()[0].getMethodName();
			
		String message = "Prelevo utenti bannati";
		logService.printAndSaveSchedulerLog(LogUtils.INFO, message, this.getClass().getSimpleName(), this.getClass().getName(), method_name, message);
		
		blackListService.firstRetrieve();
	}

	@Scheduled(initialDelay = 15000, fixedRate = 30000)
    public void blackListRoutine() {

		String method_name = new Throwable().getStackTrace()[0].getMethodName();
		
		String message = "Esecuzione routine controllo blacklist";
		logService.printAndSaveSchedulerLog(LogUtils.INFO, message, this.getClass().getSimpleName(), this.getClass().getName(), method_name, message);
		
		blackListService.executeRoutine();
    }
	
	@Scheduled(initialDelay = 20000, fixedRate = 30000)
    public void unbanCheck() {
		
		String method_name = new Throwable().getStackTrace()[0].getMethodName();

		String message = "Controllo aggiornamento elenco utenti bannati";
		logService.printAndSaveSchedulerLog(LogUtils.INFO, message, this.getClass().getSimpleName(), this.getClass().getName(), method_name, message);
		
		blackListService.checkToUnban();
    }
}