package io.jeidiiy.outflearn.web.validation.password;

import java.text.MessageFormat;

import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class PasswordValidator implements ConstraintValidator<Password, String> {

	private static final int MIN_SIZE = 8;
	private static final int MAX_SIZE = 50;
	private static final String regexPassword =
		"^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@!%*#?&])[A-Za-z0-9$@!%*#?&]{" + MIN_SIZE
			+ "," + MAX_SIZE + "}$";

	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
		boolean isValidPassword = password.matches(regexPassword);
		if (!isValidPassword) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(
					MessageFormat.format("{0}자 이상, {1}자 이하의 숫자, 영문자, 특수문자를 포함한 비밀번호를 입력해 주세요", MIN_SIZE, MAX_SIZE))
				.addConstraintViolation();
		}
		return isValidPassword;
	}

	@Override
	public void initialize(Password constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}
}
