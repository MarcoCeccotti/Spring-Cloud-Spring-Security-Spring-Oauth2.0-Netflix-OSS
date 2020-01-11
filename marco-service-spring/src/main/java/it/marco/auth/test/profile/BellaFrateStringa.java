package it.marco.auth.test.profile;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class BellaFrateStringa implements ProvaService {

	@Override
	public String provaStringa() {
		return "BELLA FRATE!!!";
	}

}
