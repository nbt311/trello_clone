package com.example.trellobackend.repositories;

import com.example.trellobackend.models.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Long> {
}
