package io.jeidiiy.outflearn.mock;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class FakeAuthenticationManager implements AuthenticationManager {
	private final AuthenticationProvider authenticationProvider;

	public FakeAuthenticationManager(AuthenticationProvider authenticationProvider) {
		this.authenticationProvider = authenticationProvider;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		return authenticationProvider.authenticate(authentication);
	}
}
