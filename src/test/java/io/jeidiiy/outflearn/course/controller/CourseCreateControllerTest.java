package io.jeidiiy.outflearn.course.controller;

import static org.assertj.core.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import io.jeidiiy.outflearn.course.controller.response.CourseCreateResponse;
import io.jeidiiy.outflearn.course.domain.CourseCreate;
import io.jeidiiy.outflearn.mock.TestContainer;
import io.jeidiiy.outflearn.security.login.ajax.domain.LoginUser;
import io.jeidiiy.outflearn.user.domain.User;
import io.jeidiiy.outflearn.user.domain.UserStatus;

public class CourseCreateControllerTest {

	private TestContainer testContainer;
	private LoginUser loginUser;

	@BeforeEach
	void init() {
		testContainer = TestContainer.builder().build();
		testContainer.userRepository.save(User.builder()
			.email("test@gmail.com")
			.nickname("tester")
			.status(UserStatus.ACTIVE)
			.build());
		loginUser = LoginUser.from(
			testContainer.userRepository.getById(1L),
			Set.of(new SimpleGrantedAuthority("ROLE_TEACHER"))
		);
	}

	@Test
	void 사용자는_강의를_만들_수_있다() {
		// given
		CourseCreate courseCreate = CourseCreate.builder()
			.title("Spring Boot")
			.description("Learn about Spring Boot")
			.build();

		// when
		ResponseEntity<CourseCreateResponse> response =
			testContainer.courseCreateController.create(courseCreate, loginUser);

		// then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getId()).isEqualTo(1);
		assertThat(response.getBody().getCreator()).isEqualTo("tester");
	}
}
