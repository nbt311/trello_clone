package com.example.trellobackend.repositories;

import com.example.trellobackend.models.board.Columns;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColumnsRepository extends JpaRepository<Columns,Long> {
}
