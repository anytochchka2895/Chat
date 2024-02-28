package org.example.controllers;

import org.example.dtos.*;
import org.example.services.*;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UsersController {
	private final UserService userService;
	private final AuthorizationService authorizationService;

	public UsersController(UserService userService, AuthorizationService authorizationService) {
		this.userService = userService;
		this.authorizationService = authorizationService;
	}


	@PostMapping(value = "/users")
	public UserDto createUser(@RequestBody UserLoginDto newUser) {
		return userService.createUser(newUser);
	}


	@PostMapping(value = "/users/login")
	public TokenDto authorization(@RequestBody UserLoginDto userLoginDto) {
		return authorizationService.authorization(userLoginDto);
	}

	@PostMapping(value = "/users/update")
	public UserDto updateUser(@RequestHeader UUID token, @RequestBody UserDto userDto) {
		authorizationService.validateToken(token);
		return userService.updateUser(userDto);
	}

	@GetMapping(value = "/users/{userId}")
	public UserDto getUserById(@RequestHeader UUID token, @PathVariable UUID userId) {
		authorizationService.validateToken(token);
		return userService.getUserById(userId);
	}

	@GetMapping(value = "/users/get-by-filters")
	public UserDto getByFilters(@RequestHeader UUID token,
	                            @RequestParam(required = false) String name,
	                            @RequestParam(required = false) Long phone) {
		authorizationService.validateToken(token);
		return userService.getByFilters(name, phone);
	}


}
