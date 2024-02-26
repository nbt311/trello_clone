package com.example.trellobackend.models.workspace;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

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

@ManyToMany
@JoinTable(name = "workspace_types",
        joinColumns = @JoinColumn(name = "workspace_id"),
        inverseJoinColumns = @JoinColumn(name = "type_id"))
private Set<Type> types = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "workspace_permissions",
            joinColumns = @JoinColumn(name = "workspace_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Set<Permission> permissions = new HashSet<>();

}
