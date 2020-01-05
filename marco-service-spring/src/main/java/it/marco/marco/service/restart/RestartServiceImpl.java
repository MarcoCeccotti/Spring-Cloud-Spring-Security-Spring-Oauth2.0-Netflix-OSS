package it.marco.marco.service.restart;

import javax.inject.Inject;

import org.springframework.cloud.context.restart.RestartEndpoint;
import org.springframework.stereotype.Service;

@Service
public class RestartServiceImpl implements RestartService {
     
    @Inject
    private RestartEndpoint restartEndpoint;
     
    public void restartApp() {
        restartEndpoint.restart();
    }
}
