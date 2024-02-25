package com.example.trellobackend.serviceImpls;

import com.example.trellobackend.enums.UserRole;
import com.example.trellobackend.enums.WorkSpacePermission;
import com.example.trellobackend.enums.WorkSpaceType;
import com.example.trellobackend.models.MemberWorkSpace;
import com.example.trellobackend.models.User;
import com.example.trellobackend.models.WorkSpace;
import com.example.trellobackend.payload.request.WorkspaceRequest;
import com.example.trellobackend.repositories.MemberWorkSpaceReporisoty;
import com.example.trellobackend.repositories.UserRepository;
import com.example.trellobackend.repositories.WorkspaceRepository;
import com.example.trellobackend.services.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WorkspaceRepository workspaceRepository;
    @Autowired
    private MemberWorkSpaceReporisoty memberWorkSpaceRepository;
    @Override
    public WorkSpace createWorkspace(WorkspaceRequest workspaceRequest, User creator) {
        WorkSpace workSpace = new WorkSpace();
        workSpace.setName(workspaceRequest.getName());
        workSpace.setType(WorkSpaceType.valueOf(workspaceRequest.getType()));
        workSpace.setDescription(workSpace.getDescription());
        workSpace.setPermission(WorkSpacePermission.valueOf(workspaceRequest.getPermission()));
        workspaceRepository.save(workSpace);

        addUserToWorkspaceWithRole(creator,workSpace, UserRole.ROLE_ADMIN);
        return workSpace;
    }

    private void addUserToWorkspaceWithRole(User user, WorkSpace workSpace, UserRole role) {
        MemberWorkSpace memberWorkSpace = new MemberWorkSpace();
        memberWorkSpace.setUser(user);
        memberWorkSpace.setWorkspace(workSpace);
        memberWorkSpace.setRole(role);
        memberWorkSpaceRepository.save(memberWorkSpace);
    }

    @Override
    public void inviteUserToWorkspace(User inviter, User invitedUser, WorkSpace workspace) {

    }
}
