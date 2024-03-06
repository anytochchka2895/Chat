package org.example.repositories;

import org.example.entities.MessageGroupEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface MessageGroupRepository extends JpaRepository<MessageGroupEntity, UUID> {

	@Query (nativeQuery = true,
	value = "select * from messages_in_groups m " +
			" where m.group_id = :groupId " +
			" order by m.created_at desc" +
			" limit :limit")
	List<MessageGroupEntity> findAllByGroupIdWithLimit(UUID groupId, int limit);

	void deleteAllByGroupId(UUID groupId);

}
