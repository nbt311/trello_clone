package com.example.trellobackend.services.impl;

import com.example.trellobackend.dto.CardDTO;
import com.example.trellobackend.dto.ColumnsDTO;
import com.example.trellobackend.models.board.Board;
import com.example.trellobackend.models.board.Card;
import com.example.trellobackend.models.board.Columns;
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
    public ColumnsDTO createNewCard(CardRequest cardRequest) {
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
                ColumnsDTO responseDTO = new ColumnsDTO();
                responseDTO.setId(columns.getId());
                responseDTO.setTitle(columns.getTitle());
                List<CardDTO> cardDTOList = columns.getCards()
                        .stream()
                        .map(card -> {
                            CardDTO cardDTO = new CardDTO();
                            cardDTO.setId(card.getId());
                            cardDTO.setTitle(card.getTitle());
                            return cardDTO;
                        })
                        .collect(Collectors.toList());

                responseDTO.setCards(cardDTOList);

                List<Long> cardOrderIds = columns.getCardOrderIds();
                cardOrderIds.add(newCard.getId());
                columns.setCardOrderIds(cardOrderIds);

                List<Card> cards = columns.getCards();
                cards.add(newCard);
                columns.setCards(cards);

                columnsRepository.save(columns);
                return responseDTO;
            } else {
                throw new RuntimeException("Error: Columns not found.");
            }
        } else {
            throw new RuntimeException("Error: Board not found.");

        }
    }
}
