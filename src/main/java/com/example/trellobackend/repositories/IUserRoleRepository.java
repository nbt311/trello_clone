package com.example.trellobackend.repositories;

import com.example.trellobackend.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRoleRepository extends JpaRepository<UserRole,Long> {
    Optional<UserRole> findByName(String name);
}
