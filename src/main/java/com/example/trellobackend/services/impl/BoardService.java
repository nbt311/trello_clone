package com.example.trellobackend.services.impl;

import com.example.trellobackend.models.board.Board;
import com.example.trellobackend.models.workspace.Workspace;
import com.example.trellobackend.repositories.BoardRepository;
import com.example.trellobackend.services.IBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BoardService implements IBoardService {
    @Autowired
    private BoardRepository boardRepository;

    @Override
    public Iterable<Board> findAll() {
        return boardRepository.findAll();
    }

    @Override
    public Optional<Board> findById(Long id) {
        return boardRepository.findById(id);
    }

    @Override
    public void save(Board board) {
        boardRepository.save(board);
    }

    @Override
    public void remove(Long id) {
        boardRepository.deleteById(id);
    }

    @Override
    public Iterable<Board> getBoardByWorkspace_id(Long id) {
        return boardRepository.getBoardByWorkspace_id(id);
    }

//    @Override
//    public Iterable<Board> getBoardByVisibility(Long id) {
//        return boardRepository.getBoardByVisibility(id);
//    }
}
