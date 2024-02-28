package org.example.services;

import org.example.dtos.*;
import org.example.entities.*;
import org.example.exceptions.ContactException;
import org.example.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.*;

@Service
public class ContactService {

	private final ContactRepository contactRepository;
	private final UserRepository userRepository;

	public ContactService(ContactRepository contactRepository, UserRepository userRepository) {
		this.contactRepository = contactRepository;
		this.userRepository = userRepository;
	}


	@Transactional
	public ContactDto newContact(AddContactDto addContactDto) {
		UserEntity userEntityByPhone = userRepository.findByPhone(addContactDto.getPhone());
		if (Objects.isNull(userEntityByPhone)) {
			throw new ContactException("Пользователь с таким номером телефона не найден");
		}
		ContactEntity contactEntity = new ContactEntity();
		contactEntity.setId(UUID.randomUUID());
		contactEntity.setCurrentUserId(addContactDto.getCurrentUserId());
		contactEntity.setFriendId(userEntityByPhone.getId());
		contactEntity.setTimeLastMessage(ZonedDateTime.now());

		contactRepository.save(contactEntity);

		ContactDto contactDto = new ContactDto();
		contactDto.setId(contactEntity.getId());
		contactDto.setFriendId(contactEntity.getFriendId());
		String userName = userEntityByPhone.getName();
		if (Objects.isNull(userName)) {
			contactDto.setFriendName(userEntityByPhone.getPhone() + "");
		}
		else {
			contactDto.setFriendName(userName);
		}
		contactDto.setTimeLastMessage(contactEntity.getTimeLastMessage());

		return contactDto;
	}

	@Transactional
	public void createContactForFriend(UUID currentUserId, UUID friendId) {
		ContactEntity contactFriend = contactRepository.findByCurrentUserIdAndFriendId(friendId, currentUserId);
		if (Objects.isNull(contactFriend)) {
			ContactEntity contactEntity = new ContactEntity();
			contactEntity.setId(UUID.randomUUID());
			contactEntity.setTimeLastMessage(ZonedDateTime.now());
			contactEntity.setCurrentUserId(friendId);
			contactEntity.setFriendId(currentUserId);
			contactRepository.save(contactEntity);
		}
		else {
			contactFriend.setTimeLastMessage(ZonedDateTime.now());
			contactRepository.save(contactFriend);
		}
	}


	@Transactional
	public void deleteContactById(UUID id) {
		contactRepository.deleteById(id);
	}


	public List<ContactDto> showAll(UUID userId) {
		List<ContactEntity> contactsEntitys = contactRepository.findAllByCurrentUserIdOrderByTimeLastMessageDesc(userId);
		List<UUID> friendsIds = new ArrayList<>();
		for (ContactEntity contactEntity : contactsEntitys) {
			friendsIds.add(contactEntity.getFriendId());
		}
		List<UserEntity> friendsUsersEntities = userRepository.findAllById(friendsIds);

		Map<UUID, String> friendsIdsNames = new HashMap<>();

		for (UserEntity userEntity : friendsUsersEntities) {
			friendsIdsNames.put(userEntity.getId(), userEntity.getName());
		}

		List<ContactDto> contactDtos = new ArrayList<>();
		for (ContactEntity contactEntity : contactsEntitys) {
			ContactDto contactDto = new ContactDto();
			contactDto.setId(contactEntity.getId());
			contactDto.setFriendId(contactEntity.getFriendId());
			String friendName = friendsIdsNames.get(contactEntity.getFriendId());
			contactDto.setFriendName(friendName);
			contactDto.setTimeLastMessage(contactEntity.getTimeLastMessage());
			contactDtos.add(contactDto);
		}

		return contactDtos;
	}
}
