package com.example.trellobackend.repositories;

import com.example.trellobackend.models.workspace.WorkspaceMembers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspaceMembersRepository extends JpaRepository<WorkspaceMembers,Long> {
}
