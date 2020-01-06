package it.marco.products.controller;

import javax.ws.rs.core.MediaType;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import it.marco.products.service.product.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductsController {
	
	private ProductService productService;
	
	public ProductsController(ProductService productService) {
		this.productService = productService;
	}
	
	@ApiOperation(value = "Retrieves all the products")
	@ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header")})
	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<WrapperResponse> getProducts() throws Exception {
		
		return ResponseEntity
					.status(HttpStatus.OK)
					.body(new WrapperResponse(productService.getProducts(), new Outcome(HttpStatus.OK, null, null, null, null)));
	}
	
	@ApiOperation(value = "Retrieves product by id passed as path variable")
	@ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header")})
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<WrapperResponse> getProduct(@PathVariable int id) throws Exception {
		
		return ResponseEntity
					.status(HttpStatus.OK)
					.body(new WrapperResponse(productService.getProduct(id), new Outcome(HttpStatus.OK, null, null, null, null)));
	}
	
	@ApiOperation(value = "Saves a new product on db")
	@ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header")})
	@PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<WrapperResponse> saveProduct(@RequestBody Product product) throws Exception {
		
		return ResponseEntity
					.status(HttpStatus.OK)
					.body(new WrapperResponse(productService.saveProduct(product), new Outcome(HttpStatus.OK, null, null, null, null)));
	}
	
	@ApiOperation(value = "Deletes a product from db")
	@ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header")})
	@DeleteMapping(value = "/delete/{id}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<WrapperResponse> deleteProduct(@PathVariable int id) throws Exception {
		
		return ResponseEntity
					.status(HttpStatus.OK)
					.body(new WrapperResponse(productService.deleteProduct(id), new Outcome(HttpStatus.OK, null, null, null, null)));
	}
}
