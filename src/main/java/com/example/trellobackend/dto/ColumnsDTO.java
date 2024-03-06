package com.example.trellobackend.dto;

import com.example.trellobackend.models.board.Board;
import com.example.trellobackend.models.board.Columns;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ColumnsDTO {
    private Long id;
    private String title;

    public ColumnsDTO(Columns columns) {
        this.id = columns.getId();
        this.title = columns.getTitle();
    }
}
