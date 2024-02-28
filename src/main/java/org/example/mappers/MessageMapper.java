package org.example.mappers;

import org.example.dtos.MessageDto;
import org.example.entities.MessageEntity;

import java.util.*;

public class MessageMapper {
	public static MessageDto messageToDto(MessageEntity messageEntity) {
		return new MessageDto(messageEntity.getId(),
		                      messageEntity.getText(),
		                      messageEntity.getFromUserId(),
		                      messageEntity.getToUserId(),
		                      messageEntity.getCreatedAt());
	}

	public static MessageEntity messageToEntity(MessageDto messageDto) {
		return new MessageEntity(messageDto.getId(),
		                         messageDto.getText(),
		                         messageDto.getFromUserId(),
		                         messageDto.getToUserId(),
		                         messageDto.getCreatedAt());
	}

	public static List<MessageDto> listMessagesToDtos(List<MessageEntity> messageEntities) {
		List<MessageDto> listMessagesDtos = new ArrayList<>();
		for (MessageEntity entity : messageEntities){
			listMessagesDtos.add(messageToDto(entity));
		}
		return listMessagesDtos;
	}
}
