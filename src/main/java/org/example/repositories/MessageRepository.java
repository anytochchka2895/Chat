package org.example.repositories;

import org.example.entities.MessageEntity;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface MessageRepository extends JpaRepository<MessageEntity, UUID> {

	@Query(nativeQuery = true,
			value = "select * from messages m " +
					" where (m.from_user_id = :userId1 and m.to_user_id = :userId2) " +
					" or (m.from_user_id = :userId2 and m.to_user_id = :userId1) " +
					" order by m.created_at desc " +
					" limit :limit")
	List<MessageEntity> findMessagesByUsers(UUID userId1, UUID userId2, int limit);

	MessageEntity getById(UUID id);

	void deleteById(UUID id);




}
