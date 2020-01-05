package it.marco.stone.dao.stone;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.marco.stone.bean.stone.Tab_stone_type;

@Repository
public interface Tab_stone_type_DAO extends JpaRepository<Tab_stone_type, String> {
	
	List<Tab_stone_type> findByCodeOrCode(String code1, String code2);
}