package com.example.trellobackend.dto;

import com.example.trellobackend.enums.UserRole;
import com.example.trellobackend.models.workspace.Members;

public class MemberDTO {
    private Long userId;
    private String userName;
    private String avatarUrl;
    private UserRole role;

    public MemberDTO(Members members){
        this.userId = members.getUser().getId();
        this.userName = members.getUser().getUsername();
        this.avatarUrl = members.getUser().getAvatarUrl();
        this.role = members.getRole();
    }
}
