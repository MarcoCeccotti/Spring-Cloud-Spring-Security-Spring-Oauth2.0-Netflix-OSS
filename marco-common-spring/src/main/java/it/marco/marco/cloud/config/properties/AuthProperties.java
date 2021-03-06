package it.marco.marco.cloud.config.properties;

public class AuthProperties {
	
	private String clientId;
	
	private String clientSecret;
	
	private int accessTokenValiditySeconds;

	private int refreshTokenValiditySeconds;
	
	private String signinKey;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public int getAccessTokenValiditySeconds() {
		return accessTokenValiditySeconds;
	}

	public void setAccessTokenValiditySeconds(int accessTokenValiditySeconds) {
		this.accessTokenValiditySeconds = accessTokenValiditySeconds;
	}

	public int getRefreshTokenValiditySeconds() {
		return refreshTokenValiditySeconds;
	}

	public void setRefreshTokenValiditySeconds(int refreshTokenValiditySeconds) {
		this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
	}

	public String getSigninKey() {
		return signinKey;
	}

	public void setSigninKey(String signinKey) {
		this.signinKey = signinKey;
	}
}
