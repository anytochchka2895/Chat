package org.example.entities;

import jakarta.persistence.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "groups")
public class GroupEntity {

	@Id
	@Column(name = "id")
	private UUID id;

	@Column(name = "name")
	private String name;

	@Column(name = "info")
	private String info;

	@Column(name = "created_at")
	private ZonedDateTime createdAt;

	@Column(name = "time_last_message")
	private ZonedDateTime timeLastMessage;

	public GroupEntity() {
	}

	public GroupEntity(UUID id, String name, String info, ZonedDateTime createdAt, ZonedDateTime timeLastMessage) {
		this.id = id;
		this.name = name;
		this.info = info;
		this.createdAt = createdAt;
		this.timeLastMessage = timeLastMessage;
	}


	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public ZonedDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(ZonedDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public ZonedDateTime getTimeLastMessage() {
		return timeLastMessage;
	}

	public void setTimeLastMessage(ZonedDateTime timeLastMessage) {
		this.timeLastMessage = timeLastMessage;
	}
}
