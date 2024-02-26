package com.example.trellobackend.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkspaceRequest {
    private String email;
    private String name;
    private String description;
}
