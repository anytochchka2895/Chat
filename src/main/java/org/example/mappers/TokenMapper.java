package org.example.mappers;

import org.example.dtos.TokenDto;
import org.example.entities.TokenEntity;

public class TokenMapper {

	public static TokenDto tokenToDto(TokenEntity tokenEntity){
		return new TokenDto(tokenEntity.getUserId(),
		                    tokenEntity.getToken(),
		                    tokenEntity.getCreatedAt(),
		                    tokenEntity.getExpiredAt());
	}

}
