package it.marco.products.service.product;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import it.marco.marco.cloud.service.log.LogService;
import it.marco.marco.utils.utilities.LogUtils;
import it.marco.products.bean.product.Product;
import it.marco.products.dao.product.Products_DAO;

@Service
public class ProductServiceImpl implements ProductService {

	@Inject
	private LogService logService;
	
	@Inject
	private Products_DAO products_DAO;
	
	@Override
	public Product getProduct(int id) {
		
		String method_name = new Throwable().getStackTrace()[0].getMethodName();
		
		logService.printAndSaveActionLog(LogUtils.INFO, "Effettuata richiesta prodotto " + id, this.getClass().getName(), method_name, "Effettuata richiesta prodotto " + id);
		
		return products_DAO.findById(id).get();
	}

	@Override
	public List<Product> getProducts() {
		
		String method_name = new Throwable().getStackTrace()[0].getMethodName();
		
		logService.printAndSaveActionLog(LogUtils.INFO, "Effettuata richiesta prodotti", this.getClass().getName(), method_name, "Effettuata richiesta prodotti");
		
		return products_DAO.findAllByOrderById();
	}
	
	@Override
	public Product saveProduct(Product product) {
		
		String method_name = new Throwable().getStackTrace()[0].getMethodName();
		
		logService.printAndSaveActionLog(LogUtils.INFO, "Salvataggio nuovo prodotto", this.getClass().getName(), method_name, "Salvataggio nuovo prodotto");
		
		return products_DAO.save(product);
	}
	
	@Override
	public int deleteProduct(int id) {
		
		String method_name = new Throwable().getStackTrace()[0].getMethodName();
		
		logService.printAndSaveActionLog(LogUtils.INFO, "Cancellazione prodotto con id " + id, this.getClass().getName(), method_name, "Cancellazione prodotto");
		
		products_DAO.deleteById(id);
		
		return id;
	}

}
