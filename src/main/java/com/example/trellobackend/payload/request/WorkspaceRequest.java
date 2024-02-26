package com.example.trellobackend.payload.request;

import lombok.*;

import java.util.Set;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WorkspaceRequest {
    private String email;
    private String name;
    private String description;
    private Set<String> type;
    private Set<String> permission;
}
