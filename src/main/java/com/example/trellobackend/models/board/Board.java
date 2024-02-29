package com.example.trellobackend.models.board;

import com.example.trellobackend.models.workspace.Workspace;
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

    @ManyToOne
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    @ElementCollection
    private List<Long> columnOrderIds;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Columns> columns;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "board_visibilities",
            joinColumns = @JoinColumn(name = "board_id"),
            inverseJoinColumns = @JoinColumn(name = "visibility_id"))
    private Set<Visibility> visibilities = new HashSet<>();
}
