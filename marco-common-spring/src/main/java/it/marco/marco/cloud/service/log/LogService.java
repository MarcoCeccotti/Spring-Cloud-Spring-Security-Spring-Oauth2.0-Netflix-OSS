package it.marco.marco.cloud.service.log;

public interface LogService {
	
	/**
	 * Prints the log.
	 * @param log_level the level of the log. Advise is to use the utility class LogUtils to set it.
	 * @param log_message the massage of the log
	 */
	public void printLog(String log_level, String log_message);
	
	/**
	 * Saves a Login object on db.
	 */
	public void saveLoginLog();
	
	/**
	 * Prints the log and saves a LoginLog object on db.
	 * @param log_level the level of the log. Advise is to use the utility class LogUtils to set it.
	 * @param log_message the massage of the log
	 */
	public void printAndSaveLoginLog(String log_level, String log_message);

	/**
	 * Saves a Login_failed object on db.
	 * @param passwd the password of the user that tried to login
	 */
	public void saveLoginFailedLog(String passwd);
	
	/**
	 * Prints the log and saves a Login_failed object on db.
	 * @param log_level the level of the log. Advise is to use the utility class LogUtils to set it.
	 * @param log_message the massage of the log
	 * @param password the password of the user that tried to login
	 */
	public void printAndSaveLoginFailedLog(String log_level, String log_message, String password);
	
	/**
	 * Saves an Action_log object on db.
	 * @param action_name the name of the action
	 * @param action_method the name of the method
	 * @param log_json the json of the action
	 */
	public void saveActionLog(String action_name, String action_method, String log_json);
	
	/**
	 * Prints the log and saves an Action_log object on db.
	 * @param log_level the level of the log. Advise is to use the utility class LogUtils to set it.
	 * @param log_message the massage of the log
	 * @param action_name the name of the action
	 * @param action_method the name of the method
	 * @param log_json the json of the action
	 */
	public void printAndSaveActionLog(String log_level, String log_message, String action_name, String action_method, String log_json);
	
	/**
	 * Saves the log on db.
	 * @param action_name name of the action
	 * @param action_method name of the method
	 * @param error the error of the action, if any
	 * @param log_json json of the request
	 */
	public void saveStoneLog(String action_name, String action_method, String error, String log_json);
	
	/**
	 * Prints and saves the log about Stone.
	 * @param log_level the level of the log. Advise is to use the utility class LogUtils to set it.
	 * @param log_message the massage of the log
	 * @param action_name name of the action
	 * @param action_method name of the method
	 * @param error the error of the action, if any
	 * @param log_json json of the request
	 */
	public void printAndSaveStoneLog(String log_level, String log_message, String action_name, String action_method, String error, String log_json);
	
	/**
	 * Saves the log of the scheduler on db. It is used to distinguish the normal flux of a request from the scheduler one.
	 * @param scheduler_name class name of the scheduler
	 * @param action_name name of the action
	 * @param action_method name of the method
	 * @param log_json json of the request
	 */
	public void saveSchedulerLog(String scheduler_name, String action_name, String action_method, String log_json);
	
	/**
	 * Prints and saves the log about scheduler.
	 * @param log_level the level of the log. Advise is to use the utility class LogUtils to set it.
	 * @param log_message the message of the log
	 * @param scheduler_name the name of the scheduler class
	 * @param action_name the name of the action
	 * @param action_method the name of the method
	 * @param log_json the json of the action. it can be an empty string
	 */
	public void printAndSaveSchedulerLog(String log_level, String log_message, String scheduler_name, String action_name, String action_method, String log_json);
}
