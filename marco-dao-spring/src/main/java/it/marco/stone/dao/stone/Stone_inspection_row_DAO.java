package it.marco.stone.dao.stone;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.marco.stone.bean.stone.Stone_inspection_row;

@Repository
public interface Stone_inspection_row_DAO extends JpaRepository<Stone_inspection_row, Integer> {
	
	public Stone_inspection_row findByUuid(String uuid);
	
	public List<Stone_inspection_row> findByStoneInspectionHeadId(int stone_inspection_head_id);
}