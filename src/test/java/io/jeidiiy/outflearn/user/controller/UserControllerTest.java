package io.jeidiiy.outflearn.user.controller;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import io.jeidiiy.outflearn.common.exception.VerificationCodeNotMatchedException;
import io.jeidiiy.outflearn.mock.TestContainer;
import io.jeidiiy.outflearn.user.controller.response.UserResponse;
import io.jeidiiy.outflearn.user.domain.User;
import io.jeidiiy.outflearn.user.domain.UserStatus;

class UserControllerTest {

	private TestContainer testContainer;

	@BeforeEach
	void init() {
		testContainer = TestContainer.builder().build();
	}

	@Test
	void 사용자는_특정_유저의_정보를_개인정보가_제거된_상태로_받을_수_있다() {
		// given
		testContainer.userRepository.save(User.builder()
			.id(1L)
			.email("test@gmail.com")
			.nickname("test")
			.status(UserStatus.ACTIVE)
			.verificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
			.lastLoginAt(100L)
			.build());

		// when
		ResponseEntity<UserResponse> result = testContainer.userController.getById(1L);

		// then
		assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
		assertThat(result.getBody()).isNotNull();
		assertThat(result.getBody().getEmail()).isEqualTo("test@gmail.com");
		assertThat(result.getBody().getNickname()).isEqualTo("test");
		assertThat(result.getBody().getLastLoginAt()).isEqualTo(100L);
		assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.ACTIVE);
	}

	@Test
	void 사용자는_인증_코드가_일치하지_않는_경우_잘못된_값_응답을_내려준다() {
		// given
		testContainer.userRepository.save(User.builder()
			.id(1L)
			.email("test@gmail.com")
			.nickname("test")
			.status(UserStatus.PENDING)
			.verificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
			.lastLoginAt(100L)
			.build());

		// when
		// then
		assertThatThrownBy(() -> {
			testContainer.userController.verifyEmail(1L, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaac");
		}).isInstanceOf(VerificationCodeNotMatchedException.class);
	}
}
