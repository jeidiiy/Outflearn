package io.jeidiiy.outflearn.common.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode{
	UNAUTHORIZED(HttpStatus.NOT_FOUND, "Unauthorized"),
	UNAUTHENTICATED(HttpStatus.UNAUTHORIZED, "Unauthenticated");

	private final HttpStatus httpStatus;
	private final String message;
}
