package io.jeidiiy.outflearn.section.controller.port;

import io.jeidiiy.outflearn.section.domain.SectionCreate;

public interface SectionService {
	Long create(SectionCreate sectionCreate);
}
