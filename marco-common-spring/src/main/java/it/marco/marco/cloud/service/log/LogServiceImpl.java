package it.marco.marco.cloud.service.log;

import java.sql.Timestamp;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.marco.marco.bean.log.Action_log;
import it.marco.marco.bean.log.Login;
import it.marco.marco.bean.log.Login_failed;
import it.marco.marco.bean.log.Scheduler_log;
import it.marco.marco.bean.log.Stone_log;
import it.marco.marco.cloud.session.Session_infos;
import it.marco.marco.dao.log.Action_log_DAO;
import it.marco.marco.dao.log.Login_DAO;
import it.marco.marco.dao.log.Login_failed_DAO;
import it.marco.marco.dao.log.Scheduler_log_DAO;
import it.marco.marco.dao.log.Stone_log_DAO;
import it.marco.marco.utils.utilities.LogUtils;

@Service
public class LogServiceImpl implements LogService {

	@Inject
	private Action_log_DAO action_log_DAO;
	@Inject
	private Stone_log_DAO stone_log_DAO;
	@Inject
	private Scheduler_log_DAO scheduler_log_DAO;
	@Inject
	private Login_DAO login_DAO;
	@Inject
	private Login_failed_DAO login_failed_DAO;
	
	@Inject
	private Session_infos session_infos;
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@Override
	public void printLog(String log_level, String log_message) {
		
		switch(log_level) {
		
			case LogUtils.INFO:
				log.info(log_message);
			break;
			
			case LogUtils.WARNING:
				log.warn(log_message);
			break;
			
			case LogUtils.ERROR:
				log.error(log_message);
			break;
			
			case LogUtils.FATAL:
				log.fatal(log_message);
			break;
			
			default:
				log.debug(log_message);
		}
	}

	@Override
	public void saveLoginLog() {
		
		this.login_DAO.save(new Login(session_infos.getUsername(), session_infos.getSession(), session_infos.getClient_ip(), session_infos.getUser_agent(), session_infos.getRemote_host()));
	}

	@Override
	public void printAndSaveLoginLog(String log_level, String log_message) {
		
		this.printLog(log_level, log_message);
		this.saveLoginLog();
	}

	@Override
	public void saveLoginFailedLog(String password) {
		
		this.login_failed_DAO.save(new Login_failed(session_infos.getUsername(), password, session_infos.getSession(), session_infos.getClient_ip(), session_infos.getUser_agent(), session_infos.getRemote_host()));
	}

	@Override
	public void printAndSaveLoginFailedLog(String log_level, String log_message, String password) {
		
		this.printLog(log_level, log_message);
		this.saveLoginFailedLog(password);
	}

	@Override
	public void saveActionLog(String action_name, String action_method, String log_json) {
		
		this.action_log_DAO.save(new Action_log(session_infos.getUsername(), session_infos.getSession(), action_name, action_method, log_json));
	}

	@Override
	public void printAndSaveActionLog(String log_level, String log_message, String action_name, String action_method, String log_json) {
		
		this.printLog(log_level, log_message);
		this.saveActionLog(action_name, action_method, log_json);
	}


	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveStoneLog(String action_name, String action_method, String error, String log_json) {
		
		Stone_log stone_log = new Stone_log(session_infos.getTime_st(), session_infos.getUsername(), session_infos.getSession(), action_name, action_method, error, log_json);
		
		stone_log_DAO.save(stone_log);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void printAndSaveStoneLog(String log_level, String log_message, String action_name, String action_method, String error, String log_json) {

		this.printLog(log_level, log_message);
		this.saveStoneLog(action_name, action_method, error, log_json);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveSchedulerLog(String action_method, String action_name, String log_json, String scheduler_name) {
		
		Scheduler_log scheduler_log = new Scheduler_log(action_method, action_name, log_json, scheduler_name, new Timestamp(System.currentTimeMillis()));
		
		scheduler_log_DAO.save(scheduler_log);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void printAndSaveSchedulerLog(String log_level, String log_message, String scheduler_name, String action_name, String action_method, String log_json) {
		
		this.printLog(log_level, log_message);
		this.saveSchedulerLog(action_method, action_name, log_json, scheduler_name);
	}
}
