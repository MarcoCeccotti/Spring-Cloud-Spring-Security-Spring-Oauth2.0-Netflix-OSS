package it.marco.stone.dao.stone;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.marco.stone.bean.stone.Coan_divisioni;

@Repository
public interface Coan_divisioni_DAO extends JpaRepository<Coan_divisioni, String> {
	
}