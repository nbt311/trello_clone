package com.example.trellobackend.controllers;

import com.example.trellobackend.dto.UserDTO;
import com.example.trellobackend.models.User;
import com.example.trellobackend.models.workspace.Workspace;
import com.example.trellobackend.payload.request.AvatarRequest;
import com.example.trellobackend.repositories.WorkspaceRepository;
import com.example.trellobackend.services.IUserService;
import com.example.trellobackend.services.WorkspaceService;
import com.example.trellobackend.services.impl.BoardService;
import com.example.trellobackend.services.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.example.trellobackend.payload.request.ChangePasswordRequest;
import com.example.trellobackend.payload.response.MessageResponse;
import com.example.trellobackend.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    @PostMapping("/{userId}/avatar")
    public ResponseEntity<String> updateAvatar(@PathVariable Long userId, @RequestBody AvatarRequest data) {
        Optional<User> optionalUser = iUserService.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setAvatarUrl(data.getImage());
            iUserService.save(user);
            return ResponseEntity.ok("Avatar updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/security")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest request) throws IllegalAccessException {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
                throw new IllegalAccessException("Wrong password");
            }
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(user);
            return ResponseEntity.ok(new MessageResponse("Change password successfully!"));
        } else {
            // Xử lý khi không tìm thấy người dùng với email cung cấp
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("User not found"));
        }
    }
    @GetMapping("/suggest/{query}")
    public List<User> suggestUsers(@PathVariable String query) {
        // Xử lý logic để trả về danh sách người dùng dựa trên 'query'
        return iUserService.getSuggestedUsers(query);
    }


        @GetMapping("/{userId}/workspaces")
        public ResponseEntity<UserDTO> getUserWorkspaces(@PathVariable Long userId) {
            UserDTO userDTO = userService.getUserWorkspaces(userId);

            if (userDTO != null) {
                return new ResponseEntity<>(userDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
    }