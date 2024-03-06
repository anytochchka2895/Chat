package org.example.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDto {

	private Long phone;
	private String password;


}
