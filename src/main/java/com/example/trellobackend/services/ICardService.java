package com.example.trellobackend.services;

import com.example.trellobackend.dto.BoardResponseDTO;
import com.example.trellobackend.dto.CardDTO;
import com.example.trellobackend.dto.ColumnsDTO;
import com.example.trellobackend.models.board.Card;
import com.example.trellobackend.models.board.Label;
import com.example.trellobackend.payload.request.CardRequest;

import java.util.List;

public interface ICardService extends IGeneralService<Card>{
    BoardResponseDTO createNewCard(CardRequest cardRequest);
    void changeCardTitle(Long cardId, CardDTO title);
}
