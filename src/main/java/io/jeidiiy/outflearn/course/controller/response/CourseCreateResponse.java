package io.jeidiiy.outflearn.course.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class CourseCreateResponse {
	private Long id;
	private String creator;
}
