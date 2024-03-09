package com.example.trellobackend.services.impl;

import com.example.trellobackend.dto.BoardResponseDTO;
import com.example.trellobackend.dto.CardDTO;
import com.example.trellobackend.dto.ColumnsDTO;
import com.example.trellobackend.dto.LabelDTO;
import com.example.trellobackend.models.board.Board;
import com.example.trellobackend.models.board.Card;
import com.example.trellobackend.models.board.Columns;
import com.example.trellobackend.models.board.Label;
import com.example.trellobackend.payload.request.CardRequest;
import com.example.trellobackend.repositories.BoardRepository;
import com.example.trellobackend.repositories.CardRepository;
import com.example.trellobackend.repositories.ColumnsRepository;
import com.example.trellobackend.services.ICardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CardService implements ICardService {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private ColumnsRepository columnsRepository;
    @Autowired
    private CardRepository cardRepository;

    @Override
    public Iterable<Card> findAll() {
        return null;
    }

    @Override
    public Optional<Card> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void save(Card card) {

    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public BoardResponseDTO createNewCard(CardRequest cardRequest) {
        Optional<Board> boardOptional = boardRepository.findById(cardRequest.getBoardId());

        if (boardOptional.isPresent()) {
            Board board = boardOptional.get();
            Optional<Columns> columnsOptional = columnsRepository.findById(cardRequest.getColumnId());

            if (columnsOptional.isPresent()) {
                Columns columns = columnsOptional.get();
                Card newCard = new Card();
                newCard.setTitle(cardRequest.getTitle());
                newCard.setBoard(board);
                newCard.setColumn(columns);
                cardRepository.save(newCard);

                columns.getCardOrderIds().add(newCard.getId());
                columns.getCards().add(newCard);
                columnsRepository.save(columns);

                BoardResponseDTO responseDTO = new BoardResponseDTO();
                responseDTO.setId(board.getId());
                responseDTO.setTitle(board.getTitle());
                responseDTO.setVisibility(board.getVisibilities());

                List<ColumnsDTO> columnsDTOList = board.getColumns().stream()
                        .map(ColumnsDTO::fromEntity)
                        .collect(Collectors.toList());

                responseDTO.setColumns(columnsDTOList);

                List<Long> columnOrderIds = board.getColumnOrderIds();
                responseDTO.setColumnOrderIds(columnOrderIds);

                return responseDTO;
            } else {
                throw new RuntimeException("Error: Columns not found.");
            }
        } else {
            throw new RuntimeException("Error: Board not found.");
        }
    }

    @Override
    public List<LabelDTO> getAllLabelByCardId(Long cardId){
        Card card = cardRepository.findById(cardId).orElse(null);
        if(card != null){
            return card.getLabels().stream()
                    .map(LabelDTO::new
                    )
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("Card not found");
        }
    }

    public void deleteLabelFromCard(Long cardId, Label label){
        Optional<Card> cardOptional = cardRepository.findById(cardId);
        if(cardOptional.isPresent()){
            Card card = cardOptional.get();
            if(card.getLabels().contains(label)){
                card.getLabels().remove(label);
                cardRepository.save(card);
            }
        } else {
            throw new RuntimeException("Card not found");
        }
    }
}
