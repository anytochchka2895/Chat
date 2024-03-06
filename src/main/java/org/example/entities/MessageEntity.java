package org.example.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "messages")
public class MessageEntity {
	@Id
	@Column(name = "id")
	private UUID id;

	@Column(name = "text")
	private String text;

	@Column(name = "from_user_id")
	private UUID fromUserId;

	@Column(name = "to_user_id")
	private UUID toUserId;

	@Column(name = "created_at")
	private ZonedDateTime createdAt;


}
