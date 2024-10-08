package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dtos.*;
import org.example.services.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequiredArgsConstructor

@RestController
public class ContactsController {
	private final AuthorizationService authorizationService;
	private final ContactService contactService;


	@PostMapping(value = "/contacts")
	public ContactDto newContact(@RequestHeader UUID token, @RequestBody AddContactDto addContactDto) {
		authorizationService.validateToken(token);
		return contactService.newContact(addContactDto);
	}

	@DeleteMapping(value = "/contacts/{id}")
	public void deleteContactById(@RequestHeader UUID token, @PathVariable UUID id) {
		authorizationService.validateToken(token);
		contactService.deleteContactById(id);
	}

	@GetMapping(value = "/users/{userId}/contacts")
	public List<ContactDto> showAll(@RequestHeader UUID token, @PathVariable UUID userId){
		authorizationService.validateToken(token);
		return contactService.showAll(userId);
	}


}
