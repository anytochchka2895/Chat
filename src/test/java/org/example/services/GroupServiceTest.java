package org.example.services;

import org.example.dtos.*;
import org.example.entities.*;
import org.example.repositories.*;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {

	@Mock GroupRepository groupRepository;
	@Mock MessageGroupRepository messageGroupRepository;
	@Mock UserGroupRepository userGroupRepository;
	@Mock UserRepository userRepository;

	GroupService groupService;

	EasyRandom generator = new EasyRandom();

	@BeforeEach
	void init() {
		groupService = new GroupService(groupRepository, messageGroupRepository,
		                                userGroupRepository, userRepository);
	}

	@Test
	void newGroupTest() {
		//GIVEN
		CreateGroupDto createGroupDto = generator.nextObject(CreateGroupDto.class);

		//MOCK
		when(userGroupRepository.findByGroupIdAndUserId(any(), eq(createGroupDto.getCurrentUserId())))
				.thenReturn(Optional.empty());
		//WHEN
		GroupDto resultGroupDto = groupService.newGroup(createGroupDto);

		//THEN
		verify(groupRepository, times(1)).save(any());
		verify(userGroupRepository, times(1)).findByGroupIdAndUserId(any(), eq(createGroupDto.getCurrentUserId()));
		verify(userGroupRepository, times(1)).save(any());
		assertNotNull(resultGroupDto.getId());
		assertNotNull(resultGroupDto.getCreatedAt());
		assertNotNull(resultGroupDto.getTimeLastMessage());
		assertEquals(createGroupDto.getName(), resultGroupDto.getName());

	}


	@Test
	void addUserInGroupTest() {
		//GIVEN
		UUID groupId = generator.nextObject(UUID.class);
		UUID userId = generator.nextObject(UUID.class);
		UserGroupEntity userGroupEntity = generator.nextObject(UserGroupEntity.class);

		//MOCK
		when(userGroupRepository.findByGroupIdAndUserId(groupId, userId))
				.thenReturn(Optional.of(userGroupEntity));

		//WHEN
		RuntimeException thrown = assertThrows(
				RuntimeException.class,
				() -> groupService.addUserInGroup(groupId, userId));

		//THEN
		assertTrue(thrown.getMessage().contains("Данный пользователь уже есть в группе"));
	}


	@Test
	void deleteUserFromGroupTest() {
		//GIVEN
		UUID groupId = generator.nextObject(UUID.class);
		UUID userId = generator.nextObject(UUID.class);
		//MOCK

		//WHEN
		groupService.deleteUserFromGroup(groupId, userId);
		//THEN
		verify(userGroupRepository, times(1)).deleteByGroupIdAndUserId(groupId, userId);
	}

	@Test
	void showAllGroupsByUserTest() {
		//GIVEN
		UUID userId = generator.nextObject(UUID.class);
		List<GroupEntity> entityList = generator.objects(GroupEntity.class, 11).toList();
		//MOCK
		when(groupRepository.findAllByUserIdOrderByTimeLastMessageDesc(userId))
				.thenReturn(entityList);
		//WHEN
		List<GroupDto> resultGroupDtoList = groupService.showAllGroupsByUser(userId);
		//THEN
		assertEquals(entityList.size(), resultGroupDtoList.size());
		verify(groupRepository, times(1)).findAllByUserIdOrderByTimeLastMessageDesc(userId);
	}


	@Test
	void showMessagesFromGroupTest() {
		//GIVEN
		UUID groupId = generator.nextObject(UUID.class);
		int limit = 30;
		List<MessageGroupEntity> messageGroupEntityList = generator.objects(MessageGroupEntity.class, 30).toList();
		List<UserEntity> usersByIdEntities = generator.objects(UserEntity.class, 30).toList();
		//MOCK
		when(messageGroupRepository.findAllByGroupIdWithLimit(groupId, limit))
				.thenReturn(messageGroupEntityList);
		when(userRepository.findAllById(any())).thenReturn(usersByIdEntities);
		//WHEN
		List<MessageGroupDto> resultDtoList = groupService.showMessagesFromGroup(groupId, limit);
		//THEN
		assertEquals(30, resultDtoList.size());
	}


	@Test
	void showMessagesFromGroupSingleTest() {
		//GIVEN
		UUID groupId = generator.nextObject(UUID.class);
		int limit = 1;
		MessageGroupEntity messageGroupEntity = generator.nextObject(MessageGroupEntity.class);
		messageGroupEntity.setGroupId(groupId);
		UserEntity userByIdEntity = generator.nextObject(UserEntity.class);
		userByIdEntity.setId(messageGroupEntity.getFromUserId());
		//MOCK
		when(messageGroupRepository.findAllByGroupIdWithLimit(groupId, limit))
				.thenReturn(List.of(messageGroupEntity));
		when(userRepository.findAllById(any())).thenReturn(List.of(userByIdEntity));
		//WHEN
		List<MessageGroupDto> resultDtoList = groupService.showMessagesFromGroup(groupId, limit);
		//THEN
		assertEquals(1, resultDtoList.size());
		MessageGroupDto resultMessageGroupDto = resultDtoList.get(0);
		assertEquals(groupId, resultMessageGroupDto.getGroupId());
		assertEquals(messageGroupEntity.getText(), resultMessageGroupDto.getText());
		assertEquals(userByIdEntity.getId(), resultMessageGroupDto.getFromUserId());
	}


	@Test
	void showAllUsersInGroupTest() {
		//GIVEN
		UUID groupId = generator.nextObject(UUID.class);
		List<UserGroupEntity> allUsersByGroupId = generator.objects(UserGroupEntity.class, 15).toList();
		List<UserEntity> allUsersEntityByIds = generator.objects(UserEntity.class, 15).toList();
		//MOCK
		when(userGroupRepository.findAllByGroupId(groupId)).thenReturn(allUsersByGroupId);
		when(userRepository.findAllById(any())).thenReturn(allUsersEntityByIds);
		//WHEN
		List<UserDto> resultUserDtoList = groupService.showAllUsersInGroup(groupId);
		//THEN
		assertEquals(allUsersEntityByIds.size(), resultUserDtoList.size());
	}


	@Test
	void showAllUsersInGroupSingleTest() {
		//GIVEN
		UUID groupId = generator.nextObject(UUID.class);
		UserGroupEntity userByGroupId = generator.nextObject(UserGroupEntity.class);
		userByGroupId.setGroupId(groupId);
		UserEntity userEntityById = generator.nextObject(UserEntity.class);
		userEntityById.setId(userByGroupId.getUserId());
		//MOCK
		when(userGroupRepository.findAllByGroupId(groupId)).thenReturn(List.of(userByGroupId));
		when(userRepository.findAllById(any())).thenReturn(List.of(userEntityById));
		//WHEN
		List<UserDto> resultUserDtoList = groupService.showAllUsersInGroup(groupId);
		//THEN
		assertEquals(1, resultUserDtoList.size());
		UserDto resultUserDto = resultUserDtoList.get(0);
		assertEquals(userByGroupId.getUserId(), resultUserDto.getId());
		assertEquals(userEntityById.getName(), resultUserDto.getName());
		assertEquals(userEntityById.getPhone(), resultUserDto.getPhone());
	}


	@Test
	void newMessageInGroupTest() {
		//GIVEN
		CreateMessageGroupDto createMessageGroupDto = generator.nextObject(CreateMessageGroupDto.class);
		GroupEntity groupEntity = generator.nextObject(GroupEntity.class);
		ZonedDateTime timeGroupEntity = groupEntity.getTimeLastMessage();
		groupEntity.setId(createMessageGroupDto.getGroupId());
		UserEntity userEntity = generator.nextObject(UserEntity.class);
		userEntity.setId(createMessageGroupDto.getFromUserId());
		//MOCK
		when(groupRepository.findById(createMessageGroupDto.getGroupId())).thenReturn(Optional.of(groupEntity));
		when(userRepository.findById(createMessageGroupDto.getFromUserId())).thenReturn(Optional.of(userEntity));
		//WHEN
		MessageGroupDto resultMessageGroupDto = groupService.newMessageInGroup(createMessageGroupDto);
		//THEN
		assertNotNull(resultMessageGroupDto);
		assertEquals(groupEntity.getTimeLastMessage(), resultMessageGroupDto.getCreatedAt());
		assertEquals(createMessageGroupDto.getGroupId(), resultMessageGroupDto.getGroupId());
		assertEquals(createMessageGroupDto.getFromUserId(), resultMessageGroupDto.getFromUserId());
		assertEquals(userEntity.getId(), resultMessageGroupDto.getFromUserId());
		verify(messageGroupRepository, times(1)).save(argThat(messageGroupEntity -> {
			assertNotNull(messageGroupEntity.getId());
			assertNotNull(messageGroupEntity.getCreatedAt());
			return true;
		}));
		verify(groupRepository, times(1)).save(argThat(savedGroupEntity -> {
			assertNotEquals(timeGroupEntity, savedGroupEntity.getTimeLastMessage());
			return true;
		}));
	}


	@Test
	void deleteMessageFromGroupByCurrentUserTest() {
		//GIVEN
		UUID currentUserId = generator.nextObject(UUID.class);
		UUID messageId = generator.nextObject(UUID.class);
		MessageGroupEntity messageGroupRepositoryById = generator.nextObject(MessageGroupEntity.class);
		messageGroupRepositoryById.setFromUserId(currentUserId);
		messageGroupRepositoryById.setId(messageId);
		//MOCK
		when(messageGroupRepository.findById(messageId)).thenReturn(Optional.of(messageGroupRepositoryById));
		//WHEN
		groupService.deleteMessageFromGroupByCurrentUser(currentUserId, messageId);
		//THEN
		verify(messageGroupRepository, times(1)).deleteById(messageId);
	}


	@Test
	void dontDeleteMessageFromGroupByCurrentUserTest() {
		//GIVEN
		UUID currentUserId = generator.nextObject(UUID.class);
		UUID messageId = generator.nextObject(UUID.class);
		MessageGroupEntity messageGroupRepositoryById = generator.nextObject(MessageGroupEntity.class);

		//MOCK
		when(messageGroupRepository.findById(messageId)).thenReturn(Optional.of(messageGroupRepositoryById));
		//WHEN
		RuntimeException thrown = assertThrows(
				RuntimeException.class,
				() -> groupService.deleteMessageFromGroupByCurrentUser(currentUserId, messageId));

		//THEN
		assertTrue(thrown.getMessage().contains("Вы не можете удалить данное сообщение"));
	}


	@Test
	void updateGroupTest() {
		//GIVEN
		GroupDto groupDto = generator.nextObject(GroupDto.class);
		GroupEntity groupEntity = generator.nextObject(GroupEntity.class);
		groupEntity.setId(groupDto.getId());
		//MOCK
		when(groupRepository.findById(groupDto.getId())).thenReturn(Optional.of(groupEntity));
		when(groupRepository.save(groupEntity)).thenReturn(groupEntity);
		//WHEN
		GroupDto resultGroupDto = groupService.updateGroup(groupDto);
		//THEN
		assertNotNull(resultGroupDto);
		assertEquals(groupDto.getId(), resultGroupDto.getId());
		assertEquals(groupDto.getName(), resultGroupDto.getName());
		assertEquals(groupDto.getInfo(), resultGroupDto.getInfo());
	}


	@Test
	void notUpdateGroupTest() {
		//GIVEN
		GroupDto groupDto = generator.nextObject(GroupDto.class);
		//MOCK
		when(groupRepository.findById(groupDto.getId())).thenReturn(Optional.empty());
		//WHEN
		RuntimeException thrown = assertThrows(
				RuntimeException.class,
				() -> groupService.updateGroup(groupDto));

		//THEN
		assertTrue(thrown.getMessage().contains("Группа не найдена"));
	}


	@Test
	void deleteGroupTest() {
		//GIVEN
		UUID groupId = generator.nextObject(UUID.class);
		//MOCK

		//WHEN
		groupService.deleteGroup(groupId);
		//THEN
		verify(messageGroupRepository, times(1)).deleteAllByGroupId(groupId);
		verify(userGroupRepository, times(1)).deleteAllByGroupId(groupId);
		verify(groupRepository, times(1)).deleteById(groupId);
	}
}