package it.marco.products.dao.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.marco.products.bean.product.Product;

@Repository
public interface Products_DAO extends JpaRepository<Product, Integer> {

	public List<Product> findAllByOrderById();
}
