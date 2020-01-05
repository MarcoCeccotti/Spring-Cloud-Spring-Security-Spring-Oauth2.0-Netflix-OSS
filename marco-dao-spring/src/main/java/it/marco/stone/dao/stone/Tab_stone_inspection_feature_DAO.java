package it.marco.stone.dao.stone;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.marco.stone.bean.stone.Tab_stone_inspection_feature;

@Repository
public interface Tab_stone_inspection_feature_DAO extends JpaRepository<Tab_stone_inspection_feature, Integer> {
	
}