package io.jeidiiy.outflearn.user.domain;

import io.jeidiiy.outflearn.web.validation.password.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserCreate {

	@Email
	@NotNull
	private final String email;

	@Password
	private final String password;

	@Builder
	public UserCreate(String email, String password) {
		this.email = email;
		this.password = password;
	}
}
