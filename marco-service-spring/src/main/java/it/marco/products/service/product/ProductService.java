package it.marco.products.service.product;

import java.util.List;

import it.marco.products.bean.product.Product;

public interface ProductService {

	/**
	 * Fetches a product based on its id.
	 * @param id the id of the product to fetch
	 * @return a Product object if the item exists, null otherwise.
	 */
	public Product getProduct(int id);
	
	/**
	 * Fetches all the products.
	 * @return the List of Product items fetched.
	 */
	public List<Product> getProducts();
	
	/**
	 * Saves a new product.
	 * @param product the product to save
	 * @return the Product item saved.
	 */
	public Product saveProduct(Product product);
	
	/**
	 * Deletes a specific product by its id.
	 * @param product the id of the Product item to delete
	 */
	public int deleteProduct(int id);
}
