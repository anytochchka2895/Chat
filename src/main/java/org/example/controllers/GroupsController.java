package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dtos.*;
import org.example.services.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequiredArgsConstructor
@RestController
public class GroupsController {
	private final AuthorizationService authorizationService;
	private final GroupService groupService;


	@PostMapping(value = "/groups/create")
	public GroupDto newGroup(@RequestHeader UUID token, @RequestBody CreateGroupDto createGroupDto) {
		authorizationService.validateToken(token);
		return groupService.newGroup(createGroupDto);
	}

	@PostMapping(value = "/groups/{groupId}/users/{userId}")
	public void addUserInGroup(@RequestHeader UUID token,
	                           @PathVariable UUID groupId,
	                           @PathVariable UUID userId) {
		authorizationService.validateToken(token);
		groupService.addUserInGroup(groupId, userId);
	}

	@DeleteMapping(value = "/groups/{groupId}/users/{userId}")
	public void deleteUserFromGroup(@RequestHeader UUID token,
	                                @PathVariable UUID groupId,
	                                @PathVariable UUID userId) {
		authorizationService.validateToken(token);
		groupService.deleteUserFromGroup(groupId, userId);
	}


	@GetMapping(value = "/groups/users/{userId}")
	public List<GroupDto> showAllGroupsByUser(@RequestHeader UUID token, @PathVariable UUID userId){
		authorizationService.validateToken(token);
		return groupService.showAllGroupsByUser(userId);
	}


	@GetMapping(value = "/groups/{groupId}/messages/with-limit")
	public List<MessageGroupDto> showMessagesFromGroup(@RequestHeader UUID token,
	                                                   @PathVariable UUID groupId,
	                                                   @RequestParam int limit){
		authorizationService.validateToken(token);
		return groupService.showMessagesFromGroup(groupId, limit);
	}

	@GetMapping (value = "/groups/{groupId}/users")
	public List<UserDto> showAllUsersInGroup(@RequestHeader UUID token, @PathVariable UUID groupId){
		authorizationService.validateToken(token);
		return groupService.showAllUsersInGroup(groupId);
	}

	@PostMapping(value = "/groups/messages")
	public MessageGroupDto newMessageInGroup(@RequestHeader UUID token,
	                                         @RequestBody CreateMessageGroupDto createMessageGroupDto){
		authorizationService.validateToken(token);
		return groupService.newMessageInGroup(createMessageGroupDto);
	}

	@DeleteMapping(value = "/users/{currentUserId}/messages/{messageId}")
	public void deleteMessageFromGroupByCurrentUser(@RequestHeader UUID token,
	                                                @PathVariable UUID currentUserId,
	                                                @PathVariable UUID messageId){
		authorizationService.validateToken(token);
		groupService.deleteMessageFromGroupByCurrentUser(currentUserId, messageId);
	}

	@PostMapping(value = "/groups")
	public GroupDto updateGroup(@RequestHeader UUID token, @RequestBody GroupDto groupDto){
		authorizationService.validateToken(token);
		return groupService.updateGroup(groupDto);
	}

	@DeleteMapping(value = "/groups/{groupId}")
	public void deleteGroup(@RequestHeader UUID token, @PathVariable UUID groupId){
		authorizationService.validateToken(token);
		groupService.deleteGroup(groupId);
	}

}
