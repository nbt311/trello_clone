package com.example.trellobackend.controllers;

import com.example.trellobackend.dto.BoardResponseDTO;
import com.example.trellobackend.dto.CardDTO;
import com.example.trellobackend.dto.ColumnsDTO;
import com.example.trellobackend.payload.request.CardRequest;
import com.example.trellobackend.services.impl.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/cards")
public class CardController {
    @Autowired
    private CardService cardService;
    @PostMapping("/create")
    public ResponseEntity<ColumnsDTO> createNewCard(@RequestBody CardRequest cardRequest) {
            ColumnsDTO responseDTO = cardService.createNewCard(cardRequest);
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
}
