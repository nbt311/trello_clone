package com.example.trellobackend.controllers;

import com.example.trellobackend.models.User;
import com.example.trellobackend.payload.request.AvatarRequest;
import com.example.trellobackend.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private IUserService iUserService;

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
}
