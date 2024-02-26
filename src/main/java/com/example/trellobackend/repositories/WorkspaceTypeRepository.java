package com.example.trellobackend.repositories;

import com.example.trellobackend.models.WorkspaceType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspaceTypeRepository extends JpaRepository<WorkspaceType, Long> {
}
