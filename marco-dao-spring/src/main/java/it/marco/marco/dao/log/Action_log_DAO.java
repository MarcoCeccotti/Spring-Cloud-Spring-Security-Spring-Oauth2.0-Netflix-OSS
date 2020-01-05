package it.marco.marco.dao.log;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.marco.marco.bean.log.Action_log;

@Repository
public interface Action_log_DAO extends JpaRepository<Action_log, Integer> {
	
}
