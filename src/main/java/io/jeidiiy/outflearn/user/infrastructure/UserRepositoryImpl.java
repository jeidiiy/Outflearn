package io.jeidiiy.outflearn.user.infrastructure;

import org.springframework.stereotype.Repository;

import io.jeidiiy.outflearn.common.exception.ResourceNotFoundException;
import io.jeidiiy.outflearn.user.domain.User;
import io.jeidiiy.outflearn.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

	private final UserJpaRepository userJpaRepository;

	@Override
	public User getById(Long id) {
		return userJpaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Users", id));
	}

	@Override
	public User save(User user) {
		return userJpaRepository.save(user);
	}
}
