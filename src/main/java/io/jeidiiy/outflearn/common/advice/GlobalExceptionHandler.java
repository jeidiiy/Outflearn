package io.jeidiiy.outflearn.common.advice;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.jeidiiy.outflearn.common.error.CommonErrorCode;
import io.jeidiiy.outflearn.common.error.ErrorCode;
import io.jeidiiy.outflearn.common.error.ErrorResponse;
import io.jeidiiy.outflearn.common.error.SignUpErrorCode;
import io.jeidiiy.outflearn.common.exception.EmailDuplicateException;
import io.jeidiiy.outflearn.common.exception.ResourceNotFoundException;
import io.jeidiiy.outflearn.common.exception.VerificationCodeNotMatchedException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorResponse> handleInternalServerError(RuntimeException ex) {
		log.warn("handleInternalServerError", ex);
		CommonErrorCode errorCode = CommonErrorCode.INTERNAL_SERVER_ERROR;
		return handleExceptionInternal(errorCode, ex.getMessage());
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
		MethodArgumentNotValidException ex,
		HttpHeaders headers,
		HttpStatusCode status,
		WebRequest request) {
		log.warn("handleMethodArgumentNotValid", ex);
		CommonErrorCode errorCode = CommonErrorCode.INVALID_PARAMETER;
		return handleExceptionInternal(ex, errorCode);
	}

	@ExceptionHandler(VerificationCodeNotMatchedException.class)
	public ResponseEntity<ErrorResponse> handleInvalidVerification(VerificationCodeNotMatchedException ex) {
		log.warn("handleInvalidVerification", ex);
		SignUpErrorCode errorCode = SignUpErrorCode.INVALID_VERIFICATION_CODE;
		return handleExceptionInternal(errorCode);
	}

	@ExceptionHandler(EmailDuplicateException.class)
	public ResponseEntity<ErrorResponse> handleDuplicateEmail(EmailDuplicateException ex) {
		log.warn("handleDuplicateEmail", ex);
		SignUpErrorCode errorCode = SignUpErrorCode.EMAIL_DUPLICATE;
		return handleExceptionInternal(errorCode);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
		log.warn("handleResourceNotFound", ex);
		CommonErrorCode errorCode = CommonErrorCode.RESOURCE_NOT_FOUND;
		return handleExceptionInternal(errorCode, errorCode.getMessage());
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
		log.warn("handleIllegalFileExtensionException", ex);
		CommonErrorCode errorCode = CommonErrorCode.INVALID_PARAMETER;
		return handleExceptionInternal(errorCode, ex.getMessage());
	}

	private ResponseEntity<ErrorResponse> handleExceptionInternal(ErrorCode errorCode) {
		return ResponseEntity.status(errorCode.getHttpStatus()).body(makeErrorResponse(errorCode));
	}

	private ErrorResponse makeErrorResponse(ErrorCode errorCode) {
		return ErrorResponse.builder()
			.code(errorCode.name())
			.message(errorCode.getMessage())
			.build();
	}

	private ResponseEntity<ErrorResponse> handleExceptionInternal(ErrorCode errorCode, String message) {
		return ResponseEntity.status(errorCode.getHttpStatus()).body(makeErrorResponse(errorCode, message));
	}

	private ErrorResponse makeErrorResponse(ErrorCode errorCode, String message) {
		return ErrorResponse.builder()
			.code(errorCode.name())
			.message(message)
			.build();
	}

	private ResponseEntity<Object> handleExceptionInternal(BindException ex, ErrorCode errorCode) {
		return ResponseEntity.status(errorCode.getHttpStatus())
			.body(makeErrorResponse(ex, errorCode));
	}

	private ErrorResponse makeErrorResponse(BindException ex, ErrorCode errorCode) {
		List<ErrorResponse.ValidationError> validationErrorList = ex.getBindingResult()
			.getFieldErrors()
			.stream()
			.map(ErrorResponse.ValidationError::of)
			.collect(Collectors.toList());

		return ErrorResponse.builder()
			.code(errorCode.name())
			.message(errorCode.getMessage())
			.errors(validationErrorList)
			.build();
	}
}
