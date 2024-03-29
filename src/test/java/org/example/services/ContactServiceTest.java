package org.example.services;

import org.example.dtos.*;
import org.example.entities.*;
import org.example.repositories.*;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactServiceTest {

	@Mock ContactRepository contactRepository;
	@Mock UserRepository userRepository;

	ContactService contactService;

	EasyRandom generator = new EasyRandom();

	@BeforeEach
	void init() {
		contactService = new ContactService(contactRepository, userRepository);
	}


	@Test
	void newContactUserWithThisPhoneNotFoundTest() {
		//GIVEN
		AddContactDto addContactDto = generator.nextObject(AddContactDto.class);
		//MOCK
		when(userRepository.findByPhone(addContactDto.getPhone())).thenReturn(null);
		//WHEN
		RuntimeException thrown = assertThrows(
				RuntimeException.class,
				() -> contactService.newContact(addContactDto));

		//THEN
		assertTrue(thrown.getMessage().contains("Пользователь с таким номером телефона не найден"));
	}


	@Test
	void newContactTest() {
		//GIVEN
		AddContactDto addContactDto = generator.nextObject(AddContactDto.class);
		UserEntity userEntityByPhone = generator.nextObject(UserEntity.class);
		userEntityByPhone.setPhone(addContactDto.getPhone());
		//MOCK
		when(userRepository.findByPhone(addContactDto.getPhone())).thenReturn(userEntityByPhone);
		//WHEN
		ContactDto resultContactDto = contactService.newContact(addContactDto);
		//THEN
		verify(contactRepository, times(1)).save(any());
		assertNotNull(resultContactDto);
		assertEquals(userEntityByPhone.getId(), resultContactDto.getFriendId());
		assertEquals(userEntityByPhone.getName(), resultContactDto.getFriendName());
	}


	@Test
	void createContactForFriendNoContactTest() {
		//GIVEN
		UUID currentUserId = generator.nextObject(UUID.class);
		UUID friendId = generator.nextObject(UUID.class);
		//MOCK
		when(contactRepository.findByCurrentUserIdAndFriendId(friendId, currentUserId)).thenReturn(null);
		//WHEN
		contactService.createContactForFriend(currentUserId, friendId);
		//THEN
		verify(contactRepository, times(1)).save(any());
	}


	@Test
	void createContactForFriendUpdateTest() {
		//GIVEN
		UUID currentUserId = generator.nextObject(UUID.class);
		UUID friendId = generator.nextObject(UUID.class);
		ContactEntity contactFriend = generator.nextObject(ContactEntity.class);
		contactFriend.setCurrentUserId(friendId);
		contactFriend.setFriendId(currentUserId);
		//MOCK
		when(contactRepository.findByCurrentUserIdAndFriendId(friendId, currentUserId)).thenReturn(contactFriend);
		//WHEN
		contactService.createContactForFriend(currentUserId, friendId);
		//THEN
		verify(contactRepository, times(1)).save(contactFriend);
	}


	@Test
	void deleteContactByIdTest() {
		//GIVEN
		UUID id = generator.nextObject(UUID.class);
		//MOCK

		//WHEN
		contactService.deleteContactById(id);
		//THEN
		verify(contactRepository, times(1)).deleteById(id);
	}


	@Test
	void showAllTest() {
		//GIVEN
		UUID userId = generator.nextObject(UUID.class);
		List<ContactEntity> contactsEntities = generator.objects(ContactEntity.class, 19).toList();
		List<UserEntity> friendsUsersEntities = generator.objects(UserEntity.class, 19).toList();
		//MOCK
		when(contactRepository.findAllByCurrentUserIdOrderByTimeLastMessageDesc(userId)).thenReturn(contactsEntities);
		when(userRepository.findAllById(any())).thenReturn(friendsUsersEntities);
		//WHEN
		List<ContactDto> resultContactDtos = contactService.showAll(userId);
		//THEN
		assertEquals(contactsEntities.size(), resultContactDtos.size());
	}


	@Test
	void showAllSingleTest() {
		//GIVEN
		UUID userId = generator.nextObject(UUID.class);
		ContactEntity contactEntity = generator.nextObject(ContactEntity.class);
		UserEntity friendUserEntity = generator.nextObject(UserEntity.class);
		contactEntity.setId(userId);
		contactEntity.setFriendId(friendUserEntity.getId());
		//MOCK
		when(contactRepository.findAllByCurrentUserIdOrderByTimeLastMessageDesc(userId))
				.thenReturn(List.of(contactEntity));
		when(userRepository.findAllById(any())).thenReturn(List.of(friendUserEntity));
		//WHEN
		List<ContactDto> resultContactDtos = contactService.showAll(userId);
		//THEN
		assertEquals(contactEntity.getId(), resultContactDtos.get(0).getId());
		assertEquals(contactEntity.getFriendId(), resultContactDtos.get(0).getFriendId());
		assertEquals(friendUserEntity.getName(), resultContactDtos.get(0).getFriendName());
	}
}