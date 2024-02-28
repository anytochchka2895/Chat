package org.example.dtos;

import java.time.ZonedDateTime;
import java.util.UUID;

public class MessageDto {

	private UUID id;
	private String text;
	private UUID fromUserId;
	private UUID toUserId;
	private ZonedDateTime createdAt;

	public MessageDto() {
	}

	public MessageDto(UUID id, String text, UUID fromUserId, UUID toUserId, ZonedDateTime createdAt) {
		this.id = id;
		this.text = text;
		this.fromUserId = fromUserId;
		this.toUserId = toUserId;
		this.createdAt = createdAt;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public UUID getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(UUID fromUserId) {
		this.fromUserId = fromUserId;
	}

	public UUID getToUserId() {
		return toUserId;
	}

	public void setToUserId(UUID toUserId) {
		this.toUserId = toUserId;
	}

	public ZonedDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(ZonedDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
