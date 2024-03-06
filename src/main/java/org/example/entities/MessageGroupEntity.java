package org.example.entities;

import jakarta.persistence.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "messages_in_groups")
public class MessageGroupEntity {

	@Id
	@Column(name = "id")
	private UUID id;

	@Column(name = "from_user_id")
	private UUID fromUserId;

	@Column(name = "group_id")
	private UUID groupId;

	@Column(name = "text")
	private String text;

	@Column(name = "created_at")
	private ZonedDateTime createdAt;

	public MessageGroupEntity() {
	}

	public MessageGroupEntity(UUID id, UUID fromUserId, UUID groupId, String text, ZonedDateTime createdAt) {
		this.id = id;
		this.fromUserId = fromUserId;
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
