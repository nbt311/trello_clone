package com.example.trellobackend.services.impl;

import com.example.trellobackend.dto.BoardResponseDTO;
import com.example.trellobackend.dto.ColumnsDTO;
import com.example.trellobackend.dto.UserDTO;
import com.example.trellobackend.enums.EBoardVisibility;
import com.example.trellobackend.enums.UserRole;
import com.example.trellobackend.models.Role;
import com.example.trellobackend.models.User;
import com.example.trellobackend.models.board.Board;
import com.example.trellobackend.models.board.BoardMembers;
import com.example.trellobackend.models.board.Visibility;
import com.example.trellobackend.models.workspace.Workspace;
import com.example.trellobackend.payload.request.BoardRequest;
import com.example.trellobackend.repositories.*;
import com.example.trellobackend.services.IBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ModelMapper modelMapper;

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


//    @Override
//    public Board createBoard(BoardRequest boardRequest) {
//        Optional<User> userOptional = userRepository.findByEmail(boardRequest.getEmail());
//        if (userOptional.isPresent()) {
//            User creator = userOptional.get();
//            Optional<Workspace> workspaceOptional = workspaceRepository.findById(boardRequest.getWorkspaceId());
//            if (workspaceOptional.isPresent()){
//                Workspace workspace = workspaceOptional.get();
//                Board board = new Board();
//                board.setTitle(boardRequest.getTitle());
//                board.setWorkspace(workspace);
//                Set<String> strVisibilities = boardRequest.getVisibility();
//                Set<Visibility> visibilities = new HashSet<>();
//                strVisibilities.forEach(visibility -> {
//                    switch (visibility) {
//                        case "private":
//                            Visibility privateVisibility = visibilityRepository.findByName(EBoardVisibility.PRIVATE)
//                                    .orElseThrow(() -> new RuntimeException("Error: Visibility is not found."));
//                            visibilities.add(privateVisibility);
//
//                            break;
//                        case "public":
//                            Visibility publicVisibility = visibilityRepository.findByName(EBoardVisibility.PUBLIC)
//                                    .orElseThrow(() -> new RuntimeException("Error: Visibility is not found."));
//                            visibilities.add(publicVisibility);
//
//                            break;
//                        default:
//                            Visibility workspaceVisibility = visibilityRepository.findByName(EBoardVisibility.WORKSPACE)
//                                    .orElseThrow(() -> new RuntimeException("Error: Visibility is not found."));
//                            visibilities.add(workspaceVisibility);
//                    }
//                });
//                board.setVisibilities(visibilities);
//                boardRepository.save(board);
//                addMemberToBoard(board,creator,UserRole.ROLE_ADMIN);
//                return board;
//            }
//        }
//        throw new UsernameNotFoundException("User not found");
//    }

    @Override
    public BoardResponseDTO getBoardById(Long boardId) {
        Optional<Board> boardOptional = boardRepository.findById(boardId);
        if (boardOptional.isPresent()) {
            Board board = boardOptional.get();

            // Fetch Columns associated with the Board
            List<ColumnsDTO> columnsDTOList = board.getColumns()
                    .stream()
                    .map(column -> {
                        ColumnsDTO columnsDTO = new ColumnsDTO();
                        columnsDTO.setId(column.getId());
                        columnsDTO.setTitle(column.getTitle());
                        // Map other properties as needed
                        return columnsDTO;
                    })
                    .collect(Collectors.toList());

            BoardResponseDTO responseDTO = BoardResponseDTO.fromEntity(board);
            responseDTO.setColumns(columnsDTOList);
            responseDTO.setColumnIds(board.getColumnOrderIds());

            return responseDTO;
        }
        throw new NoSuchElementException("Board not found");
    }

    @Override
    public BoardResponseDTO createNewBoard(BoardRequest boardRequest) {
        Optional<User> userOptional = userRepository.findByEmail(boardRequest.getEmail());
        if (userOptional.isPresent()) {
            User creator = userOptional.get();
            Optional<Workspace> workspaceOptional = workspaceRepository.findById(boardRequest.getWorkspaceId());
            if (workspaceOptional.isPresent()) {
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
                addMemberToBoard(board, creator, UserRole.ROLE_ADMIN);

                // Create a response DTO
                BoardResponseDTO responseDTO = new BoardResponseDTO();
                responseDTO.setId(board.getId());
                responseDTO.setTitle(board.getTitle());
                responseDTO.setColumns(Collections.emptyList()); // Initialize the list of Columns
                responseDTO.setColumnIds(Collections.emptyList()); // Initialize the columnIds list

                return responseDTO;
            }
        }
        throw new UsernameNotFoundException("User not found");
    }

    public void addMemberToBoard(Board board, User user, UserRole userRole) {
        BoardMembers boardMembers = new BoardMembers();
        boardMembers.setBoard(board);
        boardMembers.setUser(user);
        boardMembers.setRole(userRole);
        boardMembersRepository.save(boardMembers);
    }

    public List<UserDTO> getBoardMembers(Long boardId){
        Optional<Board> boardOptional = boardRepository.findById(boardId);
        if(boardOptional.isPresent()){
            Board board = boardOptional.get();
            Set<User> boardMembers = board.getBoardMembers();

            List<UserDTO> boardMembersDTO = boardMembers.stream()
                    .map(user -> modelMapper.map(user, UserDTO.class))
                    .collect(Collectors.toList());
            return boardMembersDTO;
        }
        throw new RuntimeException("Board not found");
    }

    public void addUserToBoardByEmail(Long boardId, String userEmail){
        Board board = boardRepository. findById(boardId).orElseThrow(() -> new RuntimeException("Board not found"));
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        board.getBoardMembers().add(user);
        boardRepository.save(board);
    }

//    public List<UserDTO> getBoardMembersByUserRole(Long boardId, UserRole roleName){
//        Optional<Board> boardOptional = boardRepository.findById(boardId);
//        if (boardOptional.isPresent()){
//            Board board = boardOptional.get();
//            Role role = roleRepository.findByName(roleName).orElseThrow(() -> new RuntimeException("Role not found"));
//            List<User> userListByRole = userRepository.findByRoles(role);
//            List<UserDTO> userDTOList = new ArrayList<>();
//
//        }
//    }

    public void deleteMemberFromBoard(Long boardId, User user){
        Optional<Board> boardOptional = boardRepository.findById(boardId);
        if (boardOptional.isPresent()){
            Board board = boardOptional.get();
            if (board.getBoardMembers().contains(user)){
                board.getBoardMembers().remove(user);
                boardRepository.save(board);
            }
        } else {
            throw  new RuntimeException("Board not found");
        }
    }
}
