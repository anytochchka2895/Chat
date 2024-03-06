package org.example.dtos;

import java.util.UUID;

public class CreateMessageGroupDto {
	private UUID fromUserId;
	private UUID groupId;
	private String text;

	public CreateMessageGroupDto() {
	}

	public CreateMessageGroupDto(UUID fromUserId, UUID groupId, String text) {
		this.fromUserId = fromUserId;
		this.groupId = groupId;
		this.text = text;
	}

	public UUID getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(UUID fromUserId) {
		this.fromUserId = fromUserId;
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
}
