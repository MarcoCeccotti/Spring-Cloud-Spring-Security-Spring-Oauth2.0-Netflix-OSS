package it.marco.gateway.service.gateway;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@Service
public class LoginAttemptServiceImpl implements LoginAttemptService {

	private final int MAX_BAD_ATTEMPTS = 3;
    
    private LoadingCache<String, Integer> badAttemptsCache;
    
    private int badAttempts;
 
    private LoginAttemptServiceImpl() {
    	super();
    	
        badAttemptsCache 
        		= CacheBuilder
        				.newBuilder()
    					.expireAfterWrite(1, TimeUnit.DAYS)
    					.build(new CacheLoader<String, Integer>() {
    						public Integer load(String key) {
    							return 0;
    						}
    					});
    }
 
    public void loginSucceeded(String key) {
        badAttemptsCache.invalidate(key);
    }

    public boolean loginFailed(String key) {
        try {
            badAttempts = badAttemptsCache.get(key);
        } catch (ExecutionException e) {
            badAttempts = 0;
        }
        
        badAttemptsCache.put(key, ++badAttempts);
        
        return isBlocked(key);
    }

    public boolean isBlocked(String key) {
    	try {
            return badAttemptsCache.get(key) >= MAX_BAD_ATTEMPTS;
        } catch (ExecutionException e) {
            return false;
        }
    }
}
