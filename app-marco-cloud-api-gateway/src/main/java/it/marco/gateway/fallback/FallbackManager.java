package it.marco.gateway.fallback;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.marco.marco.bean.http.WrapperResponse;

public class FallbackManager implements FallbackProvider {

	private Gson gson;
	
	private HttpHeaders httpHeaders = new HttpHeaders();
	private HttpStatus statusCode;
	private String statusText;
	private WrapperResponse response;
	
	@PostConstruct
	private void init() {
		GsonBuilder gsonBuilder = new GsonBuilder();  
		gsonBuilder.serializeNulls();
		this.gson = gsonBuilder.create();
	}
	
	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	public HttpHeaders getHeaders() {
		return httpHeaders;
	}

	public void setHeaders(HttpHeaders headers) {
		this.httpHeaders = headers;
		this.httpHeaders.setContentType(MediaType.APPLICATION_JSON);
	}

	public HttpStatus getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(HttpStatus statusCode) {
		this.statusCode = statusCode;
	}
	
	@Override
	public String getRoute() {
		return "*";
	}
	
	public WrapperResponse getResponse() {
		return response;
	}

	public void setResponse(WrapperResponse response) {
		this.response = response;
	}

	@Override
	public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
		return new ClientHttpResponse() {
			
			@Override
            public InputStream getBody() throws IOException {
				response.getOutcome().setMessage(StringUtils.capitalize(route) + " è al momento offline. Prego ritentare in un secondo momento.");
                return new ByteArrayInputStream(gson.toJson(response).getBytes(StandardCharsets.UTF_8));
			}

			@Override
			public HttpHeaders getHeaders() {
				return httpHeaders;
			}

			@Override
			public void close() {}

			@Override
			public int getRawStatusCode() throws IOException {
				return statusCode.value();
			}

			@Override
			public HttpStatus getStatusCode() throws IOException {
				return statusCode;
			}

			@Override
			public String getStatusText() throws IOException {
				return statusText;
			}
		};
	}
}