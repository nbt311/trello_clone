package com.example.trellobackend.payload.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkspaceRequest {
    private String name;
    private String type;
    private String description;
    private String permission;
}
