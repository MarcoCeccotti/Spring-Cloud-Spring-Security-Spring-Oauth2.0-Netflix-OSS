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
 
    @Override
    public void loginSucceeded(String key) {
        this.badAttemptsCache.invalidate(key);
    }

    @Override
    public boolean loginFailed(String key) {
        try {
            this.badAttemptsCache.put(key, this.badAttemptsCache.get(key) + 1);
            return this.isBlocked(key);
        } catch (Exception e) {
            return true;
        }
    }

	@Override
    public boolean isBlocked(String key) {
    	try {
            return this.badAttemptsCache.get(key) >= max_bad_attempts;
        } catch (Exception e) {
            return false;
        }
    }
}
