package com.example.trellobackend.controllers;

import com.example.trellobackend.models.User;
import com.example.trellobackend.models.Workspace;
import com.example.trellobackend.models.WorkspaceType;
import com.example.trellobackend.payload.request.WorkspaceRequest;
import com.example.trellobackend.payload.response.MessageResponse;
import com.example.trellobackend.repositories.UserRepository;
import com.example.trellobackend.repositories.WorkspacePermissionRepository;
import com.example.trellobackend.repositories.WorkspaceRepository;
import com.example.trellobackend.repositories.WorkspaceTypeRepository;
import com.example.trellobackend.services.impl.WorkspaceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/workspaces")
public class WorkspaceController {
    @Autowired
    private WorkspaceRepository workspaceRepository;
    @Autowired
    private WorkspaceServiceImpl workspaceService;
    @Autowired
    private WorkspaceTypeRepository workspaceTypeRepository;
    @Autowired
    private UserRepository  userRepository;
    @GetMapping
    public ResponseEntity<Iterable<Workspace>> findAllWorkspace(){
        Iterable<Workspace> workspaces = workspaceService.findAll();
        return new ResponseEntity<>(workspaces, HttpStatus.OK);
    }
//    @PostMapping("/create")
//    public ResponseEntity<?> createWorkspace(@RequestBody WorkspaceRequest workspaceRequest){
//        Optional<User> userOptional = userRepository.findByEmail(workspaceRequest.getEmail());
//        if (userOptional.isPresent()){
//            Workspace workspace = workspaceService.createWorkspace(workspaceRequest);
//            return new ResponseEntity<>(workspace, HttpStatus.CREATED);
//        } else {
//            // Xử lý khi không tìm thấy người dùng với email cung cấp
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("User not found"));
//        }
//    }
@PostMapping("/create")
public ResponseEntity<?> createWorkspace(@RequestBody WorkspaceRequest workspaceRequest) {
    try {
        Workspace workspace = workspaceService.createWorkspace(workspaceRequest);
        return new ResponseEntity<>(workspace, HttpStatus.CREATED);
    } catch (UsernameNotFoundException e) {
        // Xử lý khi không tìm thấy người dùng
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
    }
}

    @PostMapping("/cr")
    public ResponseEntity<Workspace> createWorkspace(@RequestBody Workspace workspace) {
        return new ResponseEntity<>(workspaceRepository.save(workspace), HttpStatus.CREATED);
    }



    @GetMapping("/type")
    public List<WorkspaceType> getAllWorkspaceTypes() {
        return workspaceTypeRepository.findAll();
    }
}
