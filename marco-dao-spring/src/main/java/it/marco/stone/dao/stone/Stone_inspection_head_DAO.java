package it.marco.stone.dao.stone;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.marco.stone.bean.stone.Stone_inspection_head;

@Repository
public interface Stone_inspection_head_DAO extends JpaRepository<Stone_inspection_head, Integer> {
	
	public Stone_inspection_head findByUuid(String uuid);
	
	@Query(value = "SELECT h " + 
				   "FROM Stone_inspection_head h " + 
				   "WHERE h.uuid IN :uuids")
	public List<Stone_inspection_head> findAllByUuids(List<String> uuids);
    
    @Query(value = "SELECT h, r " + 
    			   "FROM Stone_inspection_head h " +
    			   "LEFT JOIN Stone_inspection_row r ON r.stoneInspectionHeadId = h.id " +
    			   "WHERE h.id = :head_id")
    public List<Object[]> findInspectionById(@Param("head_id") int head_id);
    
    @Query(value = "SELECT h, r " + 
    			   "FROM Stone_inspection_head h " +
		   	   	   "LEFT JOIN Stone_inspection_row r ON r.stoneInspectionHeadId = h.id " +
		   	       "WHERE h.uuid IN :head_uuid " + 
		   	   	   "ORDER BY r.block_number")
    public List<Object[]> findInspectionByUuidAndOrderByBlockNumber(@Param("head_uuid") String head_uuid);
}