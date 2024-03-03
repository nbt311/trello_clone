package com.example.trellobackend.controllers;

import com.example.trellobackend.models.workspace.Members;
import com.example.trellobackend.repositories.WorkspaceMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test/members")
public class MemberController {
    @Autowired
    private WorkspaceMemberRepository workspaceMemberRepository;
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMembers(@PathVariable Long id){
        Optional<Members> membersOptional = workspaceMemberRepository.findById(id);
        if (membersOptional.isEmpty()){
            return new ResponseEntity<>("Member not found",HttpStatus.NOT_FOUND);
        }
        workspaceMemberRepository.deleteById(id);
        return new ResponseEntity<>("Deleted!!!!",HttpStatus.OK);
    }
}
