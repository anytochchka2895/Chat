package org.example.dtos;

import java.util.UUID;

public class AddContactDto {

	private long phone;
	// Кому добавить контакт
	private UUID currentUserId;

	public AddContactDto() {
	}

	public AddContactDto(long phone, UUID currentUserId) {
		this.phone = phone;
		this.currentUserId = currentUserId;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public UUID getCurrentUserId() {
		return currentUserId;
	}

	public void setCurrentUserId(UUID currentUserId) {
		this.currentUserId = currentUserId;
	}
}
