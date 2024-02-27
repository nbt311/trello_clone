package com.example.trellobackend.repositories;

import com.example.trellobackend.models.workspace.Members;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspaceMemberRepository extends JpaRepository<Members, Long> {
    Iterable<Members> findMembersByWorkspace_Id(Long workspace_id);
}
