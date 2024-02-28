package com.example.trellobackend.models.board;

import com.example.trellobackend.models.User;
import com.example.trellobackend.models.workspace.Workspace;
import jakarta.persistence.*;

@Entity
public class WorkspaceBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;
}
