package org.example.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor

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


}
