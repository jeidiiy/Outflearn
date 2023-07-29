package io.jeidiiy.outflearn.user.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.jeidiiy.outflearn.common.exception.VerificationCodeNotMatchedException;
import io.jeidiiy.outflearn.mock.TestPasswordEncoder;
import io.jeidiiy.outflearn.mock.TestUuidHolder;

public class UserTest {

	@Test
	void UserCreate_객체로_생성할_수_있다() {
		// given
		UserCreate userCreate = UserCreate.builder()
			.email("test@gmail.com")
			.password("1234")
			.build();

		// when
		User user = User.from(userCreate, new TestUuidHolder("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"),
			new TestPasswordEncoder());

		// then
		assertThat(user.getId()).isNull();
		assertThat(user.getEmail()).isEqualTo("test@gmail.com");
		assertThat(user.getNickname()).isEqualTo("test");
		assertThat(user.getPassword()).isEqualTo("1234");
		assertThat(user.getStatus()).isEqualTo(UserStatus.PENDING);
		assertThat(user.getVerificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
	}

	@Test
	public void 유효한_인증_코드로_계정을_활성화_할_수_있다() {
		// given
		User user = User.builder()
			.id(1L)
			.email("test@gmail.com")
			.nickname("test")
			.status(UserStatus.PENDING)
			.lastLoginAt(100L)
			.verificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
			.build();

		// when
		user.verify("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

		// then
		assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
	}

	@Test
	public void 잘못된_인증_코드로_계정을_활성화_하려하면_에러를_던진다() {
		// given
		User user = User.builder()
			.id(1L)
			.email("test@kakao.com")
			.nickname("test")
			.status(UserStatus.PENDING)
			.lastLoginAt(100L)
			.verificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
			.build();

		// when
		// then
		assertThatThrownBy(() -> user.verify("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab"))
			.isInstanceOf(VerificationCodeNotMatchedException.class);
	}
}
