package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dtos.MessageDto;
import org.example.services.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequiredArgsConstructor

@RestController
public class MessagesController {
	private final MessageService messageService;
	private final AuthorizationService authorizationService;


	@GetMapping(value = "/messages/user1/{userId1}/user2/{userId2}/with-limit")
	public List<MessageDto> getMessages(@RequestHeader UUID token,
	                                    @PathVariable UUID userId1,
	                                    @PathVariable UUID userId2,
	                                    @RequestParam int limit) {
		authorizationService.validateToken(token);
		return messageService.getMessages(userId1, userId2, limit);
	}

	@PostMapping(value = "/messages")
	public MessageDto newMessage(@RequestHeader UUID token, @RequestBody MessageDto messageDto) {
		authorizationService.validateToken(token);
		return messageService.newMessage(messageDto);
	}

	@DeleteMapping(value = "/messages/{id}")
	public MessageDto deleteMessageById(@RequestHeader UUID token, @PathVariable UUID id) {
		authorizationService.validateToken(token);
		return messageService.deleteMessage(id);
	}



}

