package it.marco.marco.dao.log;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.marco.marco.bean.log.Login_failed;

@Repository
public interface Login_failed_DAO extends JpaRepository<Login_failed, Integer> {
	
}
