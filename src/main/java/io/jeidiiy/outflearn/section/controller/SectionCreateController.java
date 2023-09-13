package io.jeidiiy.outflearn.section.controller;

import static io.jeidiiy.outflearn.api.Endpoint.Api.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jeidiiy.outflearn.section.controller.port.SectionService;
import io.jeidiiy.outflearn.section.controller.response.SectionCreateResponse;
import io.jeidiiy.outflearn.section.domain.SectionCreate;
import io.jeidiiy.outflearn.security.login.ajax.domain.LoginUser;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(SECTION)
@RequiredArgsConstructor
@Builder
public class SectionCreateController {
	private final SectionService sectionService;

	@PostMapping
	public ResponseEntity<SectionCreateResponse> create(
		@Validated @RequestBody SectionCreate sectionCreate,
		@AuthenticationPrincipal LoginUser loginUser
	) {
		Long createdSectionId = sectionService.create(sectionCreate);
		return ResponseEntity
			.status(HttpStatus.CREATED.value())
			.body(SectionCreateResponse.builder()
				.id(createdSectionId)
				.build());
	}
}
