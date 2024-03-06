package org.example.dtos;

import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {

	private UUID userId;
	private UUID token;
	private ZonedDateTime createdAt;
	private ZonedDateTime expiredAt;

}
