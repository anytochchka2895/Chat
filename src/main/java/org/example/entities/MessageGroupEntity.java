package org.example.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "messages_in_groups")
public class MessageGroupEntity {

	@Id
	@Column(name = "id")
	private UUID id;

	@Column(name = "from_user_id")
	private UUID fromUserId;

	@Column(name = "group_id")
	private UUID groupId;

	@Column(name = "text")
	private String text;

	@Column(name = "created_at")
	private ZonedDateTime createdAt;


}
