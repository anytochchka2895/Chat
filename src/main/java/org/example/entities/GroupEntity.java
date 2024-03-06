package org.example.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor

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


}
