package com.example.trellobackend.repositories;

import com.example.trellobackend.models.workspace.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {
    Workspace findById(long id);
    @Query(value =  "select w2.* " +
                    "from workspace w2 " +
                    "join members m on w2.id = m.workspace_id " +
                    "join users u on m.user_id = u.id " +
                    "where u.id = :userId", nativeQuery = true)
    Iterable<Workspace> getWorkspaceByUserId(Long userId);
}
