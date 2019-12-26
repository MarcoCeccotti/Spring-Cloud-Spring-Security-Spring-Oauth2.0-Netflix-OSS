package it.marco.auth.service.auth;

import java.util.Optional;

import it.marco.auth.bean.auth.Company;
import it.marco.auth.bean.auth.Users;

public interface UserService {

	/**
	 * Retrieves an user. It should be retrieved after getting access token.
	 * @param username
	 * @return an Optional<Users> object. The Optional means that there's no certainty about the presence of the Users object from the fetching.
	 */
	public Optional<Users> getUser(String username);
	
	/**
	 * Retrives the Company object from db based on company_id value.
	 * @param company_id the id of the company
	 * @return an Optional<Company> object. The Optional means that there's no certainty about the presence of the Company object from the fetching.
	 */
	public Optional<Company> getCompany(String company_id);
}
