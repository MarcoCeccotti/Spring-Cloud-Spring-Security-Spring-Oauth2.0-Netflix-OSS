package it.marco.products.controller;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import it.marco.marco.bean.http.Outcome;
import it.marco.marco.bean.http.WrapperResponse;
import it.marco.products.bean.product.Product;
import it.marco.products.service.product.ShoppingService;

@RestController
@RequestMapping(value = "/products")
public class ShoppingController {
	
	private ShoppingService shoppingService;
	
	public ShoppingController(ShoppingService shoppingService) {
		this.shoppingService = shoppingService;
	}
	
	@ApiOperation(value = "Gets all the products related to a specific user. The bought path variable determines if he wants the bougth products or thats in the cart")
	@ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header")})
	@GetMapping(value = "/shopping/{username}/{bought}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<WrapperResponse> getShoppingCartByUsername(@PathVariable String username,
																	 @PathVariable boolean bought) throws Exception {
		
		return ResponseEntity
					.status(HttpStatus.OK)
					.body(new WrapperResponse(shoppingService.getShoppingByUsernameAndBought(username, bought), new Outcome(HttpStatus.OK, null, null, null, null)));
	}
	
	@ApiOperation(value = "Gets all the products related to a specific user. The bought path variable determines if he wants the bougth products or thats in the cart")
	@ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header")})
	@PostMapping(value = "/shopping/new/{username}/{bought}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<WrapperResponse> saveShoppingCartByUsername(@PathVariable String username,
																	  @PathVariable boolean bought,
																	  @RequestBody Product product) throws Exception {
		
		return ResponseEntity
					.status(HttpStatus.OK)
					.body(new WrapperResponse(shoppingService.saveShoppingItemByUsernameAndBought(username, bought, product), new Outcome(HttpStatus.OK, null, null, null, null)));
	}
	
	// TODO AGGIORNARE LA DESCRIZIONE DELLE API SWAGGER
	@ApiOperation(value = "Gets all the products related to a specific user. The bought path variable determines if he wants the bougth products or thats in the cart")
	@ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header")})
	@PostMapping(value = "/shopping/delete/{username}/{bought}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<WrapperResponse> deleteShoppingCartItemByUsername(@PathVariable String username,
																	  		@PathVariable boolean bought,
																	  		@RequestBody Product product) throws Exception {
		
		return ResponseEntity
					.status(HttpStatus.OK)
					.body(new WrapperResponse(shoppingService.deleteShoppingItemByUsernameAndBought(username, bought, product), new Outcome(HttpStatus.OK, null, null, null, null)));
	}
	
	@ApiOperation(value = "Gets all the products related to a specific user. The bought path variable determines if he wants the bougth products or thats in the cart")
	@ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header")})
	@PostMapping(value = "/shopping/buy/{username}/{bought}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<WrapperResponse> buyShoppingCartByUsername(@PathVariable String username,
																	 @PathVariable boolean bought,
																	 @RequestBody Product product) throws Exception {
		
		return ResponseEntity
					.status(HttpStatus.OK)
					.body(new WrapperResponse(shoppingService.buyShoppingItemByUsernameAndBought(username, bought, product), new Outcome(HttpStatus.OK, null, null, null, null)));
	}

	@ApiOperation(value = "Gets all the products related to a specific user. The bought path variable determines if he wants the bougth products or thats in the cart")
	@ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header")})
	@PostMapping(value = "/shopping/buy-all/{username}/{bought}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<WrapperResponse> buyAllShoppingCartByUsername(@PathVariable String username,
																	 	@PathVariable boolean bought,
																	 	@RequestBody List<Product> products) throws Exception {
		
		return ResponseEntity
					.status(HttpStatus.OK)
					.body(new WrapperResponse(shoppingService.buyAllShoppingItemByUsernameAndBought(username, bought, products), new Outcome(HttpStatus.OK, null, null, null, null)));
	}
}
