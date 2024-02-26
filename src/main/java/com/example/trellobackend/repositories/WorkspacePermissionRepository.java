package com.example.trellobackend.repositories;

import com.example.trellobackend.models.workspace.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspacePermissionRepository extends JpaRepository<Permission, Long> {
}
