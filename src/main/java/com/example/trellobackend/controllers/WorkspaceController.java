package com.example.trellobackend.controllers;

<<<<<<< HEAD
import com.example.trellobackend.models.workspace.Workspace;
import com.example.trellobackend.models.workspace.Type;
import com.example.trellobackend.payload.request.WorkspaceRequest;
import com.example.trellobackend.payload.response.MessageResponse;
import com.example.trellobackend.services.impl.WorkspaceServiceImpl;
=======
>>>>>>> parent of fad964f (recreate api create workspace. need to add type and permission)
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

<<<<<<< HEAD
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
=======
>>>>>>> parent of fad964f (recreate api create workspace. need to add type and permission)
@RestController
@CrossOrigin("*")
@RequestMapping("/api/workspaces")
public class WorkspaceController {
    @Autowired
    private WorkspaceServiceImpl workspaceService;
    @GetMapping
    public ResponseEntity<Iterable<Workspace>> findAllWorkspace(){
        Iterable<Workspace> workspaces = workspaceService.findAll();
        return new ResponseEntity<>(workspaces, HttpStatus.OK);
    }
<<<<<<< HEAD
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
    public List<Type> getAllWorkspaceTypes() {
        return workspaceTypeRepository.findAll();
=======
    @PostMapping("/create")
    public ResponseEntity<?> createWorkspace(@RequestBody Workspace workspace){
        Workspace createWorkspace = workspaceService.save(workspace);
        return new ResponseEntity<>(createWorkspace, HttpStatus.CREATED);
>>>>>>> parent of fad964f (recreate api create workspace. need to add type and permission)
    }
}
