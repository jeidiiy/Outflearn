package io.jeidiiy.outflearn.user.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.jeidiiy.outflearn.mock.FakeMailSender;

class VerificationServiceTest {
	@Test
	void 이메일과_내용이_제대로_만들어져서_보내는지_테스트한다() {
		// given
		FakeMailSender fakeMailSender = new FakeMailSender();
		VerificationService verificationService = new VerificationService(fakeMailSender);

		// when
		verificationService.send("test@gmail.com", 1L, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

		// then
		assertThat(fakeMailSender.email).isEqualTo("test@gmail.com");
		assertThat(fakeMailSender.title).isEqualTo("Please verify your email address");
		assertThat(fakeMailSender.content).isEqualTo(
			"Please click the following link to verify your email address: http://localhost:8080/api/users/1/verify?verificationCode=aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"
		);
	}
}
