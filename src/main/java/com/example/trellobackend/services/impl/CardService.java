package com.example.trellobackend.services.impl;

import com.example.trellobackend.dto.*;
import com.example.trellobackend.models.User;
import com.example.trellobackend.models.board.Board;
import com.example.trellobackend.models.board.card.Attachment;
import com.example.trellobackend.models.board.card.Card;
import com.example.trellobackend.models.board.Columns;
import com.example.trellobackend.models.board.card.Comment;
import com.example.trellobackend.models.board.card.Label;
import com.example.trellobackend.payload.request.CardRequest;
import com.example.trellobackend.repositories.BoardRepository;
import com.example.trellobackend.repositories.CardRepository;
import com.example.trellobackend.repositories.ColumnsRepository;
import com.example.trellobackend.repositories.LabelRepository;
import com.example.trellobackend.repositories.UserRepository;
import com.example.trellobackend.services.ICardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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
    @Autowired
    private LabelRepository labelRepository;

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

    @Override
    public void changeCardAttachment(Long cardId, List<Attachment> attachments) {
        Optional<Card> cardOptional = cardRepository.findById(cardId);
        if (cardOptional.isPresent()) {
            Card card = cardOptional.get();
            for (Attachment attachment : attachments) {
                card.getAttachments().add(attachment);
            }

            cardRepository.save(card);
        } else {
            throw new RuntimeException("Error: Card not found.");
        }
    }

    @Override
    public List<Attachment> getAttachmentsByCardId(Long cardId) {
        return cardRepository.findById(cardId)
                .map(Card::getAttachments)
                .orElseThrow(() -> new RuntimeException("Error: Card not found for ID " + cardId));
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

    @Override
    public List<CommentDTO> getCommentsByCardId(Long cardId) {
        Card card = cardRepository.findById(cardId).orElse(null);
        if(card != null){
            return card.getComments().stream()
                    .map(comment -> new CommentDTO(comment.getId(),
                            comment.getContent(),
                            comment.getUser(),
                            comment.getCreatedAt())
                    )
                    .map(commentDTO -> {
                        commentDTO.setElapsedTime(commentDTO.getTimeElapsedFromCreation());
                        return commentDTO;
                    })
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("Card not found");
        }
    }

    @Override
    public List<ColumnsDTO> getSuggestedCards(String query,Long boardId){
        List<Columns> allColumns = columnsRepository.findByBoardId(boardId);
        List<ColumnsDTO> columnsContainingCards = new ArrayList<>();

        List<CardDTO> cards = cardRepository.findCardByPartialMatchAndBoard(query, boardId);

        for (Columns column : allColumns) {
            List<Card> cardsInColumn = column.getCards();
            List<CardDTO> matchedCardsInColumn = cardsInColumn.stream()
                    .filter(card -> cards.stream().anyMatch(c -> c.getId().equals(card.getId())))
                    .map(CardDTO::new)
                    .collect(Collectors.toList());
            if (!matchedCardsInColumn.isEmpty()) {

                ColumnsDTO columnDTO = ColumnsDTO.fromEntity(column);
                columnDTO.setCards(matchedCardsInColumn);
                columnsContainingCards.add(columnDTO);
            }
        }

        return columnsContainingCards;
    }

    public void addLabelToCard(Long cardId, Long labelId){
        Optional<Card> cardOptional = cardRepository.findById(cardId);
        if(cardOptional.isPresent()){
            Card card = cardOptional.get();
            Optional<Label> labelOptional = labelRepository.findById(labelId);
            if(labelOptional.isPresent()){
                Label label = labelOptional.get();
                card.getLabels().add(label);
                cardRepository.save(card);
            }
        }
    }

    public void deleteLabelFromCard(Long cardId, Label label){
        Optional<Card> cardOptional = cardRepository.findById(cardId);
        if(cardOptional.isPresent()){
            Card card = cardOptional.get();
            if(card.getLabels().contains(label)){
                card.getLabels().remove(label);
                cardRepository.save(card);
            } else {
                throw new RuntimeException("Error");
            }
        } else {
            throw new RuntimeException("Card Not found");
        }
    }
}
