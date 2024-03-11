package com.example.trellobackend.controllers;

import com.example.trellobackend.dto.BoardResponseDTO;
import com.example.trellobackend.dto.CardDTO;
import com.example.trellobackend.dto.UserDTO;
import com.example.trellobackend.models.board.card.Attachment;
import com.example.trellobackend.payload.request.CardRequest;
import com.example.trellobackend.services.impl.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/cards")
public class CardController {
    @Autowired
    private CardService cardService;
    @PostMapping("/create")
    public ResponseEntity<?> createNewCard(@RequestBody CardRequest cardRequest) {
            BoardResponseDTO responseDTO = cardService.createNewCard(cardRequest);
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{cardId}")
    public ResponseEntity<?> changeCardId(@PathVariable Long cardId, @RequestBody CardDTO title ){
        try{
            cardService.changeCardTitle(cardId, title);
            return new ResponseEntity<>("success",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("error card not found " + cardId,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/{cardId}/members")
    public ResponseEntity<?> addMemberToCard(@PathVariable Long cardId, @RequestBody UserDTO userName){
        try{
            cardService.addMembersToCard(cardId, userName);
            return new ResponseEntity<>("success",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("error card not found " + cardId,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{cardId}/members")
    public ResponseEntity<?> getMembersByCard(@PathVariable Long cardId){
        try{
            List<UserDTO> users = cardService.getUserByCard(cardId);
            return new ResponseEntity<>(users,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("error card not found " + cardId,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("{cardId}/attachment")
    public ResponseEntity<?> changeCardAttachment(@PathVariable Long cardId, @RequestBody  List<Attachment> attachments ){
        try{
            cardService.changeCardAttachment(cardId, attachments);
            return new ResponseEntity<>("success",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("error card not found " + cardId,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{cardId}/attachment")
    public ResponseEntity<List<Attachment>> getAttachmentsByCardId(@PathVariable Long cardId) {
        try {
            List<Attachment> attachments = cardService.getAttachmentsByCardId(cardId);
            return new ResponseEntity<>(attachments, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
