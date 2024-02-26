package com.example.trellobackend.repositories;

import com.example.trellobackend.models.Workspace;
import com.example.trellobackend.models.WorkspaceMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkspaceMemberRepository extends JpaRepository<WorkspaceMember, Long> {
    List<WorkspaceMember> findByWorkspace(Workspace workspace);
}
