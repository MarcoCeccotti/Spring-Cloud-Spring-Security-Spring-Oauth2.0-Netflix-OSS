package it.marco.auth.test.profile;

import org.springframework.stereotype.Service;

@Service
public class EffettivaStringa {

	private ProvaService provaStringa;
	
	public EffettivaStringa(ProvaService provaStringa) {
		this.provaStringa = provaStringa;
	}
	
	public String getStrigaEffettiva() {
		return this.provaStringa.provaStringa();
	}
}
