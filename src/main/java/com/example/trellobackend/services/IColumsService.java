package com.example.trellobackend.services;

import com.example.trellobackend.dto.BoardResponseDTO;
import com.example.trellobackend.dto.CardDTO;
import com.example.trellobackend.dto.ColumnsDTO;
import com.example.trellobackend.models.board.Columns;
import com.example.trellobackend.payload.request.ColumnRequest;

import java.util.List;

public interface IColumsService extends IGeneralService<Columns> {
//    ColumnsDTO createColumn(ColumnRequest columnRequest);
    BoardResponseDTO createNewColumn(ColumnRequest columnRequest);
    List<ColumnsDTO> getAllColumns();
    List<CardDTO> getAllCardDTOByBoardId(Long columnId);
    void updateColumnsById(Long columnId, String title);
}
