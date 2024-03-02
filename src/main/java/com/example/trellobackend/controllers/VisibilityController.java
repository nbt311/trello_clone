package com.example.trellobackend.controllers;

import com.example.trellobackend.models.board.Board;
import com.example.trellobackend.repositories.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test/visibility")
public class VisibilityController {
    @Autowired
    private BoardRepository boardRepository;
    @GetMapping("/{visibilityId}/boards")
    public ResponseEntity<?> findBoardsByVisibility(@PathVariable Long visibilityId){
        try{
            Iterable<Board> boardsList = boardRepository.findBoardByVisibility(visibilityId);
            if (boardsList == null){
                return new ResponseEntity<>("Board not found", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(boardsList, HttpStatus.OK);
        } catch (Exception e) {
            String errorMessage = "There has been problems with the server" + ":" + e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
