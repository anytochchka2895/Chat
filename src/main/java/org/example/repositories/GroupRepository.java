package org.example.repositories;

import org.example.entities.GroupEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, UUID> {

	@Query (value = "select g from GroupEntity g " +
			" join UserGroupEntity u on g.id = u.groupId " +
			" where u.userId = :userId " +
			" order by g.timeLastMessage desc ")
	List<GroupEntity> findAllByUserIdOrderByTimeLastMessageDesc(UUID userId);


}
