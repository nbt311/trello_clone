package com.example.trellobackend.controllers;

import com.example.trellobackend.dto.UserDTO;
import com.example.trellobackend.dto.WorkspaceDTO;
import com.example.trellobackend.models.board.Board;
import com.example.trellobackend.models.workspace.Members;
import com.example.trellobackend.enums.UserRole;
import com.example.trellobackend.models.User;

import com.example.trellobackend.models.workspace.Workspace;
import com.example.trellobackend.models.workspace.Type;
import com.example.trellobackend.payload.request.WorkspaceRequest;
import com.example.trellobackend.payload.response.MessageResponse;
import com.example.trellobackend.repositories.UserRepository;
import com.example.trellobackend.repositories.BoardRepository;
import com.example.trellobackend.repositories.WorkspaceMemberRepository;
import com.example.trellobackend.repositories.WorkspaceRepository;
import com.example.trellobackend.repositories.WorkspaceTypeRepository;

import com.example.trellobackend.services.impl.EmailService;

import com.example.trellobackend.services.impl.WorkspaceServiceImpl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

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
    private EmailService emailService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  BoardRepository boardRepository;

    @GetMapping("/all")
    public ResponseEntity<List<WorkspaceDTO>> getAllWorkspaces() {
        List<WorkspaceDTO> workspaces = workspaceService.getAllWorkspaces();

        return new ResponseEntity<>(workspaces, HttpStatus.OK);
    }

    @GetMapping("/{workspaceId}")
    public ResponseEntity<WorkspaceDTO> getWorkspace(@PathVariable Long workspaceId) {
        Workspace workspace = workspaceService.getWorkspaceById(workspaceId);
        WorkspaceDTO workspaceDTO = new WorkspaceDTO(workspace);
        return ResponseEntity.ok(workspaceDTO);
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

    @GetMapping("/{workspaceId}/members")
    public ResponseEntity<List<UserDTO>> getWorkspaceMembers(@PathVariable Long workspaceId) {
        try {
            List<UserDTO> members = workspaceService.getWorkspaceMembers(workspaceId);
            return new ResponseEntity<>(members, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{workspaceId}/boards")
    public ResponseEntity<?> findBoardsByWorkspace(@PathVariable Long workspaceId){
        try{
            Iterable<Board> boardsList = boardRepository.findBoardByWorkspaceId(workspaceId);
            if (boardsList == null){
                return new ResponseEntity<>("Workspace not found", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(boardsList, HttpStatus.OK);
        } catch (Exception e) {
            String errorMessage = "There has been problems with the server" + ":" + e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{workspaceId}/invite/{email}")
    public ResponseEntity<?> inviteUserToWorkspace(@PathVariable Long workspaceId, @PathVariable String email) {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new EntityNotFoundException("Workspace not found"));
        String invitation = workspaceService.inviteUserToWorkspace(email, workspace);
        emailService.sendInvitationEmail(email, invitation);

        return new ResponseEntity<>(invitation, HttpStatus.CREATED);
    }

    @PostMapping("/{workspaceId}/addUser/{userEmail}")
    public ResponseEntity<?> addUserToWorkspace(@PathVariable Long workspaceId, @PathVariable String userEmail) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                Workspace workspace = workspaceService.getWorkspaceById(workspaceId);

                // Kiểm tra xem người dùng với email đã tồn tại hay không
                Optional<User> userOptional = userRepository.findByEmail(userEmail);
                if (userOptional.isPresent()) {
                    // Thêm user vào workspace với quyền mặc định (ví dụ: ROLE_MEMBER)
                    User userToAdd = userOptional.get();
                    workspaceService.addMemberToWorkspace(workspace, userToAdd, UserRole.ROLE_USER);

                    // Gửi email mời tham gia nhóm
                    String inviteLink = workspaceService.inviteUserToWorkspace(userEmail, workspace);
                    emailService.sendInvitationEmail(userEmail, inviteLink);

                    return ResponseEntity.ok("User added to workspace successfully and invitation sent.");
                } else {
                    // Trường hợp người dùng với email không tồn tại
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with email: " + userEmail);
                }
            } else {
                // Trường hợp không có quyền thực hiện thao tác
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized. User not authenticated.");
            }
        } catch (Exception e) {
            // Xử lý lỗi nếu có
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding user to workspace: " + e.getMessage());
        }
    }
    @GetMapping("{id}/workspace")
    public ResponseEntity<?> getWorkspaceById(@PathVariable Long id){
        Optional<Workspace> workspace =  workspaceRepository.findById(id);
        return new ResponseEntity<>(workspace, HttpStatus.OK);
    }
}
