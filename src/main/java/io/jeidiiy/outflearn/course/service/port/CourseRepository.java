package io.jeidiiy.outflearn.course.service.port;

import io.jeidiiy.outflearn.course.domain.Course;

public interface CourseRepository {
	Course save(Course course);
}
