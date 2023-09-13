package io.jeidiiy.outflearn.section.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jeidiiy.outflearn.course.domain.Course;
import io.jeidiiy.outflearn.course.service.port.CourseRepository;
import io.jeidiiy.outflearn.section.controller.port.SectionService;
import io.jeidiiy.outflearn.section.domain.Section;
import io.jeidiiy.outflearn.section.domain.SectionCreate;
import io.jeidiiy.outflearn.section.service.port.SectionRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
@Builder
public class SectionServiceImpl implements SectionService {
	private final SectionRepository sectionRepository;
	private final CourseRepository courseRepository;

	@Override
	public Long create(SectionCreate sectionCreate) {
		Course course = courseRepository.findById(sectionCreate.getCourseId());
		Section section = sectionRepository.create(Section.from(sectionCreate, course));
		return section.getId();
	}
}
