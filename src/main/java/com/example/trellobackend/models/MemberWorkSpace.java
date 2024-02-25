package com.example.trellobackend.models;

import com.example.trellobackend.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class MemberWorkSpace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "workspace_id")
    private WorkSpace workspace;
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
