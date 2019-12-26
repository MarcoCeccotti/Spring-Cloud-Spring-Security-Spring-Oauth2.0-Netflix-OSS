package it.marco.auth.service.auth;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import it.marco.auth.bean.auth.Company;
import it.marco.auth.bean.auth.Users;
import it.marco.auth.dao.auth.Company_DAO;
import it.marco.auth.dao.auth.Users_DAO;
import it.marco.marco.service.log.LogService;
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
		
		Optional<Users> user = users_DAO.findById(user_tag);
		
		if (!user.isPresent()) {
			logService.printAndSaveActionLog(LogUtils.ERROR, "Utente " + user_tag + "non trovato", "UserServiceImpl", "getUser", "");
			
			return null;
		}
		
		return user;
	}

	@Override
	@Cacheable(value = "getCompany", key = "#company_id", sync = true)
	public Optional<Company> getCompany(String company_id) {
		
		Optional<Company> company = company_DAO.findById(company_id);
		
		if (!company.isPresent()) {
			logService.printAndSaveActionLog(LogUtils.ERROR, "Company " + company + "non trovata", "UserServiceImpl", "getCompany", "");
			
			return null;
		}
		
		return company;
	}
}
