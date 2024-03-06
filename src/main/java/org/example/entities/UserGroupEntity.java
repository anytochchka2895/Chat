package org.example.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "users_in_groups")
public class UserGroupEntity {

	@Id
	@Column(name = "id")
	private UUID id;

	@Column(name = "group_id")
	private UUID groupId;

	@Column(name = "user_id")
	private UUID userId;

	public UserGroupEntity() {
	}

	public UserGroupEntity(UUID id, UUID groupId, UUID userId) {
		this.id = id;
		this.groupId = groupId;
		this.userId = userId;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getGroupId() {
		return groupId;
	}

	public void setGroupId(UUID groupId) {
		this.groupId = groupId;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID fromUserId) {
		this.userId = fromUserId;
	}
}
