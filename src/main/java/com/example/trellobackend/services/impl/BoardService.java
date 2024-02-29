package com.example.trellobackend.services.impl;

import com.example.trellobackend.enums.EBoardMemberRole;
import com.example.trellobackend.enums.EBoardVisibility;
import com.example.trellobackend.enums.UserRole;
import com.example.trellobackend.enums.WorkSpaceType;
import com.example.trellobackend.models.User;
import com.example.trellobackend.models.board.Board;
import com.example.trellobackend.models.board.BoardMembers;
import com.example.trellobackend.models.board.Visibility;
import com.example.trellobackend.models.workspace.Members;
import com.example.trellobackend.models.workspace.Type;
import com.example.trellobackend.models.workspace.Workspace;
import com.example.trellobackend.payload.request.BoardRequest;
import com.example.trellobackend.repositories.*;
import com.example.trellobackend.services.IBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class BoardService implements IBoardService {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VisibilityRepository visibilityRepository;
    @Autowired
    private BoardMembersRepository boardMembersRepository;
    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Override
    public Iterable<Board> findAll() {
        return null;
    }

    @Override
    public Optional<Board> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void save(Board board) {

    }

    @Override
    public void remove(Long id) {

    }


    @Override
    public Board createBoard(BoardRequest boardRequest) {
        Optional<User> userOptional = userRepository.findByEmail(boardRequest.getEmail());
        if (userOptional.isPresent()) {
            User creator = userOptional.get();
            Optional<Workspace> workspaceOptional = workspaceRepository.findById(boardRequest.getWorkspaceId());
            if (workspaceOptional.isPresent()){
                Workspace workspace = workspaceOptional.get();
                Board board = new Board();
                board.setTitle(boardRequest.getTitle());
                board.setWorkspace(workspace);
                Set<String> strVisibilities = boardRequest.getVisibility();
                Set<Visibility> visibilities = new HashSet<>();
                strVisibilities.forEach(visibility -> {
                    switch (visibility) {
                        case "private":
                            Visibility privateVisibility = visibilityRepository.findByName(EBoardVisibility.PRIVATE)
                                    .orElseThrow(() -> new RuntimeException("Error: Visibility is not found."));
                            visibilities.add(privateVisibility);

                            break;
                        case "public":
                            Visibility publicVisibility = visibilityRepository.findByName(EBoardVisibility.PUBLIC)
                                    .orElseThrow(() -> new RuntimeException("Error: Visibility is not found."));
                            visibilities.add(publicVisibility);

                            break;
                        default:
                            Visibility workspaceVisibility = visibilityRepository.findByName(EBoardVisibility.WORKSPACE)
                                    .orElseThrow(() -> new RuntimeException("Error: Visibility is not found."));
                            visibilities.add(workspaceVisibility);
                    }
                });
                board.setVisibilities(visibilities);
                boardRepository.save(board);
                addMemberToBoard(board,creator,EBoardMemberRole.ADMIN);
                return board;
            }
        }
        throw new UsernameNotFoundException("User not found");
    }

    public void addMemberToBoard(Board board, User user, EBoardMemberRole eBoardMemberRole) {
        BoardMembers boardMembers = new BoardMembers();
        boardMembers.setBoard(board);
        boardMembers.setUser(user);
        boardMembers.setRole(eBoardMemberRole);
        boardMembersRepository.save(boardMembers);
    }
}
