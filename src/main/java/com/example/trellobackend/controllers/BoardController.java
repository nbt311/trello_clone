package com.example.trellobackend.controllers;

import com.example.trellobackend.dto.BoardResponseDTO;
import com.example.trellobackend.dto.ColumnsDTO;
import com.example.trellobackend.dto.DragAndDropDTO;
import com.example.trellobackend.dto.UpdateBoardDTO;
import com.example.trellobackend.models.board.Board;
import com.example.trellobackend.models.board.Visibility;
import com.example.trellobackend.models.workspace.Workspace;
import com.example.trellobackend.payload.request.BoardRequest;
import com.example.trellobackend.payload.response.MessageResponse;
import com.example.trellobackend.repositories.BoardRepository;
import com.example.trellobackend.repositories.VisibilityRepository;
import com.example.trellobackend.services.impl.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/boards")
public class BoardController {
    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private VisibilityRepository visibilityRepository;

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponseDTO> getBoardById(@PathVariable Long boardId) {
        try {
            BoardResponseDTO responseDTO = boardService.getBoardById(boardId);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<BoardResponseDTO> updateBoardById(@PathVariable Long boardId,
                                                            @RequestBody UpdateBoardDTO updateData) {
        try {
            BoardResponseDTO responseDTO = boardService.updateBoardColumnOrderIds(boardId, updateData);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<BoardResponseDTO> createBoard(@RequestBody BoardRequest boardRequest) {
        try {
            BoardResponseDTO responseDTO = boardService.createNewBoard(boardRequest);
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{boardId}/columns")
    public ResponseEntity<List<ColumnsDTO>> getAllColumnsByBoardId(@PathVariable Long boardId) {
        try {
            List<ColumnsDTO> columnsDTOList = boardService.getAllColumnsDTOByBoardId(boardId);
            return ResponseEntity.ok(columnsDTOList);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/visibility")
    public List<Visibility> getAllVisibilities(){
        return  visibilityRepository.findAll();
    }

}
