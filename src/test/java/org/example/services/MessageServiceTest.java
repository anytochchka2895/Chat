package org.example.services;

import org.example.dtos.MessageDto;
import org.example.entities.MessageEntity;
import org.example.repositories.MessageRepository;
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
class MessageServiceTest {
	@Mock MessageRepository messageRepository;
	@Mock ContactService contactService;

	MessageService messageService;

	EasyRandom generator = new EasyRandom();

	@BeforeEach
	void init() {
		messageService = new MessageService(messageRepository, contactService);
	}

	@Test
	void getMessages() {
		//GIVEN
		UUID userId1 = generator.nextObject(UUID.class);
		UUID userId2 = generator.nextObject(UUID.class);
		int limit = 15;
		List<MessageEntity> messagesByUsers = generator.objects(MessageEntity.class, 15).toList();
		//MOCK
		when(messageRepository.findMessagesByUsers(userId1, userId2, limit)).thenReturn(messagesByUsers);
		//WHEN
		List<MessageDto> resultMessages = messageService.getMessages(userId1, userId2, limit);
		//THEN
		assertNotNull(resultMessages);
		assertEquals(15, messagesByUsers.size());
		verify(messageRepository, times(1)).findMessagesByUsers(any(), any(), anyInt());
	}


	@Test
	void getMessagesIsEmptyTest() {
		//GIVEN
		UUID userId1 = generator.nextObject(UUID.class);
		UUID userId2 = generator.nextObject(UUID.class);
		int limit = 15;
		List<MessageEntity> messagesByUsers = new ArrayList<>();
		//MOCK
		when(messageRepository.findMessagesByUsers(userId1, userId2, limit)).thenReturn(messagesByUsers);
		//WHEN
		List<MessageDto> resultMessages = messageService.getMessages(userId1, userId2, limit);
		//THEN
		assertNotNull(resultMessages);
		assertEquals(0, messagesByUsers.size());
		verify(messageRepository, times(1)).findMessagesByUsers(any(), any(), anyInt());
	}


	@Test
	void newMessageTest() {
		//GIVEN
		MessageDto messageDto = generator.nextObject(MessageDto.class);
		UUID userId1 = messageDto.getFromUserId();
		UUID userId2 = messageDto.getToUserId();
		messageDto.setId(null);
		messageDto.setCreatedAt(null);
		//MOCK
		when(messageRepository.save(any())).thenAnswer(arg -> arg.getArguments()[0]);
		//WHEN
		MessageDto resultMessage = messageService.newMessage(messageDto);
		//THEN
		verify(contactService, times(1)).createContactForFriend(userId1, userId2);
		verify(contactService, times(1)).createContactForFriend(userId2, userId1);
		verify(messageRepository, times(1)).save(any());
		assertNotNull(resultMessage);
		assertEquals(userId1, resultMessage.getFromUserId());
		assertEquals(userId2, resultMessage.getToUserId());
		assertNotNull(resultMessage.getId());
		assertNotNull(resultMessage.getCreatedAt());
	}


	@Test
	void deleteMessage() {
		//GIVEN
		UUID messageId = generator.nextObject(UUID.class);
		MessageEntity messageEntity = generator.nextObject(MessageEntity.class);
		messageEntity.setId(messageId);

		//MOCK
		when(messageRepository.getById(messageId)).thenReturn(messageEntity);

		//WHEN
		MessageDto resultMessageDto = messageService.deleteMessage(messageId);

		//THEN
		verify(messageRepository, times(1)).deleteById(messageId);
		verify(messageRepository, times(1)).getById(messageId);
		assertEquals(messageEntity.getId(), resultMessageDto.getId());
		assertEquals(messageEntity.getText(), resultMessageDto.getText());
		assertEquals(messageEntity.getFromUserId(), resultMessageDto.getFromUserId());
		assertEquals(messageEntity.getToUserId(), resultMessageDto.getToUserId());
	}
}