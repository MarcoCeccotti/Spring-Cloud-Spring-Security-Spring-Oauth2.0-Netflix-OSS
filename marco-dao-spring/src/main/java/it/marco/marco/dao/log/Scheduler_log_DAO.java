package it.marco.marco.dao.log;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.marco.marco.bean.log.Scheduler_log;

@Repository
public interface Scheduler_log_DAO extends JpaRepository<Scheduler_log, Integer> {
	
}
