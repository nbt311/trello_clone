package com.example.trellobackend.models.board;

import com.example.trellobackend.models.workspace.Permission;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    private String title;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "board_permissions",
            joinColumns = @JoinColumn(name = "board_id"),
            inverseJoinColumns = @JoinColumn(name = "board_permission_id"))
    private Set<BoardPermission> permissions = new HashSet<>();
}
