package com.example.trellobackend.services.impl;

import com.example.trellobackend.dto.UserDTO;
import com.example.trellobackend.dto.WorkspaceDTO;
import com.example.trellobackend.enums.MemberRole;
import com.example.trellobackend.models.User;
import com.example.trellobackend.repositories.UserRepository;
import com.example.trellobackend.services.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void remove(Long id) {
        userRepository.deleteById(id);
    }

    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        return (User) authentication.getPrincipal();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getSuggestedUsers(String query) {
        return userRepository.findUsersByPartialMatch(query);
    }

    public UserDTO getUserWorkspaces(Long userId) {
        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            List<WorkspaceDTO> ownedWorkspaces = user.getWorkspaceMembers().stream()
                    .filter(membership -> MemberRole.ADMIN.equals(membership.getRole()))
                    .map(membership -> new WorkspaceDTO(membership.getWorkspace()))
                    .collect(Collectors.toList());
            List<WorkspaceDTO> memberWorkspaces = user.getWorkspaceMembers().stream()
                    .filter(membership -> MemberRole.MEMBER.equals(membership.getRole()))
                    .map(membership -> new WorkspaceDTO(membership.getWorkspace()))
                    .collect(Collectors.toList());

            return new UserDTO(user, ownedWorkspaces, memberWorkspaces);
        }

        return null;
    }
}
