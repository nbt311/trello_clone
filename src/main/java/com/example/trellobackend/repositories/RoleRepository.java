package com.example.trellobackend.repositories;

import com.example.trellobackend.enums.UserRole;
import com.example.trellobackend.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(UserRole name);
}
