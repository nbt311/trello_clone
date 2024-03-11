package com.example.trellobackend.dto;

import com.example.trellobackend.models.board.Card;
import com.example.trellobackend.models.board.Columns;
import lombok.Data;

import java.util.List;

@Data
public class UpdateBoardDTO {
    private List<Long> columnOrderIds;
}
