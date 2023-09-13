package io.jeidiiy.outflearn.section.infrastructure;

import org.springframework.stereotype.Repository;

import io.jeidiiy.outflearn.section.domain.Section;
import io.jeidiiy.outflearn.section.service.port.SectionRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SectionRepositoryImpl implements SectionRepository {
	private final SectionJpaRepository sectionJpaRepository;

	@Override
	public Section create(Section section) {
		return sectionJpaRepository.save(section);
	}
}
