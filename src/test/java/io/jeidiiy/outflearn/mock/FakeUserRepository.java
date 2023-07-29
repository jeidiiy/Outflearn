package io.jeidiiy.outflearn.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.jeidiiy.outflearn.common.exception.ResourceNotFoundException;
import io.jeidiiy.outflearn.user.domain.User;
import io.jeidiiy.outflearn.user.service.port.UserRepository;

public class FakeUserRepository implements UserRepository {
	private final List<User> store = new ArrayList<>();
	private Long autoGeneratedId = 1L;

	private static boolean isNewUser(User user) {
		return user.getId() == null || user.getId() == 0L;
	}

	@Override
	public User getById(Long id) {
		return store.stream()
			.filter(user -> Objects.equals(user.getId(), id))
			.findFirst()
			.orElseThrow(() -> new ResourceNotFoundException("Users", id));
	}

	@Override
	public User save(User user) {
		if (isNewUser(user)) {
			User newUser = User.builder()
				.id(autoGeneratedId++)
				.email(user.getEmail())
				.nickname(user.getNickname())
				.verificationCode(user.getVerificationCode())
				.status(user.getStatus())
				.lastLoginAt(user.getLastLoginAt())
				.build();
			store.add(newUser);
			return newUser;
		}

		store.removeIf(elem -> Objects.equals(elem.getId(), user.getId()));
		store.add(user);
		return user;
	}
}