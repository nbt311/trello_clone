package com.example.trellobackend.models.board;

import com.example.trellobackend.enums.EBoardMemberRole;
import com.example.trellobackend.models.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
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
<<<<<<< HEAD
//    @Enumerated(EnumType.STRING)
//    private UserRole role;
=======

    @Enumerated(EnumType.STRING)
    private EBoardMemberRole role;
>>>>>>> origin/api/show-all-members
}
