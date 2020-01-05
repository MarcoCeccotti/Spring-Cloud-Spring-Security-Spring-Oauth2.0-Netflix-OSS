package it.marco.gateway.service.gateway;

public interface BlackListService {
	
	/**
	 * Executes the routine about retrieve data from db, save new banned users etc..
	 */
	public void executeRoutine();

	/**
	 * Retrieves data from db at boot of the application. blackList is populated.
	 */
	public void firstRetrieve();

	/**
	 * Checks if the username or ip was already or not registered.
	 * @param username the username to check
	 * @param ip the ip to check
	 * @return true if it was already registered; false otherwise.
	 */
	public boolean isBanned(String ip, String username);
	
	/**
	 * Saves the new banned username/ip on the auxBlackList and on db.
	 * @param username the username to be banned
	 * @param token the token to be banned
	 * @param ip the ip to be banned
	 */
	public void addBanned(String username, String token, String ip, String reason);
	
	/**
	 * Unbans all the users banned for login attempts failed. The users baned in that way have a specfic ban reason.
	 */
	public void checkToUnban();
}