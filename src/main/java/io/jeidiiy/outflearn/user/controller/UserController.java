package io.jeidiiy.outflearn.user.controller;

import static io.jeidiiy.outflearn.api.Endpoint.Api.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.jeidiiy.outflearn.user.controller.port.UserService;
import io.jeidiiy.outflearn.user.controller.response.UserResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(USER)
public class UserController {

	private final UserService userService;

	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
		return ResponseEntity
			.ok()
			.body(UserResponse.from(userService.getById(id)));
	}

	@GetMapping("/{id}/verify")
	public ResponseEntity<Void> verifyEmail(@PathVariable Long id, @RequestParam String verificationCode) {
		userService.verifyEmail(id, verificationCode);
		return ResponseEntity.ok().build();
	}
}
