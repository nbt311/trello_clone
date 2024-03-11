package com.example.trellobackend.dto;

import com.example.trellobackend.models.board.card.Card;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CardDTO {
    private Long id;
    private Long boardId;
    private Long columnId;
    private String title;
    private List<String> attachments;
    public CardDTO(Card card){
        this.id = card.getId();
        this.boardId = card.getBoard().getId();
        this.columnId = card.getColumn().getId();
        this.title = card.getTitle();
        this.attachments = card.getAttachmentsLink();
    }
}
