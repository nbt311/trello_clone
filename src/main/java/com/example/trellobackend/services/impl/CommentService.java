package com.example.trellobackend.services.impl;

import com.example.trellobackend.dto.CardDTO;
import com.example.trellobackend.dto.CommentDTO;
import com.example.trellobackend.dto.UserDTO;
import com.example.trellobackend.models.User;
import com.example.trellobackend.models.board.card.Card;
import com.example.trellobackend.models.board.card.Comment;
import com.example.trellobackend.repositories.CardRepository;
import com.example.trellobackend.repositories.CommentRepository;
import com.example.trellobackend.repositories.UserRepository;
import com.example.trellobackend.services.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
@Service
public class CommentService implements ICommentService {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Override
    public Iterable<Comment> findAll() {
        return null;
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void save(Comment comment) {

    }

    @Override
    public void remove(Long id) {

    }


    @Override
    public CommentDTO createComment(Comment comment, Long cardId, Long userId) {
        Optional<Card> cardOptional = cardRepository.findById(cardId);
        if (cardOptional.isPresent()){
            Card card = cardOptional.get();
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()){
                User user = userOptional.get();
                comment.setCard(card);
                comment.setUser(user);
                comment.setCreatedAt(LocalDateTime.now());
                Comment createdComment = commentRepository.save(comment);
                card.getComments().add(createdComment);
                cardRepository.save(card);
                CardDTO cardDTO = new CardDTO(card);
                UserDTO userDTO = new UserDTO();
                userDTO.setId(user.getId());
                userDTO.setUsername(user.getUsername());
                userDTO.setEmail(user.getEmail());
                userDTO.setAvatarUrl(user.getAvatarUrl());
                CommentDTO commentDTO = new CommentDTO();
                commentDTO.setId(createdComment.getId());
                commentDTO.setContent(createdComment.getContent());
                commentDTO.setCardDTO(cardDTO);
                commentDTO.setUserDTO(userDTO);
                commentDTO.setCreatedAt(createdComment.getCreatedAt());
                return commentDTO;
            } else {
                throw new RuntimeException("User not found with id: " + userId);
            }

        } else {
            throw new RuntimeException("Card not found with id: " + cardId);
        }
    }
}
