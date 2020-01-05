package it.marco.auth.service.auth;

public interface LogoutService {

	/**
	 * Logouts an authenticated user.
	 * @return a ResponseEntity object.
	 */
	public String logout();
}
