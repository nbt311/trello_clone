package com.example.trellobackend.services;

import com.example.trellobackend.dto.*;
import com.example.trellobackend.models.board.card.Attachment;
import com.example.trellobackend.models.board.card.Card;
import com.example.trellobackend.models.board.card.Comment;
import com.example.trellobackend.payload.request.CardRequest;

import java.util.List;


public interface ICardService extends IGeneralService<Card> {
    BoardResponseDTO createNewCard(CardRequest cardRequest);

    void changeCardTitle(Long cardId, CardDTO title);

    void addMembersToCard(Long cardId, UserDTO data);

    List<UserDTO> getUserByCard(Long cardId);

    void changeCardAttachment(Long cardId, List<Attachment> attachments);

    List<Attachment> getAttachmentsByCardId(Long cardId);

    List<LabelDTO> getAllLabelByCardId(Long cardId);

    List<CommentDTO> getCommentsByCardId(Long cardId);
}
