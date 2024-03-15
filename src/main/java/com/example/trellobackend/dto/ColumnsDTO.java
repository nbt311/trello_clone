package com.example.trellobackend.dto;

import com.example.trellobackend.models.board.Board;
import com.example.trellobackend.models.board.Columns;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ColumnsDTO {
    private Long id;
    private Long boardId;
    private String title;
    private List<Long> cardOrderIds;
    private List<CardDTO> cards;

    public ColumnsDTO(Long id, String title){
        this.id = id;
        this.title = title;
    }
    public ColumnsDTO(Columns columns, List<Long> cardOrderIds, List<CardDTO> cards) {
        this.id = columns.getId();
        this.boardId = columns.getBoard().getId();
        this.title = columns.getTitle();
        this.cardOrderIds = cardOrderIds;
        this.cards = cards;
    }
    public static ColumnsDTO fromEntity(Columns columns) {
        ColumnsDTO responseDTO = new ColumnsDTO();
        responseDTO.setId(columns.getId());
        responseDTO.setBoardId(columns.getBoard().getId());
        responseDTO.setTitle(columns.getTitle());

        List<CardDTO> cardDTOList = Optional.ofNullable(columns.getCards())
                .orElse(Collections.emptyList())
                .stream()
                .map(card -> {
                    CardDTO cardDTO = new CardDTO();
                    cardDTO.setId(card.getId());
                    cardDTO.setBoardId(card.getBoard().getId());
                    cardDTO.setColumnId(card.getColumn().getId());
                    cardDTO.setTitle(card.getTitle());
                    // Map other properties as needed
                    return cardDTO;
                })
                .collect(Collectors.toList());

        responseDTO.setCards(cardDTOList);
        responseDTO.setCardOrderIds(columns.getCardOrderIds() != null ? columns.getCardOrderIds() : Collections.emptyList());
        return responseDTO;
    }
}
