package org.example.mappers;

import org.example.dtos.UserDto;
import org.example.entities.UserEntity;

public class UserMapper {

	public static UserDto userToDto(UserEntity userEntity) {
		UserDto dto = new UserDto();
		dto.setId(userEntity.getId());
		dto.setName(userEntity.getName());
		dto.setPhone(userEntity.getPhone());
		dto.setStatus(userEntity.getStatus());
		dto.setInfo(userEntity.getInfo());
		dto.setCreatedAt(userEntity.getCreatedAt());
		return dto;
	}

	public static UserEntity userToEntity(UserDto userDto) {
		UserEntity entity = new UserEntity();
		entity.setId(userDto.getId());
		entity.setName(userDto.getName());
		entity.setPhone(userDto.getPhone());
		entity.setStatus(userDto.getStatus());
		entity.setInfo(userDto.getInfo());
		return entity;
	}


}
