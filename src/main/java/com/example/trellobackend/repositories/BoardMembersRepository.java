package com.example.trellobackend.repositories;

import com.example.trellobackend.models.board.BoardMembers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardMembersRepository extends JpaRepository<BoardMembers,Long> {
}
