package io.jeidiiy.outflearn.course.infrastructure;

import org.springframework.stereotype.Repository;

import io.jeidiiy.outflearn.common.exception.ResourceNotFoundException;
import io.jeidiiy.outflearn.course.domain.Course;
import io.jeidiiy.outflearn.course.service.port.CourseRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CourseRepositoryImpl implements CourseRepository {

	private final CourseJpaRepository courseJpaRepository;

	@Override
	public Course findById(Long id) {
		return courseJpaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Course", id));
	}

	@Override
	public Course save(Course course) {
		return courseJpaRepository.save(course);
	}
}
