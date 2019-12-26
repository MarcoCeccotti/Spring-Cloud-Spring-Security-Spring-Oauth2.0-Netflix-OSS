package it.marco.marco.cloud.security.token;

public interface AuthAdapter {
	
	/**
	 * Gets the signin key to decode/uncode the access tokens
	 * @return the signin key String.
	 */
	public String getSigningKey();
}