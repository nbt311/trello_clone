package com.example.trellobackend.models.board;

import com.example.trellobackend.enums.EBoardPermission;
import jakarta.persistence.*;

@Entity
public class BoardPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EBoardPermission name;
}
