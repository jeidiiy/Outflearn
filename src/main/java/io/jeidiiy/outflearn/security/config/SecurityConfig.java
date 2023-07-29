package io.jeidiiy.outflearn.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import io.jeidiiy.outflearn.security.login.ajax.configure.AjaxLoginConfigurer;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final UserDetailsService userDetailsService;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));
		http.csrf(csrf -> csrf.disable());
		http.formLogin(formLogin -> formLogin.disable());
		http.httpBasic(httpBasic -> httpBasic.disable());
		http.authorizeHttpRequests(requestMatcher -> requestMatcher.anyRequest().permitAll());
		http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

		http.apply(ajaxLoginConfigurer());

		return http.build();
	}

	public AjaxLoginConfigurer<HttpSecurity> ajaxLoginConfigurer() {
		AjaxLoginConfigurer<HttpSecurity> ajaxLoginConfigurer = AjaxLoginConfigurer.create();
		ajaxLoginConfigurer.setPasswordEncoder(passwordEncoder());
		ajaxLoginConfigurer.setUserDetailsService(userDetailsService);
		return ajaxLoginConfigurer;
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
}
