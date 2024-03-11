package com.example.trellobackend.controllers;

import com.example.trellobackend.dto.BoardResponseDTO;
import com.example.trellobackend.dto.CardDTO;
import com.example.trellobackend.dto.ColumnsDTO;
import com.example.trellobackend.dto.LabelDTO;
import com.example.trellobackend.models.board.Card;
import com.example.trellobackend.models.board.Label;
import com.example.trellobackend.payload.request.CardRequest;
import com.example.trellobackend.repositories.LabelRepository;
import com.example.trellobackend.services.impl.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/cards")
public class CardController {
    @Autowired
    private CardService cardService;
    @Autowired
    private LabelRepository labelRepository;
    @PostMapping("/create")
    public ResponseEntity<?> createNewCard(@RequestBody CardRequest cardRequest) {
            BoardResponseDTO responseDTO = cardService.createNewCard(cardRequest);
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{cardId}/allLabels")
    public ResponseEntity<?> getAllLabelsByCardId(@PathVariable Long cardId){
        try {
            List<LabelDTO> labelDTOList = cardService.getAllLabelByCardId(cardId);
            return new ResponseEntity<>(labelDTOList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Card not found", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{cardId}/newLabels/{labelId}")
    public ResponseEntity<?> addLabelToCard(@PathVariable Long cardId, @PathVariable Long labelId){
        try {
            cardService.addLabelToCard(cardId, labelId);
            return new ResponseEntity<>("Label added", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
