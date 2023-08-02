package io.jeidiiy.outflearn.security.login.ajax.handler;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jeidiiy.outflearn.common.error.CommonErrorCode;
import io.jeidiiy.outflearn.common.error.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AjaxAuthenticationFailureHandler implements AuthenticationFailureHandler {

	private final ObjectMapper objectMapper = new ObjectMapper();

	public static AjaxAuthenticationFailureHandler create() {
		return new AjaxAuthenticationFailureHandler();
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException exception) throws IOException {
		log.warn("onAuthenticationFailure", exception);

		CommonErrorCode errorCode = CommonErrorCode.INVALID_PARAMETER;

		ErrorResponse errorResponse = ErrorResponse.builder()
			.code(errorCode.name())
			.message("Email or password is wrong")
			.build();

		response.setStatus(errorCode.getHttpStatus().value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		objectMapper.writeValue(response.getWriter(), errorResponse);
	}
}
