package org.example.dtos;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMessageGroupDto {
	private UUID fromUserId;
	private UUID groupId;
	private String text;

}
