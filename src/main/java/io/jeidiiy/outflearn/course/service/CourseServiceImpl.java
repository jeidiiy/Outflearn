package io.jeidiiy.outflearn.course.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jeidiiy.outflearn.course.controller.port.CourseService;
import io.jeidiiy.outflearn.course.domain.Course;
import io.jeidiiy.outflearn.course.domain.CourseCreate;
import io.jeidiiy.outflearn.course.service.port.CourseRepository;
import io.jeidiiy.outflearn.user.domain.User;
import io.jeidiiy.outflearn.user.service.port.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
@Builder
public class CourseServiceImpl implements CourseService {

	private final CourseRepository courseRepository;
	private final UserRepository userRepository;

	@Override
	public Long create(CourseCreate courseCreate, Long creatorId) {
		User creator = userRepository.getById(creatorId);
		Course course = courseRepository.save(Course.from(courseCreate, creator));
		return course.getId();
	}
}
