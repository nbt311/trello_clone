package com.example.trellobackend.repositories;

import com.example.trellobackend.models.board.card.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
