package com.example.trellobackend.dto;

import com.example.trellobackend.enums.MemberRole;
import com.example.trellobackend.models.User;
import com.example.trellobackend.models.workspace.Workspace;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WorkspaceDTO {
    private Long id;
    private String name;
    private List<MembersDTO> members;
    private List<BoardResponseDTO> boards;
    public WorkspaceDTO(Workspace workspace) {
        this.id = workspace.getId();
        this.name = workspace.getName();
        this.members = (workspace.getWorkspaceMembers()).stream()
                .map(workspaceMembers -> {
                    User user = workspaceMembers.getUser();
                    MemberRole role = workspaceMembers.getRole();
                    return new MembersDTO(user, role);
                })
                .collect(Collectors.toList());

        this.boards = (workspace.getBoards() != null) ?
                workspace.getBoards().stream()
                        .map(BoardResponseDTO::fromEntity)
                        .collect(Collectors.toList()) :
                Collections.emptyList();
    }
}
