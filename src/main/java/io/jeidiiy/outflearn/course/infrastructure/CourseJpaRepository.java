package io.jeidiiy.outflearn.course.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import io.jeidiiy.outflearn.course.domain.Course;

public interface CourseJpaRepository extends JpaRepository<Course, Long> {
}
