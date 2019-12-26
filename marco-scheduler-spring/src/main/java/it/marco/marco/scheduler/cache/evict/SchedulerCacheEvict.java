package it.marco.marco.scheduler.cache.evict;

import javax.inject.Inject;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import it.marco.marco.service.log.LogService;
import it.marco.marco.utils.utilities.LogUtils;

@Service
public class SchedulerCacheEvict {
	
	@Inject
	private CacheManager cacheManager;
	
	@Inject
	private LogService logService;

	@Scheduled(cron = "0 0/1 * * * ?") // ogni 30 minuti
	public void evictAllCaches() {
		
		logService.printLog(LogUtils.INFO, "Inizio pulizia cache");
		
		cacheManager.getCacheNames()
					.stream()
					.map(cacheManager::getCache).forEach(Cache::clear);
	}
}
