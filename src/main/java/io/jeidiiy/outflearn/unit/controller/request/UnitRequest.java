package io.jeidiiy.outflearn.unit.controller.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class UnitRequest {
	@NotNull
	private final Long sectionId;

	@NotEmpty
	private final String title;

	private final String body;

	@NotEmpty
	private final Boolean isPreview;
}
