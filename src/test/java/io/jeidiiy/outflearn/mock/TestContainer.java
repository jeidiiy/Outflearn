package io.jeidiiy.outflearn.mock;

import io.jeidiiy.outflearn.common.service.port.UuidHolder;
import io.jeidiiy.outflearn.course.controller.CourseCreateController;
import io.jeidiiy.outflearn.course.controller.port.CourseService;
import io.jeidiiy.outflearn.course.service.CourseServiceImpl;
import io.jeidiiy.outflearn.course.service.port.CourseRepository;
import io.jeidiiy.outflearn.user.controller.UserController;
import io.jeidiiy.outflearn.user.controller.UserCreateController;
import io.jeidiiy.outflearn.user.service.UserServiceImpl;
import io.jeidiiy.outflearn.user.service.VerificationService;
import io.jeidiiy.outflearn.user.service.port.MailSender;
import io.jeidiiy.outflearn.user.service.port.UserRepository;
import lombok.Builder;

public class TestContainer {
	public final MailSender mailSender;
	public final UserRepository userRepository;
	public final VerificationService verificationService;
	public final UserController userController;
	public final UserCreateController userCreateController;
	public final CourseRepository courseRepository;
	public final CourseService courseService;
	public final CourseCreateController courseCreateController;

	@Builder
	public TestContainer(UuidHolder uuidHolder) {
		this.mailSender = new FakeMailSender();
		this.userRepository = new FakeUserRepository();
		this.verificationService = new VerificationService(this.mailSender);
		UserServiceImpl userService = UserServiceImpl.builder()
			.uuidHolder(uuidHolder)
			.userRepository(this.userRepository)
			.verificationService(this.verificationService)
			.passwordEncoder(new TestPasswordEncoder())
			.build();
		this.userController = UserController.builder()
			.userService(userService)
			.build();
		this.userCreateController = UserCreateController.builder()
			.userService(userService)
			.build();
		this.courseRepository = new FakeCourseRepository();
		this.courseService = CourseServiceImpl.builder()
			.courseRepository(courseRepository)
			.userRepository(userRepository)
			.build();
		this.courseCreateController = CourseCreateController.builder()
			.courseService(courseService)
			.build();
	}
}
