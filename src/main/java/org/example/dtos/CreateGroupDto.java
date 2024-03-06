package org.example.dtos;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateGroupDto {
	private String name;
	private UUID currentUserId;

}
