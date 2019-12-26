package it.marco.gateway.fallback;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;

public class FallbackManager implements FallbackProvider {
	
	private String responseBody;
	private HttpHeaders httpHeaders = new HttpHeaders();
	private String route;
	private HttpStatus statusCode;
	private String statusText;
	
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

	public void setRoute(String route) {
		this.route = route;
	}
 
    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }
	
	@Override
	public String getRoute() {
		return route;
	}
	
	@Override
	public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
		return new ClientHttpResponse() {
			
			@Override
            public InputStream getBody() throws IOException {
                return new ByteArrayInputStream(responseBody.getBytes(StandardCharsets.UTF_8));
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