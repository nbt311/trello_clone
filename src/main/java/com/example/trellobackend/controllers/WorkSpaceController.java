package com.example.trellobackend.controllers;

import com.example.trellobackend.models.User;
import com.example.trellobackend.models.WorkSpace;
import com.example.trellobackend.payload.request.WorkspaceRequest;
import com.example.trellobackend.serviceImpls.WorkspaceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/workspace")
public class WorkSpaceController {
    @Autowired
    private WorkspaceServiceImpl workspaceService;
    @PostMapping("/create")
    public ResponseEntity<?> createWorkSpace(@RequestBody WorkspaceRequest workspaceRequest, @AuthenticationPrincipal User creator){
        WorkSpace workSpace = workspaceService.createWorkspace(workspaceRequest,creator);
        return ResponseEntity.ok(workSpace);
    }
}
