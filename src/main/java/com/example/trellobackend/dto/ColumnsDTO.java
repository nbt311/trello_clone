package com.example.trellobackend.dto;

import com.example.trellobackend.models.board.Board;
import com.example.trellobackend.models.board.Columns;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ColumnsDTO {
    private Long id;
    private String title;
    private List<Long> cardOrderIds;
    private List<CardDTO> cards;

    public ColumnsDTO(Long id, String title){
        this.id = id;
        this.title = title;
    }
    public ColumnsDTO(Columns columns, List<Long> cardOrderIds, List<CardDTO> cards) {
        this.id = columns.getId();
        this.title = columns.getTitle();
        this.cardOrderIds = cardOrderIds;
        this.cards = cards;
    }
    public static ColumnsDTO fromEntity(Columns columns) {
        ColumnsDTO responseDTO = new ColumnsDTO();
        responseDTO.setId(columns.getId());
        responseDTO.setTitle(columns.getTitle());

        List<CardDTO> cardDTOList = columns.getCards()
                .stream()
                .map(card -> {
                    CardDTO cardDTO = new CardDTO();
                    cardDTO.setId(card.getId());
                    cardDTO.setTitle(card.getTitle());
                    // Map other properties as needed
                    return cardDTO;
                })
                .collect(Collectors.toList());

        responseDTO.setCards(cardDTOList);
        responseDTO.setCardOrderIds(columns.getCardOrderIds());

//        if (!board.getVisibilities().isEmpty()) {
//            responseDTO.setVisibility(board.getVisibilities().iterator().next().getName());
//        }
        return responseDTO;
    }
}
