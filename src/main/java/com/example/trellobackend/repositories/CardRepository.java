package com.example.trellobackend.repositories;

import com.example.trellobackend.models.board.card.Card;
import com.example.trellobackend.dto.CardDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CardRepository extends JpaRepository<Card,Long> {
    @Query("SELECT new com.example.trellobackend.dto.CardDTO(c.id, c.title, c.description) FROM Card c WHERE c.title LIKE %:query% OR c.description LIKE %:query%")
    List<CardDTO> findCardByPartialMatch(@Param("query") String query);
}
