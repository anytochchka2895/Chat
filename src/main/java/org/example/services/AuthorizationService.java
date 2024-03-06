package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.dtos.*;
import org.example.entities.*;
import org.example.exceptions.*;
import org.example.mappers.TokenMapper;
import org.example.repositories.*;
import org.example.utils.PasswordUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.*;

@RequiredArgsConstructor

@Service
public class AuthorizationService {
	private final UserRepository userRepository;
	private final TokenRepository tokenRepository;


	private static final int TOKEN_EXPIRED_INTERVAL_MINUTES = 1080;

	@Transactional
	public TokenDto authorization (UserLoginDto userLoginDto) {
		UserEntity userEntity = userRepository.findByPhone(userLoginDto.getPhone());
		if (Objects.isNull(userEntity)) {
			throw new UserException("Пользователь не найден");
		}

		String encodePassword = PasswordUtils.encodePassword(userLoginDto.getPassword());
		if (!userEntity.getPassword().equals(encodePassword)) {
			throw new UserException("Неверный пароль");
		}


		Optional<TokenEntity> tokenEntityOptional = tokenRepository.findById(userEntity.getId());
		if(tokenEntityOptional.isEmpty()){
			return createToken(userEntity.getId());
		}

		TokenEntity tokenEntity = tokenEntityOptional.get();
		if (tokenEntity.getExpiredAt().isBefore(ZonedDateTime.now())){
			return createToken(userEntity.getId());
		}
		else {
			tokenEntity.setCreatedAt(ZonedDateTime.now());
			tokenEntity.setExpiredAt(ZonedDateTime.now().plusMinutes(TOKEN_EXPIRED_INTERVAL_MINUTES));
			TokenEntity saved = tokenRepository.save(tokenEntity);
			return TokenMapper.tokenToDto(saved);
		}
	}

	private TokenDto createToken(UUID userId) {
		TokenEntity newTokenEntity = new TokenEntity(userId,
		                                             UUID.randomUUID(),
		                                             ZonedDateTime.now(),
		                                             ZonedDateTime.now().plusMinutes(TOKEN_EXPIRED_INTERVAL_MINUTES));
		TokenEntity saved = tokenRepository.save(newTokenEntity);
		return TokenMapper.tokenToDto(saved);
	}

	public void validateToken(UUID token){
		TokenEntity tokenEntity = tokenRepository.findByToken(token);
		if (Objects.isNull(tokenEntity)) {
			throw new TokenException("Ошибка авторизации. Токен не найден");
		}
		if (tokenEntity.getExpiredAt().isBefore(ZonedDateTime.now())){
			throw new TokenException("Токен просрочен");
		}
	}

}
