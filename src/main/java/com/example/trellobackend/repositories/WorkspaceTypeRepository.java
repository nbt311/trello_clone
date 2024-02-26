package com.example.trellobackend.repositories;

import com.example.trellobackend.enums.WorkSpacePermission;
import com.example.trellobackend.enums.WorkSpaceType;
import com.example.trellobackend.models.workspace.Permission;
import com.example.trellobackend.models.workspace.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkspaceTypeRepository extends JpaRepository<Type, Long> {
    Optional<Type> findByName(WorkSpaceType name);
}
