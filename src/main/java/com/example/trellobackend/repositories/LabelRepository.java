package com.example.trellobackend.repositories;

import com.example.trellobackend.models.board.card.Label;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelRepository extends JpaRepository<Label, Long> {
    Label findByColor(String color);
}
