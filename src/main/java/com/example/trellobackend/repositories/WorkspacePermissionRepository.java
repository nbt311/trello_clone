package com.example.trellobackend.repositories;

import com.example.trellobackend.enums.UserRole;
import com.example.trellobackend.enums.WorkSpacePermission;
import com.example.trellobackend.models.Role;
import com.example.trellobackend.models.workspace.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkspacePermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByName(WorkSpacePermission name);
}
