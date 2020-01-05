package it.marco.gateway.service.gateway;

public interface AttemptService {
	
	/**
	 * The user did a good login, the ip/username (the key on the argument) will be removed from bad attempts cache
	 * @param key the key to remove from bad attempts cache
	 */
	public void loginSucceeded(String key);

    /**
     * If it's the first bad attempt intializes the key to 0 bad attempts; if not, increases the bad attempts.
     * @param key the key to check
     * @return true if the threshold of attempts is reached, false otherwise.
     */
    public boolean loginFailed(String key);
 
    /**
     * Checks if the username/ip is banned.
     * @param key the key to check
     * @return true if the key is banned, false otherwise.
     */
    public boolean isBlocked(String key);
}
