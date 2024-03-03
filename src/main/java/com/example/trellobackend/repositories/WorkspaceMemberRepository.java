package com.example.trellobackend.repositories;

import com.example.trellobackend.enums.UserRole;
import com.example.trellobackend.models.User;
import com.example.trellobackend.models.workspace.Workspace;
import com.example.trellobackend.models.workspace.Members;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import java.util.List;

public interface WorkspaceMemberRepository extends JpaRepository<Members, Long> {
    Iterable<Members> findMembersByWorkspace_Id(Long workspace_id);
    List<Members> findByWorkspace(Workspace workspace);

    boolean existsByUserAndWorkspaceAndRole(User user, Workspace workspace, UserRole userRole);
    Iterable<Members> findMembersByWorkspaceId(Long workspaceId);
}
