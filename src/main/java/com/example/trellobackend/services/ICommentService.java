package com.example.trellobackend.services;

import com.example.trellobackend.dto.CommentDTO;
import com.example.trellobackend.models.board.card.Comment;

public interface ICommentService extends IGeneralService<Comment> {
    CommentDTO createComment(Comment comment, Long cardId, Long userId);
}
