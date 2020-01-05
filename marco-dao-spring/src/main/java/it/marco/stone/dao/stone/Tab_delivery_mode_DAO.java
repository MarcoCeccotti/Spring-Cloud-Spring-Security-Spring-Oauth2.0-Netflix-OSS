package it.marco.stone.dao.stone;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.marco.stone.bean.stone.Tab_delivery_mode;

@Repository
public interface Tab_delivery_mode_DAO extends JpaRepository<Tab_delivery_mode, String> {
	
}