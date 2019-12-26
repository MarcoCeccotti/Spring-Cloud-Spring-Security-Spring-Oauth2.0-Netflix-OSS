package it.marco.stone.dao.stone;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.marco.marco.db.schema.DBSchema;
import it.marco.stone.bean.stone.Partner_account;

@Repository
public interface Supplier_DAO extends JpaRepository<Partner_account, String> {
	
	@Query(value = "SELECT pt.account_id as account_id, cp.name as cust_supp_account_name " +
 			 	   "FROM 	  " + DBSchema.company_schema + ".com_partner_account pt " +
	 			   "LEFT JOIN " + DBSchema.company_schema + ".com_partner cp on cp.id = pt.partner_id " +
	 			   "LEFT JOIN " + DBSchema.company_schema + ".account_chart ac on ac.id = pt.account_id " +
	 			   "WHERE ac.partner_type = 'F' " +
	 			   "ORDER BY cust_supp_account_name", nativeQuery = true)
	public List<Partner_account> getSuppliers();
	
	@Query(value = "SELECT pt.account_id as account_id, cp.name as cust_supp_account_name " +
	 	   	   	   "FROM 	  " + DBSchema.company_schema + ".com_partner_account pt " +
	 	   	   	   "LEFT JOIN " + DBSchema.company_schema + ".com_partner cp on cp.id = pt.partner_id " +
	 	   	   	   "LEFT JOIN " + DBSchema.company_schema + ".account_chart ac on ac.id = pt.account_id " +
	 	   	   	   "WHERE ac.partner_type = 'F' " +
	 	   	   	   "AND pt.account_id = :supplier_id ", nativeQuery = true)
	public Partner_account checkSupplier(@Param("supplier_id") String supplier_id);
}