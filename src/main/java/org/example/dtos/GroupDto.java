package org.example.dtos;

import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupDto {

	private UUID id;
	private String name;
	private String info;
	private ZonedDateTime createdAt;
	private ZonedDateTime timeLastMessage;

//	public GroupDto() {
//	}
//
//	public GroupDto(UUID id, String name, String info, ZonedDateTime createdAt, ZonedDateTime timeLastMessage) {
//		this.id = id;
//		this.name = name;
//		this.info = info;
//		this.createdAt = createdAt;
//		this.timeLastMessage = timeLastMessage;
//	}
//
//
//	public UUID getId() {
//		return id;
//	}
//
//	public void setId(UUID id) {
//		this.id = id;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public String getInfo() {
//		return info;
//	}
//
//	public void setInfo(String info) {
//		this.info = info;
//	}
//
//	public ZonedDateTime getCreatedAt() {
//		return createdAt;
//	}
//
//	public void setCreatedAt(ZonedDateTime createdAt) {
//		this.createdAt = createdAt;
//	}
//
//	public ZonedDateTime getTimeLastMessage() {
//		return timeLastMessage;
//	}
//
//	public void setTimeLastMessage(ZonedDateTime timeLastMessage) {
//		this.timeLastMessage = timeLastMessage;
//	}
}
