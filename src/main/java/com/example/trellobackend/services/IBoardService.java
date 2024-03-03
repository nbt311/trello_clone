package com.example.trellobackend.services;

import com.example.trellobackend.dto.BoardResponseDTO;
import com.example.trellobackend.models.board.Board;
import com.example.trellobackend.models.workspace.Workspace;
import com.example.trellobackend.payload.request.BoardRequest;

public interface IBoardService extends IGeneralService<Board> {
    Board createBoard(BoardRequest boardRequest);
    BoardResponseDTO getBoardById(Long boardId);
    BoardResponseDTO createNewBoard(BoardRequest boardRequest);
}
