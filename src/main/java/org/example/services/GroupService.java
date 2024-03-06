package org.example.services;

import org.example.dtos.*;
import org.example.entities.*;
import org.example.exceptions.GroupException;
import org.example.mappers.*;
import org.example.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.*;

@Service
public class GroupService {
	private final GroupRepository groupRepository;
	private final MessageGroupRepository messageGroupRepository;
	private final UserGroupRepository userGroupRepository;
	private final UserRepository userRepository;

	public GroupService(GroupRepository groupRepository,
	                    MessageGroupRepository messageGroupRepository,
	                    UserGroupRepository userGroupRepository, UserRepository userRepository) {
		this.groupRepository = groupRepository;
		this.messageGroupRepository = messageGroupRepository;
		this.userGroupRepository = userGroupRepository;
		this.userRepository = userRepository;
	}



	@Transactional
	public GroupDto newGroup(CreateGroupDto createGroupDto) {
		GroupEntity groupEntity = new GroupEntity();
		groupEntity.setId(UUID.randomUUID());
		groupEntity.setName(createGroupDto.getName());
		groupEntity.setCreatedAt(ZonedDateTime.now());
		//TimeLastMessage = updatedAt
		groupEntity.setTimeLastMessage(ZonedDateTime.now());
		groupRepository.save(groupEntity);

		addUserInGroup(groupEntity.getId(), createGroupDto.getCurrentUserId());

		return GroupMapper.groupToDto(groupEntity);
	}



	@Transactional
	public void addUserInGroup(UUID groupId, UUID userId) {
		UserGroupEntity userGroupEntity = new UserGroupEntity();
		userGroupEntity.setId(UUID.randomUUID());
		userGroupEntity.setGroupId(groupId);
		userGroupEntity.setUserId(userId);

		Optional<UserGroupEntity> find = userGroupRepository.findByGroupIdAndUserId(groupId, userId);
		if (find.isEmpty()) {
			userGroupRepository.save(userGroupEntity);
		}
		else {
			throw new GroupException("Данный пользователь уже есть в группе");
		}
	}



	@Transactional
	public void deleteUserFromGroup(UUID groupId, UUID userId) {
		userGroupRepository.deleteByGroupIdAndUserId(groupId, userId);
	}




	public List<GroupDto> showAllGroupsByUser(UUID userId) {
		List<GroupEntity> entityList = groupRepository.findAllByUserIdOrderByTimeLastMessageDesc(userId);
		return GroupMapper.groupToListDto(entityList);
	}




	public List<MessageGroupDto> showMessagesFromGroup(UUID groupId, int limit) {
		List<MessageGroupEntity> messageGroupEntityList = messageGroupRepository.findAllByGroupIdWithLimit(groupId, limit);

		List<UUID> usersIds = new ArrayList<>();
		for (MessageGroupEntity entity : messageGroupEntityList) {
			usersIds.add(entity.getFromUserId());
		}

//		List<UUID> uuids = messageGroupEntityList.stream()
//				.map(MessageGroupEntity::getFromUserId)
//				.toList();

		List<UserEntity> usersByIdEntities = userRepository.findAllById(usersIds);

		Map<UUID, String> usersByIdNames = new HashMap<>();
		for (UserEntity userEntity : usersByIdEntities) {
			usersByIdNames.put(userEntity.getId(), userEntity.getName());
		}

		List<MessageGroupDto> dtoList = new ArrayList<>();
		for (MessageGroupEntity entity : messageGroupEntityList) {
			MessageGroupDto messageGroupDto = new MessageGroupDto(entity.getGroupId(),
			                                                      entity.getFromUserId(),
			                                                      usersByIdNames.get(entity.getFromUserId()),
			                                                      entity.getGroupId(),
			                                                      entity.getText(),
			                                                      entity.getCreatedAt());
			dtoList.add(messageGroupDto);
		}

		return dtoList;
	}




	public List<UserDto> showAllUsersInGroup(UUID groupId) {
		List<UserGroupEntity> allUsersByGroupId = userGroupRepository.findAllByGroupId(groupId);

		List<UUID> userIds = new ArrayList<>();
		for (UserGroupEntity userGroupEntity : allUsersByGroupId) {
			userIds.add(userGroupEntity.getUserId());
		}

		List<UserEntity> allUsersEntityByIds = userRepository.findAllById(userIds);
		List<UserDto> usersDtos = new ArrayList<>();
		for (UserEntity entity : allUsersEntityByIds) {
			UserDto toDto = UserMapper.userToDto(entity);
			usersDtos.add(toDto);
		}
		return usersDtos;
	}



	@Transactional
	public MessageGroupDto newMessageInGroup(CreateMessageGroupDto createMessageGroupDto) {
		MessageGroupEntity messageGroupEntity = new MessageGroupEntity(UUID.randomUUID(),
		                                                               createMessageGroupDto.getFromUserId(),
		                                                               createMessageGroupDto.getGroupId(),
		                                                               createMessageGroupDto.getText(),
		                                                               ZonedDateTime.now());
		messageGroupRepository.save(messageGroupEntity);

		Optional<GroupEntity> groupEntityOptional = groupRepository.findById(messageGroupEntity.getGroupId());
		GroupEntity groupEntity = groupEntityOptional.get();
		groupEntity.setTimeLastMessage(messageGroupEntity.getCreatedAt());
		groupRepository.save(groupEntity);

		Optional<UserEntity> userEntityOptional = userRepository.findById(messageGroupEntity.getFromUserId());

		MessageGroupDto messageGroupDto = new MessageGroupDto(messageGroupEntity.getId(),
		                                                      messageGroupEntity.getFromUserId(),
		                                                      userEntityOptional.get().getName(),
		                                                      messageGroupEntity.getGroupId(),
		                                                      messageGroupEntity.getText(),
		                                                      messageGroupEntity.getCreatedAt());
		return messageGroupDto;
	}



	@Transactional
	public void deleteMessageFromGroupByCurrentUser(UUID currentUserId, UUID messageId) {
		Optional<MessageGroupEntity> messageGroupRepositoryById = messageGroupRepository.findById(messageId);
		if (messageGroupRepositoryById.get().getFromUserId().equals(currentUserId)) {
			messageGroupRepository.deleteById(messageId);
		}
		else {
			throw new GroupException("Вы не можете удалить данное сообщение");
		}
	}



	@Transactional
	public GroupDto updateGroup(GroupDto groupDto) {
		GroupEntity groupEntity = groupRepository.findById(groupDto.getId())
				.orElseThrow(()->new GroupException("Группа не найдена"));
		groupEntity.setName(groupDto.getName());
		groupEntity.setInfo(groupDto.getInfo());
		GroupEntity save = groupRepository.save(groupEntity);
		return GroupMapper.groupToDto(save);
	}



	@Transactional
	public void deleteGroup(UUID groupId) {
		messageGroupRepository.deleteAllByGroupId(groupId);
		userGroupRepository.deleteAllByGroupId(groupId);
		groupRepository.deleteById(groupId);
	}
}
