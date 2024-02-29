package com.example.trellobackend.repositories;

import com.example.trellobackend.models.board.Board;
import com.example.trellobackend.models.workspace.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Iterable<Board> getBoardByWorkspace_id(Long id);
//    @Query
//    Iterable<Board> getBoardByVisibility(Long id);
}
