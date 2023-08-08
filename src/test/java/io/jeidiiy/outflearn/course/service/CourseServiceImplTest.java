package io.jeidiiy.outflearn.course.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.jeidiiy.outflearn.course.domain.CourseCreate;
import io.jeidiiy.outflearn.mock.TestContainer;
import io.jeidiiy.outflearn.user.domain.User;
import io.jeidiiy.outflearn.user.domain.UserStatus;

public class CourseServiceImplTest {
	private TestContainer testContainer;
	private CourseServiceImpl courseService;

	@BeforeEach
	void init() {
		this.testContainer = TestContainer.builder().build();
		this.courseService = CourseServiceImpl.builder()
			.courseRepository(testContainer.courseRepository)
			.userRepository(testContainer.userRepository)
			.build();
		testContainer.userRepository.save(User.builder()
			.email("test@gmail.com")
			.nickname("tester")
			.status(UserStatus.ACTIVE)
			.build());
	}

	@Test
	void CourseCreate와_계정의_아이디를_이용하여_Course를_생성할_수_있다() {
		// given
		CourseCreate courseCreate = CourseCreate.builder()
			.title("Spring Boot")
			.description("Learn about Spring Boot")
			.build();
		Long userId = testContainer.userRepository.findByEmail("test@gmail.com").get().getId();

		// when
		Long savedCourseId = courseService.create(courseCreate, userId);

		// then
		assertThat(savedCourseId).isEqualTo(1L);
	}
}
