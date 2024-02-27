package com.example.trellobackend.controllers;

import com.example.trellobackend.models.workspace.Members;
import com.example.trellobackend.services.impl.WorkspaceMemberService;
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
    private WorkspaceMemberService workspaceMemberService;
    @DeleteMapping("/{id}")
    public ResponseEntity<Members> deleteMembers(@PathVariable Long id){
        Optional<Members> membersOptional = workspaceMemberService.findById(id);
        if (membersOptional.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        workspaceMemberService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
