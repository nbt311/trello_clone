package com.example.trellobackend.services;

import com.example.trellobackend.dto.BoardResponseDTO;
import com.example.trellobackend.dto.CardDTO;
import com.example.trellobackend.dto.UserDTO;
import com.example.trellobackend.models.board.card.Card;
import com.example.trellobackend.payload.request.CardRequest;

import java.util.List;

public interface ICardService extends IGeneralService<Card>{
    BoardResponseDTO createNewCard(CardRequest cardRequest);
    void changeCardTitle(Long cardId, CardDTO title);
    void addMembersToCard(Long cardId, UserDTO data);
    List<UserDTO> getUserByCard(Long cardId);
    void changeCardAttachment(Long cardId, CardDTO data);

    List<String> getAttachmentLinks(Long cardId);
}
