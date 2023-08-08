package io.jeidiiy.outflearn.course.controller.port;

import io.jeidiiy.outflearn.course.domain.CourseCreate;

public interface CourseService {
	Long create(CourseCreate courseCreate, Long creatorId);
}
