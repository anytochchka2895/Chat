package org.example.dtos;

import java.time.ZonedDateTime;
import java.util.UUID;

public class MessageGroupDto {

	private UUID id;
	private UUID fromUserId;
	private String fromUserName;
	private UUID groupId;
	private String text;
	private ZonedDateTime createdAt;

	public MessageGroupDto() {
	}

	public MessageGroupDto(UUID id, UUID fromUserId, String fromUserName, UUID groupId, String text, ZonedDateTime createdAt) {
		this.id = id;
		this.fromUserId = fromUserId;
		this.fromUserName = fromUserName;
		this.groupId = groupId;
		this.text = text;
		this.createdAt = createdAt;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(UUID fromUserId) {
		this.fromUserId = fromUserId;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public UUID getGroupId() {
		return groupId;
	}

	public void setGroupId(UUID groupId) {
		this.groupId = groupId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public ZonedDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(ZonedDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
