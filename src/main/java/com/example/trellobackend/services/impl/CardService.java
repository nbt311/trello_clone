package com.example.trellobackend.services.impl;

import com.example.trellobackend.dto.BoardResponseDTO;
import com.example.trellobackend.dto.CardDTO;
import com.example.trellobackend.dto.ColumnsDTO;
import com.example.trellobackend.dto.UserDTO;
import com.example.trellobackend.models.User;
import com.example.trellobackend.models.board.Board;
import com.example.trellobackend.models.board.card.Card;
import com.example.trellobackend.models.board.Columns;
import com.example.trellobackend.payload.request.CardRequest;
import com.example.trellobackend.repositories.BoardRepository;
import com.example.trellobackend.repositories.CardRepository;
import com.example.trellobackend.repositories.ColumnsRepository;
import com.example.trellobackend.repositories.UserRepository;
import com.example.trellobackend.services.ICardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CardService implements ICardService {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private ColumnsRepository columnsRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private UserRepository userRepository;

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
    public void changeCardTitle(Long cardId,CardDTO title) {
        Optional<Card> cardOptional = cardRepository.findById(cardId);
        if (cardOptional.isPresent()) {
            Card card = cardOptional.get();
            card.setTitle(title.getTitle());
            cardRepository.save(card);
        }else {
            throw new RuntimeException("Error: Card not found.");
        }
    }

    @Override
    public void addMembersToCard(Long cardId, UserDTO data) {
        Optional<Card> cardOptional = cardRepository.findById(cardId);
        if (cardOptional.isPresent()) {
            Card card = cardOptional.get();
            Optional<User> userOptional = userRepository.findByUsername(data.getUsername());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                card.getUsers().add(user);
                cardRepository.save(card);
            } else {
                throw new RuntimeException("Error: User not found.");
            }
        } else {
            throw new RuntimeException("Error: Card not found.");
        }
    }

    @Override
    public List<UserDTO> getUserByCard(Long cardId) {
        Optional<Card> cardOptional = cardRepository.findById(cardId);
        if (cardOptional.isPresent()) {
            Card card = cardOptional.get();
            Set<User> users = card.getUsers();
            List<UserDTO> responseDTO = users.stream().map(user -> {
                UserDTO userDTO = new UserDTO();
                userDTO.setId(user.getId());
                userDTO.setUsername(user.getUsername());
                userDTO.setEmail(user.getEmail());
                userDTO.setAvatarUrl(user.getAvatarUrl());
            return userDTO;
            }).collect(Collectors.toList());
            return responseDTO;
        } else {
            throw new RuntimeException("Error: Card not found.");
        }
    }


}
