package com.example.trellobackend.repositories;

import com.example.trellobackend.models.workspace.Members;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkspaceMemberRepository extends JpaRepository<Members, Long> {
    Iterable<Members> findMembersByWorkspaceId(Long workspaceId);
}
