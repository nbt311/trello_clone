package com.example.trellobackend.services.impl;

import com.example.trellobackend.enums.UserRole;
import com.example.trellobackend.enums.WorkSpacePermission;
import com.example.trellobackend.enums.WorkSpaceType;
import com.example.trellobackend.models.*;
import com.example.trellobackend.models.workspace.Permission;
import com.example.trellobackend.models.workspace.Type;
import com.example.trellobackend.models.workspace.Workspace;
import com.example.trellobackend.models.workspace.Members;
import com.example.trellobackend.payload.request.WorkspaceRequest;
import com.example.trellobackend.repositories.*;
import com.example.trellobackend.services.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
        Optional<User> userOptional = userRepository.findByEmail(workspaceRequest.getEmail());
        if (userOptional.isPresent()) {
            User creator = userOptional.get();
            Workspace workspace = new Workspace();
            workspace.setName(workspaceRequest.getName());
            workspace.setDescription(workspaceRequest.getDescription());

            Set<String> strPermissions = workspaceRequest.getPermission();
            Set<Permission> permissions = new HashSet<>();

            if (strPermissions == null) {
                Permission permission = workspacePermissionRepository.findByName(WorkSpacePermission.PRIVATE)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                permissions.add(permission);
            }

            workspace.setPermissions(permissions);

            Set<String> strTypes = workspaceRequest.getType();
            Set<Type> types = new HashSet<>();

            if (strTypes == null) {
                Type type = workspaceTypeRepository.findByName(WorkSpaceType.EDUCATION)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                types.add(type);
            } else {
                strTypes.forEach(type -> {
                    switch (type) {
                        case "operation":
                            Type operationType = workspaceTypeRepository.findByName(WorkSpaceType.OPERATION)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            types.add(operationType);

                            break;
                        case "marketing":
                            Type marketingType = workspaceTypeRepository.findByName(WorkSpaceType.MARKETING)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            types.add(marketingType);

                            break;
                        case "other":
                            Type otherType = workspaceTypeRepository.findByName(WorkSpaceType.OTHER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            types.add(otherType);

                            break;
                        default:
                            Type educationType = workspaceTypeRepository.findByName(WorkSpaceType.EDUCATION)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            types.add(educationType);
                    }
                });
            }
            workspace.setTypes(types);
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
        member.setRole(userRole);
        workspaceMemberRepository.save(member);
    }
}