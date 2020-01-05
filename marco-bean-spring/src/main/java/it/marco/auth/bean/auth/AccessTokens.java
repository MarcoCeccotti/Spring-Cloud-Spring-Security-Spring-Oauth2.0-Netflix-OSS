package it.marco.auth.bean.auth;

public class AccessTokens {

	private Object access_token;
	
	private String token_type;
	
	private Object refresh_token;
	
	private Integer expires_in;
	
	private String scope;
	
	private String jti;

	public Object getAccess_token() {
		return access_token;
	}

	public void setAccess_token(Object access_token) {
		this.access_token = access_token;
	}

	public String getToken_type() {
		return token_type;
	}

	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}

	public Object getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(Object refresh_token) {
		this.refresh_token = refresh_token;
	}

	public Integer getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(Integer expires_in) {
		this.expires_in = expires_in;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getJti() {
		return jti;
	}

	public void setJti(String jti) {
		this.jti = jti;
	}
}