package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.dtos.*;
import org.example.entities.UserEntity;
import org.example.exceptions.UserException;
import org.example.mappers.UserMapper;
import org.example.repositories.UserRepository;
import org.example.utils.PasswordUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.*;

@RequiredArgsConstructor

@Service
public class UserService {

	private final UserRepository userRepository;


	@Transactional
	public UserDto createUser(UserLoginDto newUser) {
		if (Objects.isNull(newUser.getPassword())) {
			throw new UserException("Пустой пароль");
		}
		if (Objects.isNull(newUser.getPhone())) {
			throw new UserException("Поле с телефоном не заполнено");
		}
		UserEntity userEntity = new UserEntity();
		userEntity.setId(UUID.randomUUID());
		userEntity.setPhone(newUser.getPhone());
		userEntity.setName(newUser.getPhone() + "");
		String encodePassword = PasswordUtils.encodePassword(newUser.getPassword());
		userEntity.setPassword(encodePassword);
		userEntity.setCreatedAt(ZonedDateTime.now());

		UserEntity savedEntity = userRepository.save(userEntity);

		return UserMapper.userToDto(savedEntity);
	}

	@Transactional
	public UserDto updateUser(UserDto userDto) {
		Optional<UserEntity> entityOptionalById = userRepository.findById(userDto.getId());
		if (entityOptionalById.isEmpty()) {
			throw new UserException("Пользователя с таким id не существует");
		}
		UserEntity entityUser = entityOptionalById.get();
		entityUser.setName(userDto.getName());
		entityUser.setStatus(userDto.getStatus());
		entityUser.setInfo(userDto.getInfo());
		UserEntity save = userRepository.save(entityUser);
		return UserMapper.userToDto(save);
	}

	public UserDto getUserById(UUID userId) {
		Optional<UserEntity> entityOptional = userRepository.findById(userId);
		if (entityOptional.isEmpty()) {
			throw new UserException("Пользователь не найден");
		}
		return UserMapper.userToDto(entityOptional.get());
	}


	public UserDto getByFilters(String name, Long phone) {
		if (Objects.isNull(name) && Objects.isNull(phone)) {
			throw new UserException("Нет данных для поиска");
		}
		if (Objects.nonNull(phone)) {
			UserEntity userEntity = userRepository.findByPhone(phone);
			return UserMapper.userToDto(userEntity);
		}
		else {
			UserEntity userEntity = userRepository.findByName(name);
			return UserMapper.userToDto(userEntity);
		}

	}


}
