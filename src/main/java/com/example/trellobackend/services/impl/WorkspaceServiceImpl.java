package com.example.trellobackend.services.impl;

import com.example.trellobackend.dto.UserDTO;
import com.example.trellobackend.dto.WorkspaceDTO;
import com.example.trellobackend.enums.MemberRole;
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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;
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


    public List<WorkspaceDTO> getAllWorkspaces() {
        return workspaceRepository.findAll().stream()
                .map(WorkspaceDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public Workspace createWorkspace(WorkspaceRequest workspaceRequest, String frontendURL) {
        Optional<User> userOptional = userRepository.findByEmail(workspaceRequest.getEmail());
        if (userOptional.isPresent()) {
            User creator = userOptional.get();
            Workspace workspace = new Workspace();
            workspace.setName(workspaceRequest.getName());
            workspace.setDescription(workspaceRequest.getDescription());
            workspace.setOwner(creator);

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
            String inviteCode = generateInviteCode();
            frontendURL = workspaceRequest.getFrontendURL(); // Lấy đường link của frontend từ request
            String inviteLink = frontendURL + "/w/" + workspace.getName() + inviteCode;
            workspace.setInviteCode(inviteCode);
            workspace.setInviteLink(inviteLink);
//            addMemberToWorkspace(workspace, creator, UserRole.ROLE_ADMIN);
            workspaceRepository.save(workspace);
            creator.getOwnedWorkspaces().add(workspace);
            return workspace;
        }
        throw new UsernameNotFoundException("User not found");
    }

    @Override
    public String inviteUserToWorkspace(String email, Workspace workspace) {
        String inviteCode = generateInviteCode();
        String inviteLink = workspace.getInviteLink() + "/" + inviteCode;
        return inviteLink;
    }


    @Override
    public Workspace getWorkspaceById(long workspaceId) {
        return workspaceRepository.findById(workspaceId);
    }


    private String generateInviteCode() {
        // Logic để tạo mã đặc biệt, có thể sử dụng UUID, mã ngẫu nhiên, hoặc bất kỳ phương pháp nào khác.
        return UUID.randomUUID().toString().substring(0, 8);
    }

    public void addMemberToWorkspace(Workspace workspace, User user, UserDTO userRole) {
        Members member = new Members();
        member.setWorkspace(workspace);
        member.setUser(user);
//        member.setRole(userRole);
        workspaceMemberRepository.save(member);
    }

//    public List<UserDTO> getWorkspaceMembers(Long workspaceId) {
//        Optional<Workspace> workspaceOptional = workspaceRepository.findById(workspaceId);
//
//        if (workspaceOptional.isPresent()) {
//            Workspace workspace = workspaceOptional.get();
//            Set<User> members = workspace.getMembers();
//
//            // Chuyển đổi từ User Entity sang UserDTO
//            List<UserDTO> membersDTO = members.stream()
//                    .map(user -> modelMapper.map(user, UserDTO.class))
//                    .collect(Collectors.toList());
//
//            return membersDTO;
//        }
//
//        // Nếu không tìm thấy Workspace, có thể xử lý theo ý muốn của bạn
//        throw new RuntimeException("Workspace not found");
//    }


}
