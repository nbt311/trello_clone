package com.example.trellobackend.repositories;

import com.example.trellobackend.models.board.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card,Long> {
}
