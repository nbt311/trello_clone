package com.example.trellobackend.dto;

import com.example.trellobackend.enums.MemberRole;
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
    private String avatarUrl;
    private List<WorkspaceDTO> ownedWorkspaces;
    private List<WorkspaceDTO> memberWorkspaces;

    public UserDTO(User user,List<WorkspaceDTO> ownedWorkspaces,List<WorkspaceDTO> memberWorkspaces) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        if (user.getAvatarUrl() != null) {
            this.avatarUrl = user.getAvatarUrl();
        }
        this.ownedWorkspaces = ownedWorkspaces;
        this.memberWorkspaces = memberWorkspaces;
    }
    public UserDTO(Long id, String username, String email, String avatarUrl){
        this.id = id;
        this.username = username;
        this.email = email;
        this.avatarUrl = avatarUrl;
    }
}
