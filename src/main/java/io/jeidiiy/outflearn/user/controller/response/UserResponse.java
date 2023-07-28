package io.jeidiiy.outflearn.user.controller.response;

import io.jeidiiy.outflearn.user.domain.User;
import io.jeidiiy.outflearn.user.domain.UserStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class UserResponse {

	private Long id;
	private String email;
	private String nickname;
	private UserStatus status;
	private Long lastLoginAt;

	public static UserResponse from(User user) {
		return UserResponse.builder()
			.id(user.getId())
			.email(user.getEmail())
			.nickname(user.getNickname())
			.status(user.getStatus())
			.lastLoginAt(user.getLastLoginAt())
			.build();
	}
}
