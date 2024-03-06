package org.example.dtos;

import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupDto {

	private UUID id;
	private String name;
	private String info;
	private ZonedDateTime createdAt;
	private ZonedDateTime timeLastMessage;
}