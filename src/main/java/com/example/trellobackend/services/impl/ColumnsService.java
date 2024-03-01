package com.example.trellobackend.services.impl;

import com.example.trellobackend.models.User;
import com.example.trellobackend.models.board.Board;
import com.example.trellobackend.models.board.Columns;
import com.example.trellobackend.payload.request.ColumnRequest;
import com.example.trellobackend.repositories.BoardMembersRepository;
import com.example.trellobackend.repositories.BoardRepository;
import com.example.trellobackend.repositories.ColumnsRepository;
import com.example.trellobackend.repositories.UserRepository;
import com.example.trellobackend.services.IColumsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;



@Service
public class ColumnsService implements IColumsService {
    @Autowired
    private ColumnsRepository columnsRepository;
    @Autowired
    private BoardRepository boardRepository;

    @Override
    public Iterable<Columns> findAll() {
        return null;
    }

    @Override
    public Optional<Columns> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void save(Columns columns) {

    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public Columns createColumn(ColumnRequest columnRequest) {
      Optional<Board> boardOptional = boardRepository.findById(columnRequest.getBoardId());
        if (boardOptional.isPresent()) {
            Board board = boardOptional.get();
            Columns newColumns = new Columns();
            newColumns.setTitle(columnRequest.getTitle());
            newColumns.setBoard(board);
            columnsRepository.save(newColumns);
            return newColumns;
        } else {
            throw new RuntimeException("Error: Board not found.");
        }
    }
}
