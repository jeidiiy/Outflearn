package io.jeidiiy.outflearn.course.domain;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class CourseCreate {

	@NotEmpty
	private String title;

	private String description;
}
