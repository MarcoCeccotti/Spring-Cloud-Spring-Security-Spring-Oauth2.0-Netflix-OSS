package it.marco.marco.dao.mail;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.marco.marco.bean.mail.Mail_attachment;

@Repository
public interface Mail_attachment_DAO extends JpaRepository<Mail_attachment, Integer> {
	
	public List<Mail_attachment> findAllByMailServerId(int mail_server_id);
}
