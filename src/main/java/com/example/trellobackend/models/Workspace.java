package com.example.trellobackend.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "workspace")
public class Workspace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

//    @ManyToOne
//    @JoinColumn(name = "type_id")
//    private WorkspaceType type;

    private String description;

//    @ManyToOne
//    @JoinColumn(name = "permission_id")
//    private WorkspacePermission permission;
}
