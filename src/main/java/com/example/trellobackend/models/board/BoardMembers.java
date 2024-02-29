package com.example.trellobackend.models.board;

import com.example.trellobackend.enums.UserRole;
import com.example.trellobackend.models.User;
import com.example.trellobackend.models.workspace.Workspace;
import jakarta.persistence.*;

@Entity
public class BoardMembers {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
//    @Enumerated(EnumType.STRING)
//    private UserRole role;
}
