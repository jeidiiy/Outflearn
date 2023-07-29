package io.jeidiiy.outflearn.security.login.ajax.handler;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jeidiiy.outflearn.security.login.ajax.domain.AjaxLoginFailureDto;
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
		log.info("AjaxAuthenticationFailureHandler.onAuthenticationFailure() 시작");
		String errorMessage = "이메일 또는 비밀번호를 잘못 입력하셨습니다.";
		AjaxLoginFailureDto loginFailureDto = AjaxLoginFailureDto.from(errorMessage);

		response.setStatus(HttpStatus.BAD_REQUEST.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		objectMapper.writeValue(response.getWriter(), loginFailureDto);
	}
}
