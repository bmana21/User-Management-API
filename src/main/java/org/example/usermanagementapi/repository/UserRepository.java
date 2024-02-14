package org.example.usermanagementapi.repository;

import org.example.usermanagementapi.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByPasswordHash(String passwordHash);

    User findByUserId(long id);

}