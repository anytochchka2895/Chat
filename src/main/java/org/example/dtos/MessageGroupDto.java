package org.example.dtos;

import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageGroupDto {

	private UUID id;
	private UUID fromUserId;
	private String fromUserName;
	private UUID groupId;
	private String text;
	private ZonedDateTime createdAt;

}
