package com.example.trellobackend.controllers;

import com.example.trellobackend.models.workspace.Members;
import com.example.trellobackend.models.workspace.Workspace;
import com.example.trellobackend.models.workspace.Type;
import com.example.trellobackend.payload.request.WorkspaceRequest;
import com.example.trellobackend.payload.response.MessageResponse;
import com.example.trellobackend.repositories.WorkspaceRepository;
import com.example.trellobackend.repositories.WorkspaceTypeRepository;
import com.example.trellobackend.services.impl.WorkspaceMemberService;
import com.example.trellobackend.services.impl.WorkspaceServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/workspaces")
public class WorkspaceController {
    @Autowired
    private WorkspaceServiceImpl workspaceService;
    @Autowired
    private WorkspaceRepository workspaceRepository;
    @Autowired
    private WorkspaceTypeRepository workspaceTypeRepository;
    @Autowired
    private WorkspaceMemberService workspaceMemberService;
    @GetMapping
    public ResponseEntity<Iterable<Workspace>> findAllWorkspace(){
        Iterable<Workspace> workspaces = workspaceService.findAll();
        return new ResponseEntity<>(workspaces, HttpStatus.OK);
    }
@PostMapping("/create")
public ResponseEntity<?> createWorkspace(@RequestBody WorkspaceRequest workspaceRequest) {
    try {
        String frontendURL = workspaceRequest.getFrontendURL();
        Workspace workspace = workspaceService.createWorkspace(workspaceRequest, frontendURL);
        return new ResponseEntity<>(workspace, HttpStatus.CREATED);
    } catch (UsernameNotFoundException e) {
        // Xử lý khi không tìm thấy người dùng
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
    }
}
    @GetMapping("/type")
    public List<Type> getAllWorkspaceTypes() {
        return workspaceTypeRepository.findAll();
    }

    @GetMapping("/{id}/members" )
    public ResponseEntity<Iterable<Members>> findMembersByWorkspace (@PathVariable Long id){
        Iterable<Members> membersList = workspaceMemberService.findAllByWorkspace(id);
        return new ResponseEntity<>(membersList, HttpStatus.OK);
    }
}
