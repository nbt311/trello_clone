package com.example.trellobackend.controllers;

import com.example.trellobackend.models.board.Board;
import com.example.trellobackend.models.workspace.Workspace;
import com.example.trellobackend.payload.request.BoardRequest;
import com.example.trellobackend.payload.response.MessageResponse;
import com.example.trellobackend.services.impl.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/boards")
public class BoardController {
    @Autowired
    private BoardService boardService;
    @PostMapping("/create")
    public ResponseEntity<?> createBoard(@RequestBody BoardRequest boardRequest){
        try {
            Board board = boardService.createBoard(boardRequest);
            return new ResponseEntity<>(board, HttpStatus.CREATED);
        } catch (UsernameNotFoundException e) {
            // Xử lý khi không tìm thấy người dùng
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
        }
    }
}
