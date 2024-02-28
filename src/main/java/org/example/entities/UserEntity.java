package org.example.entities;

import jakarta.persistence.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
public class UserEntity {

	@Id
	@Column(name = "id")
	private UUID id;

	@Column(name = "name")
	private String name;

	@Column(name = "phone")
	private long phone;

	@Column(name = "password")
	private String password;

	@Column(name = "status")
	private String status;

	@Column(name = "info")
	private String info;

	@Column(name = "created_at")
	private ZonedDateTime createdAt;

	public UserEntity() {
	}

	public UserEntity(UUID id, String name, long phone, String password, String status, String info, ZonedDateTime createdAt) {
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.password = password;
		this.status = status;
		this.info = info;
		this.createdAt = createdAt;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
}
