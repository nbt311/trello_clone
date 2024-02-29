package com.example.trellobackend.services;

import com.example.trellobackend.models.board.Board;
import com.example.trellobackend.models.workspace.Workspace;

public interface IBoardService extends IGeneralService<Board> {
    Iterable<Board> getBoardByWorkspace_id(Long id);
//    Iterable<Board> getBoardByVisibility(Long id);
}
