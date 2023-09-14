package io.jeidiiy.outflearn.unit.service;

import io.jeidiiy.outflearn.common.exception.ResourceNotFoundException;
import io.jeidiiy.outflearn.section.controller.port.SectionService;
import io.jeidiiy.outflearn.section.domain.Section;
import io.jeidiiy.outflearn.section.service.port.SectionRepository;
import io.jeidiiy.outflearn.unit.controller.port.UnitService;
import io.jeidiiy.outflearn.unit.controller.request.UnitRequest;
import io.jeidiiy.outflearn.unit.domain.Unit;
import io.jeidiiy.outflearn.unit.service.port.UnitRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Transactional
@Service
@RequiredArgsConstructor
public class UnitServiceImpl implements UnitService {

	@Value("${file.dir}")
	private String fileDir;

	private final SectionService sectionService;
	private final UnitRepository unitRepository;

	@Override
	public String getUrlById(Long id) {
		Unit unit = unitRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("unit", id));
		return unit.getUrl();
	}

	@Override
	public void create(UnitRequest unitRequest, MultipartFile file) throws IOException {
		if (file.isEmpty()) {
			return;
		}

		// 원래 파일 이름 추출
		String originalFilename = file.getOriginalFilename();

		// 파일 이름으로 쓸 uuid 생성
		String uuid = UUID.randomUUID().toString();

		// 확장자 추출(ex : .mp4)
		String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

		// uuid와 확장자 결합
		String savedName = uuid + extension;

		// 파일을 불러올 때 사용할 파일 경로
		String savedPath = fileDir + savedName;

		// 유닛이 포함될 섹션 조회
		Section section = sectionService.findById(unitRequest.getSectionId());

		// 파일 엔티티 생성
		Unit unit = Unit.builder()
			.title(unitRequest.getTitle())
			.body(unitRequest.getBody())
			.section(section)
			.url(savedPath)
			.build();

		unitRepository.save(unit);

		// 실제로 로컬에 uuid를 파일명으로 저장
		file.transferTo(new File(savedPath));
	}
}
