package io.jeidiiy.outflearn.user.controller;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import io.jeidiiy.outflearn.mock.TestContainer;
import io.jeidiiy.outflearn.user.controller.response.UserResponse;
import io.jeidiiy.outflearn.user.domain.UserCreate;
import io.jeidiiy.outflearn.user.domain.UserStatus;

class UserCreateControllerTest {

	private TestContainer testContainer;

	@BeforeEach
	void init() {
		testContainer = TestContainer.builder().build();
	}

	@Test
	void 사용자는_회원가입할_수_있고_인증_전에는_PENDING_상태이다() {
		// given
		TestContainer testContainer = TestContainer.builder()
			.uuidHolder(() -> "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
			.build();
		UserCreate userCreate = UserCreate.builder()
			.email("test@gmail.com")
			.password("1234")
			.build();

		// when
		ResponseEntity<UserResponse> result = testContainer.userCreateController.create(userCreate);

		// then
		assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
		assertThat(result.getBody()).isNotNull();
		assertThat(result.getBody().getEmail()).isEqualTo("test@gmail.com");
		assertThat(result.getBody().getNickname()).isEqualTo("test");
		assertThat(result.getBody().getLastLoginAt()).isNull();
		assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.PENDING);
		assertThat(testContainer.userRepository.getById(1L).getVerificationCode()).isEqualTo(
			"aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");
	}
}
