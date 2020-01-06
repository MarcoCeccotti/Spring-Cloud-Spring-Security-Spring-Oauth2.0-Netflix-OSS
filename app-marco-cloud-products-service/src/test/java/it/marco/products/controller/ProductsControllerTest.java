package it.marco.products.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.marco.products.bean.product.Product;
import it.marco.products.service.product.ProductService;

@ExtendWith(MockitoExtension.class)
public class ProductsControllerTest {
	
	@Mock
	private ProductService productService;
	
	@InjectMocks
	private ProductsController productsController;
	
	public MockMvc mockMvc;
	
	private Product validProduct;
	
	@BeforeEach
	private void setUp() {
		validProduct = new Product();
		validProduct.setId(5);
		validProduct.setName("Trippa");
		validProduct.setQuantity(1);
		
		this.mockMvc = MockMvcBuilders.standaloneSetup(productsController)
									  .setMessageConverters(jackson2HttpMessageConverter())
									  .build();
	}
	
	@AfterEach
	private void tearDown() {
		reset(productService);
	}
	
	@Test
	public void testGetProductById() throws Exception {
		
		given(productService.getProduct(any(Integer.class))).willReturn(validProduct);
		
		this.mockMvc.perform(get("/products/5"))
					.andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andDo(print())
					.andExpect(jsonPath("$.payload.id", is(validProduct.getId())));
	}

	@DisplayName("List Ops - ")
    @Nested
    public class TestListProducts {
		
		private List<Product> products;
		
		@BeforeEach
        void setUp() {
			
			products = new ArrayList<Product>();
			products.add(validProduct);
			
			Product prod = new Product();
			prod.setId(2);
			prod.setName("Manzo");
			prod.setQuantity(31);
			products.add(prod);
        }
		
		@DisplayName("Test list products - no parameters")
        @Test
        void testListBeers() throws Exception {
			
			given(productService.getProducts()).willReturn(products);
			
			mockMvc.perform(get("/products/all").accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andDo(print())
					.andExpect(jsonPath("$.payload", hasSize(2)))
					.andExpect(jsonPath("$.payload[0].id", is(validProduct.getId())))
					.andExpect(jsonPath("$.payload[1].name", is("Manzo")));
        }
	}
	
	public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter() {
		ObjectMapper objectMapper = new ObjectMapper();
		
		return new MappingJackson2HttpMessageConverter(objectMapper);
	}
}
