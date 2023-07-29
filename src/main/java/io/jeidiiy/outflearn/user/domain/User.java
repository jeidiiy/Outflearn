package io.jeidiiy.outflearn.user.domain;

import static io.jeidiiy.outflearn.util.MyStringUtils.*;

import org.springframework.security.crypto.password.PasswordEncoder;

import io.jeidiiy.outflearn.common.exception.VerificationCodeNotMatchedException;
import io.jeidiiy.outflearn.common.service.port.UuidHolder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "users")
@Getter
@ToString
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String nickname;

	@Column(nullable = false)
	private String password;

	@Column
	private String verificationCode;

	@Column
	@Enumerated(EnumType.STRING)
	private UserStatus status;

	@Column
	private Long lastLoginAt;

	@Builder
	public User(Long id, String email, String nickname, String password, String verificationCode, UserStatus status,
		Long lastLoginAt) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.verificationCode = verificationCode;
		this.status = status;
		this.lastLoginAt = lastLoginAt;
	}

	public static User from(UserCreate userCreate, UuidHolder uuidHolder, PasswordEncoder passwordEncoder) {
		return User.builder()
			.email(userCreate.getEmail())
			.nickname(extractEmailLocal(userCreate.getEmail()))
			.password(passwordEncoder.encode(userCreate.getPassword()))
			.status(UserStatus.PENDING)
			.verificationCode(uuidHolder.random())
			.build();
	}

	public void verify(String verificationCode) {
		if (!this.verificationCode.equals(verificationCode)) {
			throw new VerificationCodeNotMatchedException();
		}

		status = UserStatus.ACTIVE;
	}
}
