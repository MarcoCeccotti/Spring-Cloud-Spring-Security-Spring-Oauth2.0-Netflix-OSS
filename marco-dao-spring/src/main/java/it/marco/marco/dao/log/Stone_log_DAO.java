package it.marco.marco.dao.log;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.marco.marco.bean.log.Stone_log;

@Repository
public interface Stone_log_DAO extends JpaRepository<Stone_log, Integer> {
	
}
