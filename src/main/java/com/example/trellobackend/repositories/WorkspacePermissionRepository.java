package com.example.trellobackend.repositories;

import com.example.trellobackend.models.WorkspacePermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspacePermissionRepository extends JpaRepository<WorkspacePermission, Long> {
}
