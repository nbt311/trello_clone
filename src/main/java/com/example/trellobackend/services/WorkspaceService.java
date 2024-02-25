package com.example.trellobackend.services;

import com.example.trellobackend.models.User;
import com.example.trellobackend.models.WorkSpace;
import com.example.trellobackend.payload.request.WorkspaceRequest;

public interface WorkspaceService {
    WorkSpace createWorkspace(WorkspaceRequest workspaceRequest, User creator);

    void inviteUserToWorkspace(User inviter, User invitedUser, WorkSpace workspace);


}
