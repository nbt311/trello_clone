package com.example.trellobackend.controllers;

import com.example.trellobackend.dto.CommentDTO;
import com.example.trellobackend.models.board.card.Comment;
import com.example.trellobackend.services.impl.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/create/{cardId}/{userId}")
    public ResponseEntity<?> createComment(@RequestBody Comment comment, @PathVariable Long cardId, @PathVariable Long userId){
        try {
            CommentDTO newComment = commentService.createComment(comment,cardId,userId);
            return new ResponseEntity<>(newComment, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
