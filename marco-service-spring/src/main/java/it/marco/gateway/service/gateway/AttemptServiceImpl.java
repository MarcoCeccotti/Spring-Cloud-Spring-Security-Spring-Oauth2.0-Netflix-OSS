package it.marco.gateway.service.gateway;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@Service
public class AttemptServiceImpl implements AttemptService {

	@Value("${max_bad_attempts}")
	private int max_bad_attempts;
    
    private LoadingCache<String, Integer> badAttemptsCache;
    
    @PostConstruct
    public void init() {
    	
        this.badAttemptsCache = CacheBuilder
			        				.newBuilder()
			    					.expireAfterWrite(1, TimeUnit.DAYS)
			    					.build(new CacheLoader<String, Integer>() {
			    						public Integer load(String key) {
			    							return 0;
			    						}
			    					});
    }
 
    public void loginSucceeded(String key) {
        this.badAttemptsCache.invalidate(key);
    }

    public boolean loginFailed(String key) {
    	
    	int badAttempts;
        try {
            badAttempts = this.badAttemptsCache.get(key);
        } catch (ExecutionException e) {
            badAttempts = 0;
        }
        
        this.badAttemptsCache.put(key, ++badAttempts);
        
        return this.isBlocked(key);
    }

    public boolean isBlocked(String key) {
    	
    	try {
            return this.badAttemptsCache.get(key) >= max_bad_attempts;
        } catch (ExecutionException e) {
            return false;
        }
    }
}
