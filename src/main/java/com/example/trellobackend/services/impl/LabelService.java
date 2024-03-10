package com.example.trellobackend.services.impl;

import com.example.trellobackend.models.board.Label;
import com.example.trellobackend.repositories.CardRepository;
import com.example.trellobackend.repositories.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
}
