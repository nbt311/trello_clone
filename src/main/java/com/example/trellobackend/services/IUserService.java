package com.example.trellobackend.services;

import com.example.trellobackend.dto.UserDTO;
import com.example.trellobackend.models.User;

import java.util.List;
import java.util.Optional;

public interface IUserService extends IGeneralService<User> {
    Optional<User> findByEmail(String email);
    List<UserDTO> getSuggestedUsers(String query);
}
