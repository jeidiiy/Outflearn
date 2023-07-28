package io.jeidiiy.outflearn.common.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jeidiiy.outflearn.common.exception.ResourceNotFoundException;
import io.jeidiiy.outflearn.common.exception.VerificationCodeNotMatchedException;

@RestControllerAdvice
public class ExceptionRestControllerAdvice {

	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(RuntimeException.class)
	public String serverError(RuntimeException exception) {
		return "Internal Server Error";
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public String badRequest(MethodArgumentNotValidException exception) {
		return exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(VerificationCodeNotMatchedException.class)
	public String verificationCodeNotMatchedException(VerificationCodeNotMatchedException exception) {
		return exception.getMessage();
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(ResourceNotFoundException.class)
	public String notFound(ResourceNotFoundException exception) {
		return exception.getMessage();
	}
}