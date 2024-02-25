package com.example.trellobackend.models;

import com.example.trellobackend.enums.WorkSpacePermission;
import com.example.trellobackend.enums.WorkSpaceType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class WorkSpace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private WorkSpaceType type;
    private String description;
    @Enumerated(EnumType.STRING)
    private WorkSpacePermission permission;
    @OneToMany(mappedBy = "workspace")
    private List<MemberWorkSpace> memberWorkSpaces;

}
