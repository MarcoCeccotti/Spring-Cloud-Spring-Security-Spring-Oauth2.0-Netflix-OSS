package it.marco.marco.dao.mail;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.marco.marco.bean.mail.Mail_server;

@Repository
public interface Mail_server_DAO extends JpaRepository<Mail_server, Integer> {
	
	public List<Mail_server> findBySentAndCompany(boolean sent, String company);
	
	@Query(value = "SELECT m " + 
			   	   "FROM Mail_server m " + 
			   	   "WHERE m.sent = :sent " +
			   	   "AND m.company IN :companies ")
	public List<Mail_server> findBySentAndCompanies(boolean sent, List<String> companies);
}