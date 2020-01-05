package it.marco.stone.dao.stone;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.marco.stone.bean.stone.Tab_exchange;

@Repository
public interface Tab_exchange_DAO extends JpaRepository<Tab_exchange, String> {
	
	List<Tab_exchange> findByIdOrId(String id1, String id2);
}