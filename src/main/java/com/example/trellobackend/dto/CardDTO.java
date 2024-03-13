package com.example.trellobackend.dto;

import com.example.trellobackend.models.User;
import com.example.trellobackend.models.board.card.Attachment;
import com.example.trellobackend.models.board.card.Card;
import com.example.trellobackend.models.board.card.Label;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CardDTO {
    private Long id;
    private Long boardId;
    private Long columnId;
    private String title;
    private List<Attachment> attachments;
    private String description;

    public CardDTO(Card card){
        this.id = card.getId();
        this.boardId = card.getBoard().getId();
        this.columnId = card.getColumn().getId();
        this.title = card.getTitle();
        this.attachments = card.getAttachments();
        this.description = card.getDescription();
    }

    public CardDTO(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
}
