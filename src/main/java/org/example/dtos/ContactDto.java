package org.example.dtos;

import java.time.ZonedDateTime;
import java.util.UUID;

public class ContactDto {

	private UUID id;
	private UUID friendId;
	private String friendName;
	private ZonedDateTime timeLastMessage;

	public ContactDto() {
	}

	public ContactDto(UUID id, UUID friendId, String friendName, ZonedDateTime timeLastMessage) {
		this.id = id;
		this.friendId = friendId;
		this.friendName = friendName;
		this.timeLastMessage = timeLastMessage;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getFriendId() {
		return friendId;
	}

	public void setFriendId(UUID friendId) {
		this.friendId = friendId;
	}

	public String getFriendName() {
		return friendName;
	}

	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}

	public ZonedDateTime getTimeLastMessage() {
		return timeLastMessage;
	}

	public void setTimeLastMessage(ZonedDateTime timeLastMessage) {
		this.timeLastMessage = timeLastMessage;
	}
}
