package it.marco.auth.service.auth;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import it.marco.auth.bean.auth.Company;
import it.marco.auth.bean.auth.Users;
import it.marco.auth.dao.auth.Company_DAO;
import it.marco.auth.dao.auth.Users_DAO;
import it.marco.marco.cloud.service.log.LogService;
import it.marco.marco.utils.utilities.LogUtils;

@Service
public class UserServiceImpl implements UserService {
	
	@Inject
	private Users_DAO users_DAO;
	@Inject
	private Company_DAO company_DAO;
	
	@Inject
	private LogService logService;
	
	@Override
	@Cacheable(value = "getUser", key = "#user_tag", sync = true)
	public Optional<Users> getUser(String user_tag) {

		String method_name = new Throwable().getStackTrace()[0].getMethodName();
		
		Optional<Users> user = users_DAO.findById(user_tag);
		
		if (!user.isPresent()) {
			logService.printAndSaveActionLog(LogUtils.ERROR, "Utente " + user_tag + "non trovato", this.getClass().getName(), method_name, "");
			
			return null;
		}
		
		return user;
	}

	@Override
	@Cacheable(value = "getCompany", key = "#company_id", sync = true)
	public Optional<Company> getCompany(String company_id) {

		String method_name = new Throwable().getStackTrace()[0].getMethodName();
		
		Optional<Company> company = company_DAO.findById(company_id);
		
		if (!company.isPresent()) {
			logService.printAndSaveActionLog(LogUtils.ERROR, "Company " + company + "non trovata", this.getClass().getName(), method_name, "");
			
			return null;
		}
		
		return company;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean checkUserToRegistrate(Users user) {

		String method_name = new Throwable().getStackTrace()[0].getMethodName();
		
		String json_user = new Gson().toJson(user);
		logService.printAndSaveActionLog(LogUtils.INFO, "Controllo registrazione nuovo utente " + json_user, this.getClass().getName(), method_name, json_user);
		
		if (users_DAO.findById(user.getUsername().toLowerCase() + "@" + user.getCompany_id()).isPresent()) {
			
			String registrate_response = "Utente con id " + user.getUsername().toLowerCase() + "@" + user.getCompany_id() + " già presente nel db";
			logService.printAndSaveActionLog(LogUtils.ERROR, registrate_response, this.getClass().getName(), method_name, registrate_response + ": " + json_user);
			return false;
		}
		
		user.setUserTag(user.getUsername().toLowerCase() + "@" + user.getCompany_id());
		user.setPassword(BCrypt.hashpw(user.getPasswd(), BCrypt.gensalt(4)));
		users_DAO.save(user);
		
		logService.printAndSaveActionLog(LogUtils.INFO, "Registrato nuovo utente", this.getClass().getName(), method_name, json_user);
		
		return true;
	}
}
