package it.marco.products.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.google.gson.Gson;

import it.marco.marco.bean.http.WrapperResponse;
import it.marco.marco.utils.utilities.AuthUtils;
import it.marco.products.bean.product.Product;
import it.marco.products.interfaces.ControllerTests;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductsControllerIT implements ControllerTests {

	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Test
	public void testProductsList() {
		
		HttpHeaders headers = new HttpHeaders();
		headers.set(AuthUtils.AUTHORIZATION, AuthUtils.BEARER_PREFIX + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjYXJsb3NvbGRhbmkiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiLCJ0cnVzdCJdLCJleHAiOjE1OTExODE2MzUsImlhdCI6MTU1OTY0NTYzNTYyNCwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE0iXSwiY2xpZW50X2lkIjoic2F2ZW1hLWNsaWVudCIsImp0aSI6IjI5ZTcwMWU5LTM5M2YtNDRiZi04NDQ0LWFhZGIwZWVlYzJkOCJ9.ZPExiOtgfyGlwCl0tw7nf5Vo4OHq2ZFdku_p1NHNSZg");
		
		HttpEntity<?> entity = new HttpEntity<>(headers);
		
		ResponseEntity<WrapperResponse> wrapResp = testRestTemplate.exchange("/products/5", HttpMethod.GET, entity, WrapperResponse.class);
		
		Product prod = new Gson().fromJson(new Gson().toJson(wrapResp.getBody().getPayload()), Product.class);
		assertEquals("Trippa", prod.getName());
	}
}
