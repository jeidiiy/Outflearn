package io.jeidiiy.outflearn.section.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class SectionCreate {
	@NotNull
	private Long courseId;
	@NotEmpty
	private String title;
}
