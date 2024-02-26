package com.example.trellobackend.services.impl;

import com.example.trellobackend.enums.UserRole;
import com.example.trellobackend.models.*;
import com.example.trellobackend.models.workspace.Workspace;
import com.example.trellobackend.models.workspace.Members;
import com.example.trellobackend.payload.request.WorkspaceRequest;
import com.example.trellobackend.repositories.*;
import com.example.trellobackend.services.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {
    @Autowired
    private WorkspaceRepository workspaceRepository;
    @Autowired
    private WorkspaceTypeRepository workspaceTypeRepository;
    @Autowired
    private WorkspacePermissionRepository workspacePermissionRepository;
    @Autowired
    private WorkspaceMemberRepository workspaceMemberRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public Iterable<Workspace> findAll() {
        return workspaceRepository.findAll();
    }
    @Override
    public Optional<Workspace> findById(Long id) {
        return workspaceRepository.findById(id);
    }
    @Override
    public Workspace save(Workspace workspace) {
        return workspaceRepository.save(workspace);
    }
    @Override
    public void delete(Long id) {
        workspaceRepository.deleteById(id);
    }

    @Override
    public Workspace createWorkspace(WorkspaceRequest workspaceRequest) {
//        WorkspaceType type = workspaceTypeRepository.getReferenceById(workspaceRequest.getTypeId());
//        WorkspacePermission permission = workspacePermissionRepository.getReferenceById(workspaceRequest.getPermissionId());
//        Workspace workspace = new Workspace();
//        workspace.setName(workspaceRequest.getName());
//        workspace.setDescription(workspaceRequest.getDescription());
//        workspace.setType(type);
//        workspace.setPermission(permission);
//        workspaceRepository.save(workspace);
//
//        addMemberToWorkspace(workspace,creator, UserRole.ROLE_ADMIN);
//        return workspace;
        Optional<User> userOptional = userRepository.findByEmail(workspaceRequest.getEmail());
        if (userOptional.isPresent()) {
            User creator = userOptional.get();
            Workspace workspace = new Workspace();
            workspace.setName(workspaceRequest.getName());
            workspace.setDescription(workspaceRequest.getDescription());



//            workspace.setType();
//            workspace.setPermission(permission);
            workspaceRepository.save(workspace);

            addMemberToWorkspace(workspace, creator, UserRole.ROLE_ADMIN);
            return workspace;
        }
        throw new UsernameNotFoundException("User not found");
    }

    public void addMemberToWorkspace(Workspace workspace, User user, UserRole userRole) {
        Members member = new Members();
        member.setWorkspace(workspace);
        member.setUser(user);
        workspaceMemberRepository.save(member);
    }
}