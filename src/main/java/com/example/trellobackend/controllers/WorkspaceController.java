package com.example.trellobackend.controllers;

import com.example.trellobackend.models.Workspace;
import com.example.trellobackend.serviceImpls.WorkspaceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/test/workspaces")
public class WorkspaceController {
    @Autowired
    private WorkspaceServiceImpl workspaceService;
    @GetMapping
    public ResponseEntity<Iterable<Workspace>> findAllWorkspace(){
        Iterable<Workspace> workspaces = workspaceService.findAll();
        return new ResponseEntity<>(workspaces, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> createWorkspace(@RequestBody Workspace workspace){
        Workspace createWorkspace = workspaceService.save(workspace);
        return new ResponseEntity<>(createWorkspace, HttpStatus.CREATED);
    }
}
