package it.marco.stone.dao.stone;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.marco.stone.bean.stone.Prj_activity;

@Repository
public interface Prj_activity_DAO extends JpaRepository<Prj_activity, Integer> {
	
}