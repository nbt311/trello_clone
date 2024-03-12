package com.example.trellobackend.dto;

import com.example.trellobackend.models.User;
import com.example.trellobackend.models.board.card.Card;
import com.example.trellobackend.models.board.card.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

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
    private String elapsedTime;


    public CommentDTO(Comment comment,CardDTO cardDTO, UserDTO userDTO){
        this.id = comment.getId();
        this.content = comment.getContent();
        this.cardDTO = cardDTO;
        this.userDTO = userDTO;
        this.createdAt = comment.getCreatedAt();
    }
    public CommentDTO(Long id, String content,User user, LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.userDTO = new UserDTO(user);
        this.createdAt = createdAt;
    }

    public void setElapsedTime(String elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public String getTimeElapsedFromCreation() {
        LocalDateTime currentTime = LocalDateTime.now();
        long minutes = ChronoUnit.MINUTES.between(createdAt, currentTime);

        if (minutes < 1) {
            return "Vừa xong";
        } else if (minutes < 60) {
            return minutes + " phút trước";
        } else if (createdAt.toLocalDate().equals(currentTime.toLocalDate())) {
            return createdAt.format(DateTimeFormatter.ofPattern("HH:mm"));
        } else if (createdAt.toLocalDate().equals(currentTime.toLocalDate().minusDays(1))) {
            return createdAt.format(DateTimeFormatter.ofPattern("HH:mm"));
        } else {
            return createdAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        }
    }
}
