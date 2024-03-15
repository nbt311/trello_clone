package com.example.trellobackend.repositories;

import com.example.trellobackend.models.board.card.Card;
import com.example.trellobackend.dto.CardDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CardRepository extends JpaRepository<Card,Long> {
    @Query("SELECT new com.example.trellobackend.dto.CardDTO(c) FROM Card c JOIN c.board b WHERE (c.title LIKE %:query% OR c.description LIKE %:query%) AND b.id = :boardId")
    List<CardDTO> findCardByPartialMatchAndBoard(@Param("query") String query, @Param("boardId") Long boardId);
}
