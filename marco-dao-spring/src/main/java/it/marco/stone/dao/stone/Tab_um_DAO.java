package it.marco.stone.dao.stone;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.marco.stone.bean.stone.Tab_um;

@Repository
public interface Tab_um_DAO extends JpaRepository<Tab_um, String> {
	
	List<Tab_um> findByIdOrIdOrIdOrId(String id1, String id2, String id3, String id4);
}