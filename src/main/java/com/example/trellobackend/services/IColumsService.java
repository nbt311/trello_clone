package com.example.trellobackend.services;

import com.example.trellobackend.dto.*;
import com.example.trellobackend.models.board.Columns;
import com.example.trellobackend.payload.request.ColumnRequest;

import java.util.List;

public interface IColumsService extends IGeneralService<Columns> {
//    ColumnsDTO createColumn(ColumnRequest columnRequest);
    BoardResponseDTO createNewColumn(ColumnRequest columnRequest);
    List<ColumnsDTO> getAllColumns();
    List<CardDTO> getAllCardDTOByBoardId(Long columnId);
    ColumnsDTO updateColumnCardOrderIds(Long columnId, UpdateColumnDTO updateData);
    void handleDragAndDrop(DragAndDropDTO dragAndDropDTO);
}
