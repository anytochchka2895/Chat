package org.example.entities;

import jakarta.persistence.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "tokens")
public class TokenEntity {
	@Id
	@Column(name = "user_id")
	private UUID userId;

	@Column(name = "token")
	private UUID token;

	@Column(name = "created_at")
	private ZonedDateTime createdAt;

	@Column(name = "expired_at")
	private ZonedDateTime expiredAt;

	public TokenEntity() {
	}

	public TokenEntity(UUID userId, UUID token, ZonedDateTime createdAt, ZonedDateTime expiredAt) {
		this.userId = userId;
		this.token = token;
		this.createdAt = createdAt;
		this.expiredAt = expiredAt;
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
