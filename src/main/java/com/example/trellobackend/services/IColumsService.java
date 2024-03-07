package com.example.trellobackend.services;

import com.example.trellobackend.dto.BoardResponseDTO;
import com.example.trellobackend.models.board.Columns;
import com.example.trellobackend.payload.request.ColumnRequest;

public interface IColumsService extends IGeneralService<Columns> {
//    Columns createColumn(ColumnRequest columnRequest);
    BoardResponseDTO createNewColumn(ColumnRequest columnRequest);
}
