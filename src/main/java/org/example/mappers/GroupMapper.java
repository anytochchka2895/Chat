package org.example.mappers;

import org.example.dtos.*;
import org.example.entities.GroupEntity;

import java.util.*;

public class GroupMapper {

	public static GroupDto groupToDto(GroupEntity groupEntity){
		return new GroupDto(groupEntity.getId(),
		                    groupEntity.getName(),
		                    groupEntity.getInfo(),
		                    groupEntity.getCreatedAt(),
		                    groupEntity.getTimeLastMessage());
	}

	public static GroupEntity groupToEntity(GroupDto groupDto){
		return new GroupEntity(groupDto.getId(),
		                       groupDto.getName(),
		                       groupDto.getInfo(),
		                       groupDto.getCreatedAt(),
		                       groupDto.getTimeLastMessage());
	}

	public static List<GroupDto> groupToListDto(List<GroupEntity> groupEntityList){
		List<GroupDto> groupDtoList = new ArrayList<>();
		for (GroupEntity groupEntity : groupEntityList) {
			groupDtoList.add(groupToDto(groupEntity));
		}
		return groupDtoList;
	}





}
