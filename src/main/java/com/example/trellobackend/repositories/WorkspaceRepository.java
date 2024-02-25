package com.example.trellobackend.repositories;

import com.example.trellobackend.models.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {
}
