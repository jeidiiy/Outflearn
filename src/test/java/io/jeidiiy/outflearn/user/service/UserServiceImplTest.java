package io.jeidiiy.outflearn.user.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.jeidiiy.outflearn.common.exception.EmailDuplicateException;
import io.jeidiiy.outflearn.common.exception.VerificationCodeNotMatchedException;
import io.jeidiiy.outflearn.mock.FakeMailSender;
import io.jeidiiy.outflearn.mock.FakeUserRepository;
import io.jeidiiy.outflearn.mock.TestPasswordEncoder;
import io.jeidiiy.outflearn.mock.TestUuidHolder;
import io.jeidiiy.outflearn.user.domain.User;
import io.jeidiiy.outflearn.user.domain.UserCreate;
import io.jeidiiy.outflearn.user.domain.UserStatus;

class UserServiceImplTest {
	private UserServiceImpl userService;

	@BeforeEach
	void init() {
		FakeMailSender fakeMailSender = new FakeMailSender();
		FakeUserRepository fakeUserRepository = new FakeUserRepository();
		this.userService = UserServiceImpl.builder()
			.verificationService(new VerificationService(fakeMailSender))
			.passwordEncoder(new TestPasswordEncoder())
			.userRepository(fakeUserRepository)
			.uuidHolder(new TestUuidHolder("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab"))
			.build();
		fakeUserRepository.save(User.builder()
			.id(1L)
			.email("tooth@gmail.com")
			.nickname("test")
			.verificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
			.status(UserStatus.ACTIVE)
			.lastLoginAt(0L)
			.build());
		fakeUserRepository.save(User.builder()
			.id(2L)
			.email("toast@gmail.com")
			.nickname("toast")
			.verificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
			.status(UserStatus.PENDING)
			.lastLoginAt(0L)
			.build());
	}

	@Test
	void UserCreate를_이용하여_User를_생성할_수_있다() throws Exception {
		// given
		UserCreate userCreate = UserCreate.builder()
			.email("test@gmail.com")
			.password("1234")
			.build();

		// when
		User result = userService.create(userCreate);

		// then
		assertThat(result.getId()).isNotNull();
		assertThat(result.getStatus()).isEqualTo(UserStatus.PENDING);
		assertThat(result.getVerificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");
	}

	@Test
	void verifyEmail은_잘못된_인증코드를_받으면_예외를_던진다() {
		// given
		// when
		// then
		assertThatThrownBy(() -> {
			userService.verifyEmail(2L, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaac");
		}).isInstanceOf(VerificationCodeNotMatchedException.class);
	}

	@Test
	void 같은_이메일로_회원가입하면_예외를_던진다() {
		// given
		UserCreate userCreate = UserCreate.builder()
			.email("toast@gmail.com")
			.build();

		// when
		// then
		assertThatThrownBy(() -> userService.create(userCreate))
			.isInstanceOf(EmailDuplicateException.class);
	}
}
