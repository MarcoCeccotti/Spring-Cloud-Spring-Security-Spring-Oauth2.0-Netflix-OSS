package it.marco.stone.dao.stone;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.marco.marco.db.schema.DBSchema;
import it.marco.stone.bean.stone.Stone_inspection_details;

@Repository
public interface Serial_number_DAO extends JpaRepository<Stone_inspection_details, Integer> {

	@Cacheable(value = "serialNumber", key = "#serial_number", sync = true)
	@Query(value = "SELECT tbl.id as id, tbl.code as code, tbl.store_item_id as store_item_id, tbl.store_item_first_id as store_item_first_id, tbl.store_item_root_id as store_item_root_id " + 
				   ", case when blk.block_number is not null then 'B' else 'L' end as block_slab " + 
				   ", case when blk.block_number is not null then blk.block_number else sla.block_number end as block_number " + 
				   ", case when blk.block_number is not null then blk.cut_number   else sla.cut_number   end as cut_number " + 
				   ", tbl.store_item_root_serial as store_item_root_serial " +
				   ", case when blk.block_number is not null then blk.sell_dim1 else sla.sell_dim1 end as sell_dim1 " + 
				   ", case when blk.block_number is not null then blk.sell_dim2 else sla.sell_dim2 end as sell_dim2 " + 
				   ", case when blk.block_number is not null then blk.sell_dim3 else sla.sell_dim3 end as sell_dim3 " + 
				   ", case when blk.block_number is not null then blk.sell_mq   else sla.sell_mq   end as sell_mq " +
				   ", tsq.id         as tsq_id " +
				   ", tsq.title      as tsq_title " +
				   ", tsq.stone_type as tsq_stone_type " +
				   ", tsf.id         as tsf_id " + 
				   ", tsf.title      as tsf_title " +
				   ", tss.id         as tss_id " +
				   ", tss.title      as tss_title " +
				   ", sup.id         as sup_id " +
				   ", sup.title      as sup_title " +
				   ", prj.code       as prj_code " +
				   ", prj.title      as prj_title " +
				   "FROM      " + DBSchema.company_schema + ".store_item_serialnumber tbl " + 
				   "LEFT JOIN " + DBSchema.company_schema + ".store_item_data_stoneblock  blk on blk.store_item_id = tbl.store_item_id " + 
				   "LEFT JOIN " + DBSchema.company_schema + ".store_item_data_stoneslab   sla on sla.store_item_id = tbl.store_item_id " + 
				   "LEFT JOIN " + DBSchema.company_schema + ".store_item                  itm on itm.id            = tbl.store_item_id " + 
				   "LEFT JOIN " + DBSchema.company_schema + ".tab_stone_finish            tsf on tsf.id = case when blk.block_number is not null then blk.tab_stone_finish_id    else sla.tab_stone_finish_id end " + 
				   "LEFT JOIN " + DBSchema.company_schema + ".tab_stone_quality           tsq on tsq.id = case when blk.block_number is not null then blk.tab_stone_quality_id   else sla.tab_stone_quality_id end " + 
				   "LEFT JOIN " + DBSchema.company_schema + ".tab_stone_selection         tss on tss.id = case when blk.block_number is not null then blk.tab_stone_selection_id else sla.tab_stone_selection_id end " + 
				   "LEFT JOIN " + DBSchema.company_schema + ".coan_divisioni              cdv  on cdv.id = itm.business_division " + 
				   "LEFT JOIN " + DBSchema.company_schema + ".store_item_cost_sheet_total cst  on cst.store_item_id = itm.id " + 
				   "LEFT JOIN " + DBSchema.company_schema + ".account_chart               sup  on sup.id = itm.supplier_id " + 
				   "LEFT JOIN " + DBSchema.company_schema + ".prj_activity                prj  on prj.id = itm.activity_id " + 
				   "WHERE 1=1 " + 
				   "AND tbl.id = :serial_number " + 
				   "ORDER BY tbl.code, tbl.id ", nativeQuery = true)
	public Stone_inspection_details getSerialNumberData(@Param("serial_number") int serial_number);
}