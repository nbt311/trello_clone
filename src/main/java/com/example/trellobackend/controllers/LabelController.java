package com.example.trellobackend.controllers;

import com.example.trellobackend.models.board.card.Label;
import com.example.trellobackend.services.impl.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test/labels")
public class LabelController {
    @Autowired
    private LabelService labelService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllLabels() {
        Iterable<Label> labels = labelService.findAll();
        return new ResponseEntity<>(labels, HttpStatus.OK);
    }

    @GetMapping("/{labelId}")
    public ResponseEntity<?> getLabelById(@PathVariable Long labelId){
        Optional<Label> labelOptional = labelService.findById(labelId);
        if (labelOptional.isPresent()){
            return new ResponseEntity<>(labelOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Label not found", HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/create")
    public ResponseEntity<Label> createLabel(@RequestBody Label label){
        Label labelCreate = labelService.save(label);
        return new ResponseEntity<>(labelCreate, HttpStatus.CREATED);
    }

    @DeleteMapping("/")
    public ResponseEntity<Label> deleteLabel(@PathVariable Long labelId){
        labelService.remove(labelId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
