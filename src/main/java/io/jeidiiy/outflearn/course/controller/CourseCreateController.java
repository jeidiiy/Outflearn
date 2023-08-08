package io.jeidiiy.outflearn.course.controller;

import static io.jeidiiy.outflearn.api.Endpoint.Api.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jeidiiy.outflearn.course.controller.port.CourseService;
import io.jeidiiy.outflearn.course.controller.response.CourseCreateResponse;
import io.jeidiiy.outflearn.course.domain.CourseCreate;
import io.jeidiiy.outflearn.security.login.ajax.domain.LoginUser;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(COURSE)
@RequiredArgsConstructor
@Builder
public class CourseCreateController {

	private final CourseService courseService;

	@PostMapping
	// TODO: 강사만 강의를 만들 수 있도록 인가 처리
	public ResponseEntity<CourseCreateResponse> create(
		@Validated @RequestBody CourseCreate courseCreate,
		@AuthenticationPrincipal LoginUser loginUser
	) {
		Long createdCourseId = courseService.create(courseCreate, loginUser.getId());
		return ResponseEntity
			.status(HttpStatus.CREATED.value())
			.body(CourseCreateResponse.builder()
				.id(createdCourseId)
				.creator(loginUser.getUsername()).build());
	}
}
