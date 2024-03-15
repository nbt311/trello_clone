package com.example.trellobackend.repositories;

import com.example.trellobackend.dto.ColumnsDTO;
import com.example.trellobackend.models.board.Columns;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ColumnsRepository extends JpaRepository<Columns,Long> {
    List<Columns> findByBoardId(Long boardId);

}
