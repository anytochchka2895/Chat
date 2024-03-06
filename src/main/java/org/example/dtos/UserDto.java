package org.example.dtos;

import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	private UUID id;
	private String name;
	private long phone;
	private String status;
	private String info;
	private ZonedDateTime createdAt;
	private Boolean online;


}
