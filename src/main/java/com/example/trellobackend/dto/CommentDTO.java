package com.example.trellobackend.dto;

import com.example.trellobackend.models.User;
import com.example.trellobackend.models.board.card.Card;
import com.example.trellobackend.models.board.card.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentDTO {
    private Long id;
    private String content;
    private CardDTO cardDTO;
    private UserDTO userDTO;
    private LocalDateTime createdAt;


    public CommentDTO(Comment comment,CardDTO cardDTO, UserDTO userDTO){
        this.id = comment.getId();
        this.content = comment.getContent();
        this.cardDTO = cardDTO;
        this.userDTO = userDTO;
        this.createdAt = comment.getCreatedAt();
    }
}
