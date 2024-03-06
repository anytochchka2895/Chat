package org.example.dtos;

import java.util.UUID;

public class CreateGroupDto {
	private String name;
	private UUID currentUserId;

	public CreateGroupDto() {
	}

	public CreateGroupDto(String name, UUID currentUserId) {
		this.name = name;
		this.currentUserId = currentUserId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UUID getCurrentUserId() {
		return currentUserId;
	}

	public void setCurrentUserId(UUID currentUserId) {
		this.currentUserId = currentUserId;
	}
}
