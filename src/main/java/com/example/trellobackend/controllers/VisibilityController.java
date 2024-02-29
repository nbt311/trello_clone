package com.example.trellobackend.controllers;

import com.example.trellobackend.models.board.Board;
import com.example.trellobackend.services.impl.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test/visibility")
public class VisibilityController {
    @Autowired
    private BoardService boardService;
//    @GetMapping("/{id}/boards")
//    public ResponseEntity<Iterable<Board>> getBoardByVisibility(@PathVariable Long id){
//        Iterable<Board> boardsList = boardService.getBoardByVisibility(id);
//        return new ResponseEntity<>(boardsList, HttpStatus.OK);
//    }
}
