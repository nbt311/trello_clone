package com.example.trellobackend.controllers;

import com.example.trellobackend.models.board.Board;
import com.example.trellobackend.models.board.Columns;
import com.example.trellobackend.models.workspace.Workspace;
import com.example.trellobackend.payload.request.ColumnRequest;
import com.example.trellobackend.payload.response.MessageResponse;
import com.example.trellobackend.repositories.BoardRepository;
import com.example.trellobackend.repositories.ColumnsRepository;
import com.example.trellobackend.services.impl.BoardService;
import com.example.trellobackend.services.impl.ColumnsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/columns")
public class ColumsController {
    @Autowired
    private ColumnsService columnsService;
    @Autowired
    private ColumnsRepository columnsRepository;
    @PostMapping("/create")
    public ResponseEntity<?> createColumns(@RequestBody ColumnRequest columnRequest){
            Columns newColumns = columnsService.createColumn(columnRequest);
            return new ResponseEntity<>(newColumns, HttpStatus.CREATED);
    }
    @GetMapping("{id}")
    public ResponseEntity<?> getColumsById(@PathVariable Long id){
        Optional<Columns> columns = columnsRepository.findById(id);
        return new ResponseEntity<>(columns, HttpStatus.OK);
    }
}
