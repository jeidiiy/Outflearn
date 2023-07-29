package io.jeidiiy.outflearn.security.login.ajax;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jeidiiy.outflearn.api.Endpoint;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AjaxEmailPasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	public static final String DEFAULT_URL = Endpoint.Api.LOGIN_WITH_EMAIL;
	private static final String AJAX_EMAIL_KEY = "email";
	private static final String AJAX_PASSWORD_KEY = "password";
	private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER =
		new AntPathRequestMatcher(DEFAULT_URL, "POST");

	private final ObjectMapper objectMapper = new ObjectMapper();

	private final boolean postOnly = true;

	private AjaxEmailPasswordAuthenticationFilter() {
		super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
	}

	private AjaxEmailPasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
		super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
	}

	public static AjaxEmailPasswordAuthenticationFilter create() {
		return new AjaxEmailPasswordAuthenticationFilter();
	}

	public static AjaxEmailPasswordAuthenticationFilter create(AuthenticationManager authenticationManager) {
		return new AjaxEmailPasswordAuthenticationFilter(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException, IOException {
		log.info("AjaxEmailPasswordAuthenticationFilter.attemptAuthentication() 시작");
		if (this.postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}

		if (!request.getHeader(HttpHeaders.CONTENT_TYPE).startsWith(MediaType.APPLICATION_JSON_VALUE)) {
			throw new AuthenticationServiceException(
				"Authentication content type not supported: " + request.getHeader(HttpHeaders.CONTENT_TYPE));
		}

		EmailLoginRequest requestDto = getAjaxLoginUserDetails(request);

		String email = requestDto.getEmail();
		email = (email != null) ? email.trim() : "";
		String password = requestDto.getPassword();
		password = (password != null) ? password : "";
		AjaxEmailPasswordAuthenticationToken authRequest = AjaxEmailPasswordAuthenticationToken.unauthenticated(email,
			password);

		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);
		return this.getAuthenticationManager().authenticate(authRequest);
	}

	protected void setDetails(HttpServletRequest request, AjaxEmailPasswordAuthenticationToken authRequest) {
		authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
	}

	private EmailLoginRequest getAjaxLoginUserDetails(HttpServletRequest request) throws IOException {
		return objectMapper.readValue(request.getReader(), EmailLoginRequest.class);
	}
}
