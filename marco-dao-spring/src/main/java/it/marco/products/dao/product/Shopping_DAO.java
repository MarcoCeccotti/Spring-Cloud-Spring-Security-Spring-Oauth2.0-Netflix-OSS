package it.marco.products.dao.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.marco.products.bean.product.Shopping;

@Repository
public interface Shopping_DAO extends JpaRepository<Shopping, Integer> {

	public Shopping findByUsername(String username);
	
	public Shopping findByUsernameAndBoughtAndProductId(String username, boolean bought, int productId);
	
	public List<Shopping> findAllByUsernameAndBoughtAndProductId(String username, boolean bought, int productId);
	
	public List<Shopping> findAllByUsernameAndBought(String username, boolean bought);
}
