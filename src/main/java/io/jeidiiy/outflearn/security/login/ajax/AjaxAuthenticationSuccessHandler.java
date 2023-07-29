package io.jeidiiy.outflearn.security.login.ajax;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AjaxAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final ObjectMapper objectMapper = new ObjectMapper();
	private final RequestCache requestCache = new HttpSessionRequestCache();

	public static AjaxAuthenticationSuccessHandler create() {
		return new AjaxAuthenticationSuccessHandler();
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException {
		log.info("AjaxAuthenticationSuccessHandler.onAuthenticationSuccess() 시작");
		SavedRequest savedRequest = this.requestCache.getRequest(request, response);
		String redirectUrl = savedRequest == null ? getDefaultTargetUrl() : savedRequest.getRedirectUrl();
		AjaxLoginSuccessDto loginSuccessDto = getAjaxLoginSuccessDto(
			authentication, redirectUrl);
		response.setStatus(HttpStatus.OK.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		objectMapper.writeValue(response.getWriter(), loginSuccessDto);
		clearAuthenticationAttributes(request);
	}

	private AjaxLoginSuccessDto getAjaxLoginSuccessDto(Authentication authentication, String redirectUrl) {
		LoginUser principal = (LoginUser)authentication.getPrincipal();
		String id = Long.toString(principal.getId());
		return AjaxLoginSuccessDto.of(id, redirectUrl);
	}
}
