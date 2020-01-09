package it.marco.products.service.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import it.marco.marco.cloud.service.log.LogService;
import it.marco.marco.utils.utilities.LogUtils;
import it.marco.products.bean.product.Product;
import it.marco.products.bean.product.Shopping;
import it.marco.products.dao.product.Products_DAO;
import it.marco.products.dao.product.Shopping_DAO;

@Service
public class ShoppingServiceImpl implements ShoppingService {

	private LogService logService;
	
	private Products_DAO products_DAO;
	private Shopping_DAO shopping_DAO;
	
	public ShoppingServiceImpl(LogService logService, Products_DAO products_DAO, Shopping_DAO shopping_DAO) {
		this.logService = logService;
		this.products_DAO = products_DAO;
		this.shopping_DAO = shopping_DAO;
	}
	
	@Override
	public List<Product> getShoppingByUsernameAndBought(String username, boolean bought) {
		
		String method_name = new Throwable().getStackTrace()[0].getMethodName();
		String msg_log = "Effettuata richiesta prodotti " + (bought ? "comprati da " : "del carrello per ") + username;
		logService.printAndSaveActionLog(LogUtils.INFO, msg_log, this.getClass().getName(), method_name, msg_log);
		
		List<Shopping> shoppingList = shopping_DAO.findAllByUsernameAndBought(username, bought);
		
		List<Product> productsList = new ArrayList<Product>();
		for (Shopping shopping: shoppingList) {
			Optional<Product> product = products_DAO.findById(shopping.getProductId());
			if (product.isPresent()) {
				productsList.add(product.get());
			}
		}
		
		return productsList;
	}

	@Override
	public Shopping saveShoppingItemByUsernameAndBought(String username, boolean bought, Product product) {
		
		String method_name = new Throwable().getStackTrace()[0].getMethodName();
		String msg_log = "Salvataggio nuovo prodotto " + product.getName() + " nello shopping per " + username;
		logService.printAndSaveActionLog(LogUtils.INFO, msg_log, this.getClass().getName(), method_name, msg_log);
		
		Shopping shopping = new Shopping(username, bought, product.getId());
		
		return shopping_DAO.save(shopping);
	}

	@Override
	public Product deleteShoppingItemByUsernameAndBought(String username, boolean bought, Product product) {
		
		String method_name = new Throwable().getStackTrace()[0].getMethodName();
		String msg_log = "Cancellazione prodotto nello shopping per " + username;
		logService.printAndSaveActionLog(LogUtils.INFO, msg_log, this.getClass().getName(), method_name, msg_log);
		
		shopping_DAO.delete(shopping_DAO.findByUsernameAndBoughtAndProductId(username, bought, product.getId()));
		
		return product;
	}

	@Override
	public Product buyShoppingItemByUsernameAndBought(String username, boolean bought, Product product) {
		
		String method_name = new Throwable().getStackTrace()[0].getMethodName();
		String msg_log = "Acquisto nuovo prodotto " + product.getName() + " dal carrello per " + username;
		logService.printAndSaveActionLog(LogUtils.INFO, msg_log, this.getClass().getName(), method_name, msg_log);
		
		List<Shopping> productsToBuy = shopping_DAO.findAllByUsernameAndBoughtAndProductId(username, bought, product.getId());
		
		for (Shopping toShop: productsToBuy) {
			Optional<Product> prodToEdit = products_DAO.findById(product.getId());
			if (prodToEdit.isPresent()) {
				prodToEdit.get().setQuantity(prodToEdit.get().getQuantity() - 1);
				products_DAO.save(prodToEdit.get());
			}
			toShop.setBought(true);
			shopping_DAO.save(toShop);
		}
		
		return product;
	}

	@Override
	public List<Product> buyAllShoppingItemByUsernameAndBought(String username, boolean bought, List<Product> products) {
		
		String method_name = new Throwable().getStackTrace()[0].getMethodName();
		String msg_log = "Acquistati tutti i prodotti dal carrello per " + username;
		logService.printAndSaveActionLog(LogUtils.INFO, msg_log, this.getClass().getName(), method_name, msg_log);
		
		for (Product product: products) {
			this.buyShoppingItemByUsernameAndBought(username, bought, product);
		}
		
		return products;
	}
}
