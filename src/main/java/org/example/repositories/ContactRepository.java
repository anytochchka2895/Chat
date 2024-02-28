package org.example.repositories;

import org.example.entities.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface ContactRepository extends JpaRepository<ContactEntity, UUID> {

	List<ContactEntity> findAllByCurrentUserIdOrderByTimeLastMessageDesc(UUID currentUserId);

	ContactEntity findByCurrentUserIdAndFriendId(UUID currentUserId, UUID friendId);


}
