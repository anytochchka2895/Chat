package org.example.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "contacts")
public class ContactEntity {
	@Id
	@Column(name = "id")
	private UUID id;

	@Column(name = "current_user_id")
	private UUID currentUserId;

	@Column(name = "friend_id")
	private UUID friendId;

	@Column(name = "time_last_message")
	private ZonedDateTime timeLastMessage;

}
