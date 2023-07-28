package io.jeidiiy.outflearn.user.controller;

import static io.jeidiiy.outflearn.api.Endpoint.Api.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jeidiiy.outflearn.user.controller.port.UserService;
import io.jeidiiy.outflearn.user.controller.response.UserResponse;
import io.jeidiiy.outflearn.user.domain.User;
import io.jeidiiy.outflearn.user.domain.UserCreate;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
public class UserCreateController {

	private final UserService userService;

	@PostMapping
	public ResponseEntity<UserResponse> create(@Validated @RequestBody UserCreate userCreate) {
		User user = userService.create(userCreate);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(UserResponse.from(user));
	}
}
