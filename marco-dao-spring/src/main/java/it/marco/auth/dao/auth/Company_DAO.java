package it.marco.auth.dao.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.marco.auth.bean.auth.Company;

@Repository
public interface Company_DAO extends JpaRepository<Company, String> {
	
}
