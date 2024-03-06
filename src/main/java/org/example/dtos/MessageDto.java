package org.example.dtos;

import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {

	private UUID id;
	private String text;
	private UUID fromUserId;
	private UUID toUserId;
	private ZonedDateTime createdAt;

}
