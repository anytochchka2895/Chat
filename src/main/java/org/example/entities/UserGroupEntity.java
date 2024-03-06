package org.example.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor

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

}
