package org.example.dtos;

import java.time.ZonedDateTime;
import java.util.UUID;

public class TokenDto {

	private UUID userId;
	private UUID token;
	private ZonedDateTime createdAt;
	private ZonedDateTime expiredAt;

	public TokenDto() {
	}

	public TokenDto(UUID userId, UUID token, ZonedDateTime createdAt, ZonedDateTime expairedAt) {
		this.userId = userId;
		this.token = token;
		this.createdAt = createdAt;
		this.expiredAt = expairedAt;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public UUID getToken() {
		return token;
	}

	public void setToken(UUID token) {
		this.token = token;
	}

	public ZonedDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(ZonedDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public ZonedDateTime getExpiredAt() {
		return expiredAt;
	}

	public void setExpiredAt(ZonedDateTime expiredAt) {
		this.expiredAt = expiredAt;
	}
}
