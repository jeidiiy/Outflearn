package io.jeidiiy.outflearn.section.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import io.jeidiiy.outflearn.section.domain.Section;

public interface SectionJpaRepository extends JpaRepository<Section, Long> {
}
