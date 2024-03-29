package org.example.services;

import org.example.dtos.*;
import org.example.entities.UserEntity;
import org.example.repositories.UserRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	@Mock
	UserRepository userRepository;

	UserService userService;

	EasyRandom generator = new EasyRandom();

	@BeforeEach
	void init() {
		userService = new UserService(userRepository);
	}

	//GIVEN

	//MOCK

	//WHEN

	//THEN


	@Test
	void createUserTest() {
		//GIVEN
		UserLoginDto userLoginDto = generator.nextObject(UserLoginDto.class);

		//MOCK
		when(userRepository.save(any())).thenAnswer(arg -> arg.getArguments()[0]);

		//WHEN
		UserDto resultUserDto = userService.createUser(userLoginDto);

		//THEN
		assertEquals(userLoginDto.getPhone(), resultUserDto.getPhone());
		assertEquals(userLoginDto.getPhone().toString(), resultUserDto.getName());
//		verify(userRepository, times(1)).save(any());
		verify(userRepository, times(1)).save(argThat(userEntity -> {
			assertNotNull(userEntity.getId());
			assertNotNull(userEntity.getPassword());
			return true;
		}));
	}

	@Test
	void createUserWithNullPasswordTest() {
		//GIVEN
		Long phone = generator.nextObject(Long.class);
		UserLoginDto userLoginDto = new UserLoginDto(phone, null);

		//MOCK

		//WHEN
		RuntimeException thrown = assertThrows(
				RuntimeException.class,
				() -> userService.createUser(userLoginDto));

		//THEN
		assertTrue(thrown.getMessage().contains("Пустой пароль"));
	}

	@Test
	void createUserWithNullPhoneTest() {
		//GIVEN
		String password = generator.nextObject(String.class);
		UserLoginDto userLoginDto = new UserLoginDto(null, password);

		//MOCK

		//WHEN
		RuntimeException thrown = assertThrows(
				RuntimeException.class,
				() -> userService.createUser(userLoginDto)
		);

		//THEN
		assertTrue(thrown.getMessage().contains("Поле с телефоном не заполнено"));
	}


	@Test
	void updateUserTest() {
		//GIVEN
		UserDto userDto = generator.nextObject(UserDto.class);
		UserEntity userEntity = generator.nextObject(UserEntity.class);
		userEntity.setId(userDto.getId());

		//MOCK
		when(userRepository.findById(userDto.getId())).thenReturn(Optional.of(userEntity));
		when(userRepository.save(any())).thenAnswer(arg -> arg.getArguments()[0]);

		//WHEN
		UserDto resultUserDto = userService.updateUser(userDto);

		//THEN
		assertNotNull(resultUserDto);
		assertEquals(userDto.getName(), resultUserDto.getName());
		assertEquals(userDto.getStatus(), resultUserDto.getStatus());
		assertEquals(userDto.getInfo(), resultUserDto.getInfo());
		assertEquals(userEntity.getCreatedAt(), resultUserDto.getCreatedAt());
		verify(userRepository, times(1)).findById(any());
		verify(userRepository, times(1)).save(any());
	}


	@Test
	void updateUserUserIdNotFoundTest() {
		//GIVEN
		UserDto userDto = generator.nextObject(UserDto.class);

		//MOCK
		when(userRepository.findById(userDto.getId())).thenReturn(Optional.empty());

		//WHEN
		RuntimeException thrown = assertThrows(
				RuntimeException.class,
				() -> userService.updateUser(userDto)
		);

		//THEN
		assertTrue(thrown.getMessage().contains("Пользователя с таким id не существует"));
	}


	@Test
	void getUserByIdTest() {
		//GIVEN
		UUID userId = generator.nextObject(UUID.class);
		UserEntity userEntity = generator.nextObject(UserEntity.class);
		userEntity.setId(userId);

		//MOCK
		when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

		//WHEN
		UserDto resultUser = userService.getUserById(userId);

		//THEN
		assertNotNull(resultUser);
		verify(userRepository, times(1)).findById(any());
		assertEquals(userId, resultUser.getId());
		assertEquals(userEntity.getCreatedAt(), resultUser.getCreatedAt());
		assertEquals(userEntity.getName(), resultUser.getName());
		assertEquals(userEntity.getPhone(), resultUser.getPhone());
	}


	@Test
	void getUserByIdUserNotFoundTest() {
		//GIVEN
		UUID userId = generator.nextObject(UUID.class);

		//MOCK
		when(userRepository.findById(userId)).thenReturn(Optional.empty());
		//WHEN
		RuntimeException thrown = assertThrows(
				RuntimeException.class,
				() -> userService.getUserById(userId));

		//THEN
		assertTrue(thrown.getMessage().contains("Пользователь не найден"));
	}


	@Test
	void getByFiltersAllFiltersTest() {
		//GIVEN
		Long phone = generator.nextObject(Long.class);
		String name = generator.nextObject(String.class);
		UserEntity userEntity = generator.nextObject(UserEntity.class);
		userEntity.setPhone(phone);
		//MOCK
		when(userRepository.findByPhone(phone)).thenReturn(userEntity);
		//WHEN
		UserDto resultUser = userService.getByFilters(name, phone);
		//THEN
		assertNotNull(resultUser);
		assertEquals(phone, resultUser.getPhone());
		assertEquals(userEntity.getId(), resultUser.getId());
		assertEquals(userEntity.getCreatedAt(), resultUser.getCreatedAt());
		verify(userRepository, times(1)).findByPhone(phone);
	}


	@Test
	void getByFiltersOnlyPhoneTest() {
		//GIVEN
		Long phone = generator.nextObject(Long.class);
		UserEntity userEntity = generator.nextObject(UserEntity.class);
		userEntity.setPhone(phone);
		//MOCK
		when(userRepository.findByPhone(phone)).thenReturn(userEntity);
		//WHEN
		UserDto resultUser = userService.getByFilters(null, phone);
		//THEN
		assertNotNull(resultUser);
		assertEquals(phone, resultUser.getPhone());
		assertEquals(userEntity.getName(), resultUser.getName());
		assertEquals(userEntity.getId(), resultUser.getId());
		assertEquals(userEntity.getCreatedAt(), resultUser.getCreatedAt());
		verify(userRepository, times(1)).findByPhone(phone);
	}


	@Test
	void getByFiltersOnlyNameTest() {
		//GIVEN
		String name = generator.nextObject(String.class);
		UserEntity userEntity = generator.nextObject(UserEntity.class);
		userEntity.setName(name);
		//MOCK
		when(userRepository.findByName(name)).thenReturn(userEntity);
		//WHEN
		UserDto resultUser = userService.getByFilters(name, null);
		//THEN
		assertNotNull(resultUser);
		assertEquals(userEntity.getPhone(), resultUser.getPhone());
		assertEquals(name, resultUser.getName());
		assertEquals(userEntity.getId(), resultUser.getId());
		assertEquals(userEntity.getCreatedAt(), resultUser.getCreatedAt());
		verify(userRepository, times(1)).findByName(any());
	}


	@Test
	void getByFiltersEmptyTest() {
		//GIVEN

		//MOCK

		//WHEN
		RuntimeException thrown = assertThrows(
				RuntimeException.class,
				() -> userService.getByFilters(null, null));

		//THEN
		assertTrue(thrown.getMessage().contains("Нет данных для поиска"));
	}
}