package com.example.trellobackend.dto;

import com.example.trellobackend.enums.MemberRole;
import com.example.trellobackend.models.User;
import com.example.trellobackend.models.board.Board;
import com.example.trellobackend.models.board.BoardMembers;
import com.example.trellobackend.models.workspace.Workspace;
import com.example.trellobackend.models.workspace.WorkspaceMembers;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardMemberDTO {
    private Long userId;
    private String username;
    private String email;
    private String avatarUrl;
    private MemberRole memberRole;
    private BoardResponseDTO boardResponseDTO;

    public BoardMemberDTO(User user, MemberRole memberRole) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.avatarUrl = user.getAvatarUrl();
        this.memberRole = memberRole;
    }
    public BoardMemberDTO(BoardMembers boardMembers) {
        this(boardMembers.getUser(), boardMembers.getRole());

        Board board = boardMembers.getBoard();
        if (board != null) {
            this.boardResponseDTO = BoardResponseDTO.fromEntity(board);
        }
    }
}
