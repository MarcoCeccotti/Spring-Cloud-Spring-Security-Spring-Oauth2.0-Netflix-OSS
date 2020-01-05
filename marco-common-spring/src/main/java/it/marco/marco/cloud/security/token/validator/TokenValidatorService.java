package it.marco.marco.cloud.security.token.validator;

public interface TokenValidatorService {

	/**
	 *  TODO DA COMPLETARE
	 * @param token
	 * @param tokenType
	 * @return true if everything goes right
	 */
	public boolean validateToken(String token, String tokenType);
}
