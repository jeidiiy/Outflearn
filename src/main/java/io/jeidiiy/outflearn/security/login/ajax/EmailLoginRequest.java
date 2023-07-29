package io.jeidiiy.outflearn.security.login.ajax;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class EmailLoginRequest {

	private String email;
	private String password;

	@Builder
	public EmailLoginRequest(String email, String password) {
		this.email = email;
		this.password = password;
	}
}
