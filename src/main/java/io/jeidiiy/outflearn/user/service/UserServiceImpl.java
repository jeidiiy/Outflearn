package io.jeidiiy.outflearn.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jeidiiy.outflearn.common.service.port.UuidHolder;
import io.jeidiiy.outflearn.user.controller.port.UserService;
import io.jeidiiy.outflearn.user.domain.User;
import io.jeidiiy.outflearn.user.domain.UserCreate;
import io.jeidiiy.outflearn.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final VerificationService verificationService;
	private final UserRepository userRepository;
	private final UuidHolder uuidHolder;
	private final PasswordEncoder passwordEncoder;

	public User create(UserCreate userCreate) {
		User user = User.from(userCreate, uuidHolder, passwordEncoder);
		user = userRepository.save(user);
		verificationService.send(userCreate.getEmail(), user.getId(), user.getVerificationCode());
		return user;
	}

	@Transactional(readOnly = true)
	public User getById(Long id) {
		return userRepository.getById(id);
	}

	public void verifyEmail(Long id, String verificationCode) {
		User user = userRepository.getById(id);
		user.verify(verificationCode);
	}
}
