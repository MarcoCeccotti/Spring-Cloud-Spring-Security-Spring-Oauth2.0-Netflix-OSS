package it.marco.stone.dao.stone;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.marco.stone.bean.stone.Stone_inspection_inspector;

@Repository
public interface Stone_inspection_inspector_DAO extends JpaRepository<Stone_inspection_inspector, Integer> {
	
}