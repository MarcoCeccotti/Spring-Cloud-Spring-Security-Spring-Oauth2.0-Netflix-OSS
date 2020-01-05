package it.marco.marco.cloud.config.ribbon;

import org.springframework.context.annotation.Bean;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.BestAvailableRule;
import com.netflix.loadbalancer.IRule;

public class CloudRibbonConfig {
	
    @Bean
    public IRule ribbonRule(IClientConfig config) {
        return new BestAvailableRule();
    }
}