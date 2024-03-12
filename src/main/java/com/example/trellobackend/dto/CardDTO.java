package com.example.trellobackend.dto;

import com.example.trellobackend.models.board.Card;
import com.example.trellobackend.models.board.Columns;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CardDTO {
    private Long id;
    private String title;
    private String description;

    public CardDTO(Card card){
        this.id = card.getId();
        this.title = card.getTitle();
        this.description = card.getDescription();
    }
}
