package com.example.trellobackend.repositories;

import com.example.trellobackend.enums.EBoardVisibility;
import com.example.trellobackend.models.board.Visibility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VisibilityRepository extends JpaRepository<Visibility,Long> {
    Optional<Visibility> findByName(EBoardVisibility name);
}
