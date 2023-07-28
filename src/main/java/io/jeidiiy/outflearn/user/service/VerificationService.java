package io.jeidiiy.outflearn.user.service;

import org.springframework.stereotype.Service;

import io.jeidiiy.outflearn.user.service.port.MailSender;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VerificationService {

	private final MailSender mailSender;

	public void send(String email, long userId, String verificationCode) {
		String verificationUrl = generateCertificationUrl(userId, verificationCode);
		String title = "Please verify your email address";
		String content = "Please click the following link to verify your email address: " + verificationUrl;
		mailSender.send(email, title, content);
	}

	private String generateCertificationUrl(long userId, String verificationCode) {
		return "http://localhost:8080/api/users/" + userId + "/verify?verificationCode=" + verificationCode;
	}
}
