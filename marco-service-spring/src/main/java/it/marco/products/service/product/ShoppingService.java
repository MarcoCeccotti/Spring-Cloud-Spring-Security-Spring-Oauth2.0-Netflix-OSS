package it.marco.products.service.product;

import java.util.List;

import it.marco.products.bean.product.Product;
import it.marco.products.bean.product.Shopping;

public interface ShoppingService {

	/**
	 * TODO DA COMPLETARE
	 * @param username
	 * @param bought
	 * @return
	 */
	public List<Product> getShoppingByUsernameAndBought(String username, boolean bought);
	
	/**
	 * TODO DA COMPLETARE
	 * @param username
	 * @param bought
	 * @param product
	 * @return
	 */
	public Shopping saveShoppingItemByUsernameAndBought(String username, boolean bought, Product product);
	
	/**
	 * TODO DA COMPLETARE
	 * @param username
	 * @param bought
	 * @param product
	 * @return
	 */
	public Product deleteShoppingItemByUsernameAndBought(String username, boolean bought, Product product);
}
