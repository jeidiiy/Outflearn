package io.jeidiiy.outflearn.mock;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import io.jeidiiy.outflearn.security.login.ajax.domain.AjaxEmailPasswordAuthenticationToken;

public class FakeAuthenticationProvider implements AuthenticationProvider {

	private final GrantedAuthoritiesMapper authoritiesMapper = new SimpleAuthorityMapper();
	private UserDetailsService userDetailsService;
	private PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String email = (String)authentication.getPrincipal();
		String password = (String)authentication.getCredentials();
		UserDetails user;

		try {
			user = retrieveUser(email);
		} catch (UsernameNotFoundException ex) {
			throw new BadCredentialsException("AjaxEmailPasswordAuthenticationProvider.badCredentials");
		}
		Assert.notNull(user, "retrieveUser returned null - a violation of the interface contract");
		boolean matches = passwordEncoder.matches(password, user.getPassword());
		if (!matches) {
			throw new BadCredentialsException("AjaxEmailPasswordAuthenticationProvider.badCredentials");
		}

		return createSuccessAuthentication(user, authentication, user);
	}

	private Authentication createSuccessAuthentication(Object principal, Authentication authentication,
		UserDetails user) {
		AjaxEmailPasswordAuthenticationToken result = AjaxEmailPasswordAuthenticationToken.authenticated(principal,
			authentication.getCredentials(), this.authoritiesMapper.mapAuthorities(user.getAuthorities()));
		result.setDetails(authentication.getDetails());
		return result;
	}

	protected final UserDetails retrieveUser(String email)
		throws AuthenticationException {
		try {
			UserDetails loadedUser = this.userDetailsService.loadUserByUsername(email);
			if (loadedUser == null) {
				throw new InternalAuthenticationServiceException(
					"UserDetailsService returned null, which is an interface contract violation");
			}
			return loadedUser;
		} catch (Exception ex) {
			throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
}
