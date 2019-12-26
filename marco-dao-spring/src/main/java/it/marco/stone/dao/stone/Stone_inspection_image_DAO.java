package it.marco.stone.dao.stone;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.marco.stone.bean.stone.Stone_inspection_image;

@Repository
public interface Stone_inspection_image_DAO extends JpaRepository<Stone_inspection_image, Integer> {
	
	public List<Stone_inspection_image> findByStoneInspectionHeadUuid(String stone_inspection_head_uuid);

	public List<Stone_inspection_image> findByStoneInspectionHeadUuidOrderByInsertTime(String stone_inspection_head_uuid);
	
	public List<Stone_inspection_image> findByStoneInspectionHeadUuidAndOverlayPathIsNotNull(String stone_inspection_head_uuid);
	
	public Stone_inspection_image findByOverlayPath(String overlay_path);
	
	@Query(value = "SELECT i " + 
			   	   "FROM Stone_inspection_image i " + 
			   	   "WHERE i.uuid IN :uuids")
	public List<Stone_inspection_image> findAllByUuids(List<String> uuids);
}
