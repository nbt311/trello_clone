package com.example.trellobackend.services.impl;

import com.example.trellobackend.dto.MembersDTO;
import com.example.trellobackend.dto.UserDTO;
import com.example.trellobackend.dto.WorkspaceDTO;
import com.example.trellobackend.enums.MemberRole;
import com.example.trellobackend.enums.WorkSpacePermission;
import com.example.trellobackend.enums.WorkSpaceType;
import com.example.trellobackend.models.*;
import com.example.trellobackend.models.workspace.Permission;
import com.example.trellobackend.models.workspace.Type;
import com.example.trellobackend.models.workspace.Workspace;
import com.example.trellobackend.models.workspace.WorkspaceMembers;
import com.example.trellobackend.payload.request.WorkspaceRequest;
import com.example.trellobackend.repositories.*;
import com.example.trellobackend.services.WorkspaceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
    private WorkspaceMembersRepository workspaceMembersRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserService userService;
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
        return null;
    }

    @Override
    public WorkspaceDTO createWorkspace(WorkspaceRequest workspaceRequest, String frontendURL) {
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
                        case "OPERATION":
                            Type operationType = workspaceTypeRepository.findByName(WorkSpaceType.OPERATION)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            types.add(operationType);

                            break;
                        case "MARKETING":
                            Type marketingType = workspaceTypeRepository.findByName(WorkSpaceType.MARKETING)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            types.add(marketingType);

                            break;
                        case "OTHER":
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
            workspaceRepository.save(workspace);
            addMemberToWorkspace(MemberRole.ADMIN, creator, workspace);
            WorkspaceDTO responseDTO = new WorkspaceDTO();
            responseDTO.setId(workspace.getId());
            responseDTO.setName(workspace.getName());
            responseDTO.setMembers(workspace.getWorkspaceMembers().stream()
                    .map(MembersDTO::new)
                    .collect(Collectors.toList()));
            responseDTO.setBoards(Collections.emptyList());

            return responseDTO;
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

    @Override
    public List<WorkspaceDTO> getAllWorkspaceByUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            return null;
        }else {
            throw  new RuntimeException("User not found");
        }

    }


    private String generateInviteCode() {
        // Logic để tạo mã đặc biệt, có thể sử dụng UUID, mã ngẫu nhiên, hoặc bất kỳ phương pháp nào khác.
        return UUID.randomUUID().toString().substring(0, 8);
    }

    public void addMemberToWorkspace(MemberRole memberRole, User user,Workspace workspace ) {
        WorkspaceMembers members = new WorkspaceMembers();
        members.setWorkspace(workspace);
        members.setUser(user);
        members.setRole(memberRole);
        workspace.getWorkspaceMembers().add(members);
        workspaceMembersRepository.save(members);
    }

    public List<MembersDTO> getWorkspaceMembers(Long workspaceId) {
        Optional<Workspace> workspaceOptional = workspaceRepository.findById(workspaceId);

        if (workspaceOptional.isPresent()) {
            Workspace workspace = workspaceOptional.get();

            List<WorkspaceMembers> workspaceMembersList = new ArrayList<>(workspace.getWorkspaceMembers());

            List<MembersDTO> membersDTOs = workspaceMembersList.stream()
                    .map(workspaceMembers -> new MembersDTO(workspaceMembers.getUser(), workspaceMembers.getRole()))
                    .collect(Collectors.toList());

            return membersDTOs;
        }

        // Nếu không tìm thấy Workspace, có thể xử lý theo ý muốn của bạn
        throw new RuntimeException("Workspace not found");
    }


}
