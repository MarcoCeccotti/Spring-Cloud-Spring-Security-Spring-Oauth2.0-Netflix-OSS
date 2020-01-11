package it.marco.auth.test.profile;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class CiaoMondoStringa implements ProvaService {

	@Override
	public String provaStringa() {
		return "CIAO MONDO!!!";
	}

}
