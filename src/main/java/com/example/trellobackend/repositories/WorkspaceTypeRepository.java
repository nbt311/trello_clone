package com.example.trellobackend.repositories;

import com.example.trellobackend.models.workspace.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspaceTypeRepository extends JpaRepository<Type, Long> {
}
