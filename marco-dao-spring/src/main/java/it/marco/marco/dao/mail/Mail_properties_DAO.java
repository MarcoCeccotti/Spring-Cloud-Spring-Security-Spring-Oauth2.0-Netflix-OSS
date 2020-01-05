package it.marco.marco.dao.mail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.marco.marco.bean.mail.Mail_properties;

@Repository
public interface Mail_properties_DAO extends JpaRepository<Mail_properties, String> {
	
}
