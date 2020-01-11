package it.marco.marco.cloud.config.properties;

public interface AuthProperties {

	public String getClientId();

	public void setClientId(String clientId);

	public String getClientSecret();

	public void setClientSecret(String clientSecret);

	public int getAccessTokenValiditySeconds();

	public void setAccessTokenValiditySeconds(int accessTokenValiditySeconds);

	public int getRefreshTokenValiditySeconds();

	public void setRefreshTokenValiditySeconds(int refreshTokenValiditySeconds);

	public String getSigninKey();

	public void setSigninKey(String signinKey);
}
