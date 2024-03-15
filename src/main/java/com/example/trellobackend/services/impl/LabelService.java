package com.example.trellobackend.services.impl;

import com.example.trellobackend.models.board.card.Label;
import com.example.trellobackend.dto.CardDTO;
import com.example.trellobackend.repositories.CardRepository;
import com.example.trellobackend.repositories.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LabelService{
    @Autowired
    private LabelRepository labelRepository;

    public Iterable<Label> findAll() {
        return labelRepository.findAll();
    }

    public Optional<Label> findById(Long id) {
        return labelRepository.findById(id);
    }

    public Label save(Label label) {
        return labelRepository.save(label);
    }

    public void remove(Long id) {
        labelRepository.deleteById(id);
    }

    public List<CardDTO> getAllCardByLabel(Long labelId){
        Label label = labelRepository.findById(labelId).orElse(null);
        if (label != null){
            return label.getCards().stream()
                    .map(CardDTO::new)
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("Label not found");
        }
    }
}
