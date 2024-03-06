package org.example.dtos;

import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactDto {

	private UUID id;
	private UUID friendId;
	private String friendName;
	private ZonedDateTime timeLastMessage;

}
