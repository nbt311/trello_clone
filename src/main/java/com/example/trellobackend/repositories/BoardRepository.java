package com.example.trellobackend.repositories;

import com.example.trellobackend.models.board.Board;
import com.example.trellobackend.models.workspace.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Iterable<Board> findBoardByWorkspaceId(Long workspaceId);
    @Query (value = "select b.* " +
                    "from board b " +
                    "join trello.board_visibilities bv on b.id = bv.board_id " +
                    "join trello.visibility v on bv.visibility_id = v.id " +
                    "where v.id = :visibilityId", nativeQuery = true)
    Iterable<Board> findBoardByVisibility(Long visibilityId);
}
