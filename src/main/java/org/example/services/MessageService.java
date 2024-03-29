package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.dtos.MessageDto;
import org.example.entities.MessageEntity;
import org.example.mappers.MessageMapper;
import org.example.repositories.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class MessageService {
	private final MessageRepository messageRepository;
	private final ContactService contactService;


	public List<MessageDto> getMessages(UUID userId1, UUID userId2, int limit) {
		List<MessageEntity> messagesByUsers = messageRepository.findMessagesByUsers(userId1, userId2, limit);
		return MessageMapper.listMessagesToDtos(messagesByUsers);
	}

	@Transactional
	public MessageDto newMessage(MessageDto messageDto) {
		contactService.createContactForFriend(messageDto.getFromUserId(), messageDto.getToUserId());
		contactService.createContactForFriend(messageDto.getToUserId(), messageDto.getFromUserId());
		messageDto.setId(UUID.randomUUID());
		messageDto.setCreatedAt(ZonedDateTime.now());
		MessageEntity messageEntity = MessageMapper.messageToEntity(messageDto);
		MessageEntity save = messageRepository.save(messageEntity);
		return MessageMapper.messageToDto(save);
	}

	@Transactional
	public MessageDto deleteMessage(UUID id) {
		MessageEntity entityById = messageRepository.getById(id);
		messageRepository.deleteById(id);
		return MessageMapper.messageToDto(entityById);
	}
}
