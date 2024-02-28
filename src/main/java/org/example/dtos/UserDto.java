package org.example.dtos;

import java.time.ZonedDateTime;
import java.util.UUID;

public class UserDto {

	private UUID id;
	private String name;
	private long phone;
	private String status;
	private String info;
	private ZonedDateTime createdAt;
	private Boolean online;

	public UserDto() {
	}

	public UserDto(UUID id, String name, long phone, String status, String info, ZonedDateTime createdAt, Boolean online) {
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.status = status;
		this.info = info;
		this.createdAt = createdAt;
		this.online = online;
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

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Boolean getOnline() {
		return online;
	}

	public void setOnline(Boolean online) {
		this.online = online;
	}
}
