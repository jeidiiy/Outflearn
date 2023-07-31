package io.jeidiiy.outflearn.security.config;

import static io.jeidiiy.outflearn.api.Endpoint.Api.*;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.*;

import java.nio.charset.StandardCharsets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jeidiiy.outflearn.security.login.ajax.configure.AjaxLoginConfigurer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

	private final UserDetailsService userDetailsService;
	private final ObjectMapper objectMapper;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
			.csrf(csrf -> csrf.disable())
			.formLogin(formLogin -> formLogin.disable())
			.httpBasic(httpBasic -> httpBasic.disable())
			.cors(cors -> cors.configurationSource(corsConfigurationSource()));

		http
			.authorizeHttpRequests(
				auth ->
					auth
						.requestMatchers(antMatcher("/h2-console/**")).permitAll() // TODO: 개발용이므로 나중에 배포 시 삭제
						.requestMatchers(antMatcher(LOGIN_WITH_EMAIL)).permitAll()
						.requestMatchers(antMatcher(HttpMethod.GET, USER + "/**")).permitAll()
						.requestMatchers(antMatcher(HttpMethod.POST, USER)).permitAll() // 회원가입
						.anyRequest().authenticated()
			);

		http
			.apply(ajaxLoginConfigurer());

		http
			.logout(logout -> logout
				.logoutUrl(LOGOUT)
				.clearAuthentication(true)
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.logoutSuccessHandler(
					(request, response, authentication) -> response.setStatus(HttpStatus.NO_CONTENT.value())
				));

		http
			.exceptionHandling(exceptionHandling ->
				exceptionHandling
					.authenticationEntryPoint(authenticationEntryPoint())
					.accessDeniedHandler(accessDeniedHandler())
			);

		http
			.securityContext(
				securityContext ->
					securityContext.requireExplicitSave(false)
			);

		return http.build();
	}

	public AjaxLoginConfigurer<HttpSecurity> ajaxLoginConfigurer() {
		AjaxLoginConfigurer<HttpSecurity> ajaxLoginConfigurer = AjaxLoginConfigurer.create();
		ajaxLoginConfigurer.setPasswordEncoder(passwordEncoder());
		ajaxLoginConfigurer.setUserDetailsService(userDetailsService);
		return ajaxLoginConfigurer;
	}

	public AuthenticationEntryPoint authenticationEntryPoint() {
		return (request, response, authException) -> {
			log.trace("인증실패");
			AuthenticationFailureDto authenticationFailureDto = AuthenticationFailureDto.create();
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setCharacterEncoding(String.valueOf(StandardCharsets.UTF_8));
			response.setContentType(String.valueOf(MediaType.APPLICATION_JSON));
			objectMapper.writeValue(response.getWriter(), authenticationFailureDto);
		};
	}

	public AccessDeniedHandler accessDeniedHandler() {
		return (request, response, accessDeniedException) -> {
			log.trace("인가실패");
			AuthorizationFailureDto authorizationFailureDto = AuthorizationFailureDto.create();
			response.setStatus(HttpStatus.NOT_FOUND.value());
			response.setCharacterEncoding(String.valueOf(StandardCharsets.UTF_8));
			response.setContentType(String.valueOf(MediaType.APPLICATION_JSON));
			objectMapper.writeValue(response.getWriter(), authorizationFailureDto);
		};
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.addAllowedMethod("*");        // 모든 메서드 허용
		corsConfiguration.addAllowedOriginPattern("*"); // TODO: 이후 프론트엔드 IP만 허용하도록 변경 필요
		corsConfiguration.setAllowCredentials(true);    // 클라이언트에서 쿠키 요청 허용

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration);
		return source;
	}

	@Getter
	static class AuthenticationFailureDto {
		private final String message = "로그인 후 이용해 주세요.";

		public static AuthenticationFailureDto create() {
			return new AuthenticationFailureDto();
		}
	}

	@Getter
	static class AuthorizationFailureDto {
		private final String message = "권한이 없습니다.";

		public static AuthorizationFailureDto create() {
			return new AuthorizationFailureDto();
		}
	}
}
