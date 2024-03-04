package com.example.trellobackend.dto;

import com.example.trellobackend.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private List<WorkspaceDTO> ownedWorkspaces;
    private List<WorkspaceDTO> memberWorkspaces;

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        // Map other fields as needed

        this.ownedWorkspaces = user.getOwnedWorkspaces().stream()
                .map(WorkspaceDTO::new)
                .collect(Collectors.toList());

        this.memberWorkspaces = user.getMemberWorkspaces().stream()
                .map(WorkspaceDTO::new)
                .collect(Collectors.toList());
    }
}
