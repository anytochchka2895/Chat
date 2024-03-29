package org.example.services;

import org.example.dtos.*;
import org.example.entities.*;
import org.example.repositories.*;
import org.example.utils.PasswordUtils;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorizationServiceTest {
	@Mock UserRepository userRepository;
	@Mock TokenRepository tokenRepository;

	AuthorizationService authorizationService;

	EasyRandom generator = new EasyRandom();

	@BeforeEach
	void init() {
		authorizationService = new AuthorizationService(userRepository, tokenRepository);
	}


	@Test
	void authorizationUserNotFoundTest() {
		//GIVEN
		UserLoginDto userLoginDto = generator.nextObject(UserLoginDto.class);
		//MOCK
		when(userRepository.findByPhone(userLoginDto.getPhone())).thenReturn(null);
		//WHEN
		RuntimeException thrown = assertThrows(
				RuntimeException.class,
				() -> authorizationService.authorization(userLoginDto));

		//THEN
		assertTrue(thrown.getMessage().contains("Пользователь не найден"));

	}

	@Test
	void authorizationPasswordIncorrectTest() {
		//GIVEN
		UserLoginDto userLoginDto = generator.nextObject(UserLoginDto.class);
		UserEntity userEntity = generator.nextObject(UserEntity.class);
		//MOCK
		when(userRepository.findByPhone(userLoginDto.getPhone())).thenReturn(userEntity);
		//WHEN
		RuntimeException thrown = assertThrows(
				RuntimeException.class,
				() -> authorizationService.authorization(userLoginDto));

		//THEN
		assertTrue(thrown.getMessage().contains("Неверный пароль"));
	}


	@Test
	void authorizationEmptyTokenTest() {
		//GIVEN
		UserLoginDto userLoginDto = generator.nextObject(UserLoginDto.class);
		UserEntity userEntity = generator.nextObject(UserEntity.class);
		userEntity.setPhone(userLoginDto.getPhone());
		userEntity.setPassword(PasswordUtils.encodePassword(userLoginDto.getPassword()));

		//MOCK
		when(userRepository.findByPhone(userLoginDto.getPhone())).thenReturn(userEntity);
		when(tokenRepository.findById(userEntity.getId())).thenReturn(Optional.empty());

		//WHEN
		TokenDto resultAuthorization = authorizationService.authorization(userLoginDto);
		//THEN
		assertNotNull(resultAuthorization);
		assertEquals(userEntity.getId(), resultAuthorization.getUserId());
		verify(tokenRepository, times(1)).save(any());

	}


	@Test
	void authorizationExpiredTokenTest() {
		//GIVEN
		UserLoginDto userLoginDto = generator.nextObject(UserLoginDto.class);
		UserEntity userEntity = generator.nextObject(UserEntity.class);
		userEntity.setPhone(userLoginDto.getPhone());
		userEntity.setPassword(PasswordUtils.encodePassword(userLoginDto.getPassword()));
		TokenEntity tokenEntity = generator.nextObject(TokenEntity.class);
		tokenEntity.setUserId(userEntity.getId());
		tokenEntity.setExpiredAt(ZonedDateTime.now().minusMinutes(15));
		//MOCK
		when(userRepository.findByPhone(userLoginDto.getPhone())).thenReturn(userEntity);
		when(tokenRepository.findById(userEntity.getId())).thenReturn(Optional.of(tokenEntity));

		//WHEN
		TokenDto resultAuthorization = authorizationService.authorization(userLoginDto);
		//THEN
		assertNotNull(resultAuthorization);
		assertEquals(userEntity.getId(), resultAuthorization.getUserId());
		verify(tokenRepository, times(1)).save(any());
		assertEquals(tokenEntity.getUserId(),resultAuthorization.getUserId());
	}


	@Test
	void authorizationUpdateTokenTest() {
		//GIVEN
		UserLoginDto userLoginDto = generator.nextObject(UserLoginDto.class);
		UserEntity userEntity = generator.nextObject(UserEntity.class);
		userEntity.setPhone(userLoginDto.getPhone());
		userEntity.setPassword(PasswordUtils.encodePassword(userLoginDto.getPassword()));
		TokenEntity tokenEntity = generator.nextObject(TokenEntity.class);
		tokenEntity.setUserId(userEntity.getId());
		tokenEntity.setExpiredAt(ZonedDateTime.now().plusMinutes(15));
		//MOCK
		when(userRepository.findByPhone(userLoginDto.getPhone())).thenReturn(userEntity);
		when(tokenRepository.findById(userEntity.getId())).thenReturn(Optional.of(tokenEntity));

		//WHEN
		TokenDto resultAuthorization = authorizationService.authorization(userLoginDto);
		//THEN
		assertNotNull(resultAuthorization);
		assertEquals(userEntity.getId(), resultAuthorization.getUserId());
		verify(tokenRepository, times(1)).save(any());
		assertEquals(tokenEntity.getUserId(),resultAuthorization.getUserId());
		assertEquals(tokenEntity.getToken(),resultAuthorization.getToken());
	}



	@Test
	void errorValidateTokenTest() {
		//GIVEN
		UUID token = generator.nextObject(UUID.class);
		//MOCK
		when(tokenRepository.findByToken(token)).thenReturn(null);
		//WHEN
		RuntimeException thrown = assertThrows(
				RuntimeException.class,
				() -> authorizationService.validateToken(token));

		//THEN
		assertTrue(thrown.getMessage().contains("Ошибка авторизации. Токен не найден"));

	}


	@Test
	void expiredAtValidateTokenTest() {
		//GIVEN
		UUID token = generator.nextObject(UUID.class);
		TokenEntity tokenEntity = generator.nextObject(TokenEntity.class);
		tokenEntity.setExpiredAt(ZonedDateTime.now().minusMinutes(15));
		//MOCK
		when(tokenRepository.findByToken(token)).thenReturn(tokenEntity);
		//WHEN
		RuntimeException thrown = assertThrows(
				RuntimeException.class,
				() -> authorizationService.validateToken(token));

		//THEN
		assertTrue(thrown.getMessage().contains("Токен просрочен"));

	}
}