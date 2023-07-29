package io.jeidiiy.outflearn.security.login.ajax.filter;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.http.MediaType.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jeidiiy.outflearn.mock.FakeAuthenticationManager;
import io.jeidiiy.outflearn.mock.FakeAuthenticationProvider;
import io.jeidiiy.outflearn.mock.TestContainer;
import io.jeidiiy.outflearn.mock.TestPasswordEncoder;
import io.jeidiiy.outflearn.security.login.ajax.domain.EmailLoginRequest;
import io.jeidiiy.outflearn.security.login.ajax.service.EmailUserDetailsService;
import io.jeidiiy.outflearn.user.domain.User;
import io.jeidiiy.outflearn.user.domain.UserStatus;

class AjaxEmailPasswordAuthenticationFilterTest {

	private ObjectMapper objectMapper;

	private FakeAuthenticationProvider fakeAuthenticationProvider;

	@BeforeEach
	void init() {
		objectMapper = new ObjectMapper();
		TestContainer testContainer = TestContainer.builder().build();
		testContainer.userRepository.save(User.builder()
			.id(1L)
			.email("test@gmail.com")
			.nickname("test")
			.password("password1234")
			.status(UserStatus.ACTIVE)
			.verificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
			.lastLoginAt(100L)
			.build());

		fakeAuthenticationProvider = new FakeAuthenticationProvider();
		fakeAuthenticationProvider.setPasswordEncoder(new TestPasswordEncoder());
		fakeAuthenticationProvider.setUserDetailsService(new EmailUserDetailsService(testContainer.userRepository));
	}

	@Test
	void 로그인_성공() throws Exception {
		// given
		EmailLoginRequest requestDto = EmailLoginRequest.builder()
			.email("test@gmail.com")
			.password("password1234")
			.build();

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setContent(objectMapper.writeValueAsBytes(requestDto));
		request.setMethod("POST");
		request.setContentType(APPLICATION_JSON_VALUE);

		MockHttpServletResponse response = new MockHttpServletResponse();

		AjaxEmailPasswordAuthenticationFilter filter = AjaxEmailPasswordAuthenticationFilter.create(
			new FakeAuthenticationManager(fakeAuthenticationProvider));

		// when
		Authentication authentication = filter.attemptAuthentication(request, response);

		// then
		assertThat(authentication).isNotNull();
	}

	@Test
	void 틀린_비밀번호로_인한_로그인_실패() throws Exception {
		// given
		EmailLoginRequest requestDto = EmailLoginRequest.builder()
			.email("test@gmail.com")
			.password("password12345")
			.build();

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setContent(objectMapper.writeValueAsBytes(requestDto));
		request.setMethod("POST");
		request.setContentType(APPLICATION_JSON_VALUE);

		MockHttpServletResponse response = new MockHttpServletResponse();

		AjaxEmailPasswordAuthenticationFilter filter =
			AjaxEmailPasswordAuthenticationFilter.create(new FakeAuthenticationManager(fakeAuthenticationProvider));

		// when
		// then
		assertThatThrownBy(() -> filter.attemptAuthentication(request, response))
			.isInstanceOf(BadCredentialsException.class);
	}

	@Test
	void 없는_이메일로_인한_로그인_실패() throws Exception {
		// given
		EmailLoginRequest requestDto = EmailLoginRequest.builder()
			.email("toast@gmail.com")
			.password("password1234")
			.build();

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setContent(objectMapper.writeValueAsBytes(requestDto));
		request.setMethod("POST");
		request.setContentType(APPLICATION_JSON_VALUE);

		MockHttpServletResponse response = new MockHttpServletResponse();

		AjaxEmailPasswordAuthenticationFilter filter =
			AjaxEmailPasswordAuthenticationFilter.create(new FakeAuthenticationManager(fakeAuthenticationProvider));

		// when
		// then
		assertThatThrownBy(() -> filter.attemptAuthentication(request, response))
			.isInstanceOf(InternalAuthenticationServiceException.class);
	}
}
