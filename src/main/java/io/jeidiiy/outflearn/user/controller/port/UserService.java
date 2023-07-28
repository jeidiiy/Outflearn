package io.jeidiiy.outflearn.user.controller.port;

import io.jeidiiy.outflearn.user.domain.User;
import io.jeidiiy.outflearn.user.domain.UserCreate;

public interface UserService {

	User create(UserCreate userCreate);

	User getById(Long id);

	void verifyEmail(Long id, String verificationCode);
}
