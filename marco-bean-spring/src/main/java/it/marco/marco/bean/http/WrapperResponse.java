package it.marco.marco.bean.http;

public class WrapperResponse {
	
	private Object payload;
	
	private Outcome outcome;
	
	public WrapperResponse(Object payload, Outcome outcome) {
		this.payload = payload;
		this.outcome = outcome;
	}

	public Object getPayload() {
		return payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}

	public Outcome getOutcome() {
		return outcome;
	}

	public void setOutcome(Outcome outcome) {
		this.outcome = outcome;
	}
}