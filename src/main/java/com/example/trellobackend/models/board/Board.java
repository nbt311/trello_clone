package com.example.trellobackend.models.board;

import com.example.trellobackend.models.workspace.Workspace;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "board")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(max = 20)
    private String title;

    @ManyToMany
    @JoinTable( name = "board_visibilities",
                joinColumns = @JoinColumn(name = "board_id"),
                inverseJoinColumns = @JoinColumn(name = "visibility_id"))
    private Set<Visibility> types= new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;
}
