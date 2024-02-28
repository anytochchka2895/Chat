package org.example.entities;

import jakarta.persistence.*;

import java.time.ZonedDateTime;
import java.util.UUID;

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

	public MessageEntity() {
	}

	public MessageEntity(UUID id, String text, UUID fromUserId, UUID toUserId, ZonedDateTime createdAt) {
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
