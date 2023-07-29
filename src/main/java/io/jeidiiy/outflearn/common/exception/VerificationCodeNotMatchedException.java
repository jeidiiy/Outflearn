package io.jeidiiy.outflearn.common.exception;

public class VerificationCodeNotMatchedException extends RuntimeException {
	public VerificationCodeNotMatchedException() {
		super("인증 코드가 유효하지 않습니다.");
	}
}
