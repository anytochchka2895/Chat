package org.example.repositories;

import org.example.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroupEntity, UUID> {

	Optional<UserGroupEntity> findByGroupIdAndUserId(UUID groupId, UUID userId);

	void deleteByGroupIdAndUserId(UUID groupId, UUID userId);

	List<UserGroupEntity> findAllByGroupId(UUID groupId);

	void deleteAllByGroupId(UUID groupId);



}
