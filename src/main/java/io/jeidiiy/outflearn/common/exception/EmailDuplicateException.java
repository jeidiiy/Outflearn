package io.jeidiiy.outflearn.common.exception;

public class EmailDuplicateException extends RuntimeException {
	public EmailDuplicateException() {
		super("이미 가입된 이메일입니다. 다른 이메일을 사용해 주세요");
	}
}
