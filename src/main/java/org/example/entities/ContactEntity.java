package org.example.entities;

import jakarta.persistence.*;

import java.time.ZonedDateTime;
import java.util.UUID;

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

	public ContactEntity() {
	}

	public ContactEntity(UUID id, UUID currentUserId, UUID friendId, ZonedDateTime timeLastMessage) {
		this.id = id;
		this.currentUserId = currentUserId;
		this.friendId = friendId;
		this.timeLastMessage = timeLastMessage;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getCurrentUserId() {
		return currentUserId;
	}

	public void setCurrentUserId(UUID userId) {
		this.currentUserId = userId;
	}

	public UUID getFriendId() {
		return friendId;
	}

	public void setFriendId(UUID friendId) {
		this.friendId = friendId;
	}

	public ZonedDateTime getTimeLastMessage() {
		return timeLastMessage;
	}

	public void setTimeLastMessage(ZonedDateTime timeLastMessage) {
		this.timeLastMessage = timeLastMessage;
	}
}
