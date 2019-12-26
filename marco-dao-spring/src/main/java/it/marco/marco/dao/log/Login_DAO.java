package it.marco.marco.dao.log;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.marco.marco.bean.log.Login;

@Repository
public interface Login_DAO extends JpaRepository<Login, Integer> {
	
}
