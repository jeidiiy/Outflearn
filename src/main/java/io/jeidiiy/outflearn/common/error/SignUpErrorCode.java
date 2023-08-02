package io.jeidiiy.outflearn.common.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SignUpErrorCode implements ErrorCode {
	EMAIL_DUPLICATE(HttpStatus.CONFLICT, "Email is exists"),
	INVALID_VERIFICATION_CODE(HttpStatus.BAD_REQUEST, "Invalid code");

	private final HttpStatus httpStatus;
	private final String message;
}
