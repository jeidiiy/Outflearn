package io.jeidiiy.outflearn.unit.controller;

import static io.jeidiiy.outflearn.api.Endpoint.Api.*;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.jeidiiy.outflearn.unit.controller.port.UnitService;
import io.jeidiiy.outflearn.unit.controller.request.UnitRequest;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping(UNIT)
@Builder
public class UnitController {

	private final UnitService unitService;

	@GetMapping("/{id}")
	public ResponseEntity<String> get(@PathVariable Long id) {
		return ResponseEntity.ok(unitService.getUrlById(id));
	}

	@PostMapping
	public ResponseEntity<Void> create(
		@RequestPart(name = "details") UnitRequest unitRequest,
		@RequestPart(name = "vod") MultipartFile vod) throws IOException {
		validateExtensionIsMp4(vod);

		unitService.create(unitRequest, vod);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	private void validateExtensionIsMp4(MultipartFile vod) {
		String originalFilename = vod.getOriginalFilename();
		assert originalFilename != null;
		String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

		if (!ext.equals("mp4")) {
			throw new IllegalArgumentException("Only Mp4 file is available");
		}
	}
}
