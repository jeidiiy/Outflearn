package io.jeidiiy.outflearn.security.login.ajax.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class AjaxLoginFailureDto {
	private final String errorMessage;

	private AjaxLoginFailureDto(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public static AjaxLoginFailureDto from(String errorMessage) {
		return new AjaxLoginFailureDto(errorMessage);
	}
}
