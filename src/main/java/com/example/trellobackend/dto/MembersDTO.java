package com.example.trellobackend.dto;

import com.example.trellobackend.enums.MemberRole;
import com.example.trellobackend.models.User;
import com.example.trellobackend.models.workspace.Workspace;
import com.example.trellobackend.models.workspace.WorkspaceMembers;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class MembersDTO {
    private Long userId;
    private String username;
    private String email;
    private String avatarUrl;
    private MemberRole memberRole;
    private WorkspaceDTO workspaceDTO;
    public MembersDTO(User user, MemberRole memberRole) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.avatarUrl = user.getAvatarUrl();
        this.memberRole = memberRole;
    }

    public MembersDTO(WorkspaceMembers workspaceMembers) {
        this(workspaceMembers.getUser(), workspaceMembers.getRole());

        Workspace workspace = workspaceMembers.getWorkspace();
        if (workspace != null) {
            this.workspaceDTO = new WorkspaceDTO (workspace);
        }
    }
}
