package io.jeidiiy.outflearn.user.service.port;

import io.jeidiiy.outflearn.user.domain.User;

public interface UserRepository {
	User getById(Long id);

	User save(User user);
}
