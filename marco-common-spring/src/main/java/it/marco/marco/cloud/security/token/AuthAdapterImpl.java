package it.marco.marco.cloud.security.token;

import java.io.Serializable;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import it.marco.marco.cloud.config.properties.AuthProperties;

@Component
public class AuthAdapterImpl implements AuthAdapter, Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private AuthProperties authProps;

	@Override
	public String getSigningKey() {
		return authProps.getSigninKey();
	}
}