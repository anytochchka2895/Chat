package org.example.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "tokens")
public class TokenEntity {
	@Id
	@Column(name = "user_id")
	private UUID userId;

	@Column(name = "token")
	private UUID token;

	@Column(name = "created_at")
	private ZonedDateTime createdAt;

	@Column(name = "expired_at")
	private ZonedDateTime expiredAt;

}
