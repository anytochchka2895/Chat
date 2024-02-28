package org.example.repositories;

import org.example.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

	UserEntity findByPhone(long phone);

	UserEntity findByName(String name);




}
