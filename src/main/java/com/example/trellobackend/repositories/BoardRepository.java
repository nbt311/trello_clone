package com.example.trellobackend.repositories;

import com.example.trellobackend.models.board.Board;
import com.example.trellobackend.models.workspace.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Iterable<Board> getBoardByWorkspace_id(Long id);
    @Query (value = "select b.* " +
                    "from board b " +
                    "join trello.board_visibilities bv on b.id = bv.board_id " +
                    "join trello.visibility v on bv.visibility_id = v.id " +
                    "where v.id = :id", nativeQuery = true)
    Iterable<Board> getBoardByVisibility(Long id);
}
