package org.example.dtos;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddContactDto {

	private long phone;
	// Кому добавить контакт
	private UUID currentUserId;

}
