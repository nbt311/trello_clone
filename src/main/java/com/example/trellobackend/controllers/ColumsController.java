package com.example.trellobackend.controllers;

import com.example.trellobackend.dto.BoardResponseDTO;
import com.example.trellobackend.dto.CardDTO;
import com.example.trellobackend.dto.ColumnsDTO;
import com.example.trellobackend.dto.WorkspaceDTO;
import com.example.trellobackend.models.board.Board;
import com.example.trellobackend.models.board.Columns;
import com.example.trellobackend.models.workspace.Workspace;
import com.example.trellobackend.payload.request.ColumnRequest;
import com.example.trellobackend.payload.response.MessageResponse;
import com.example.trellobackend.repositories.BoardRepository;
import com.example.trellobackend.repositories.ColumnsRepository;
import com.example.trellobackend.services.impl.BoardService;
import com.example.trellobackend.services.impl.ColumnsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/columns")
public class ColumsController {
    @Autowired
    private ColumnsService columnsService;
    @Autowired
    private ColumnsRepository columnsRepository;
@GetMapping("/{id}")
public ResponseEntity<ColumnsDTO>  getColumnById(@PathVariable Long id) {
    try {
        Optional<Columns> columnsOptional = columnsRepository.findById(id);
        if (columnsOptional.isPresent()){
            Columns columns = columnsOptional.get();
            ColumnsDTO columnsDTO = new ColumnsDTO();
            columnsDTO.setId(columns.getId());
            columnsDTO.setTitle(columns.getTitle());
            return new ResponseEntity<>(columnsDTO, HttpStatus.OK);
        }else {
            throw new RuntimeException( "Column not found");
        }
    } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

    @PostMapping("/create")
    public ResponseEntity<?> createColumn(@RequestBody ColumnRequest columnRequest) {
        try {
            BoardResponseDTO responseDTO = columnsService.createNewColumn(columnRequest);
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace(); // In thông tin lỗi ra console (hoặc sử dụng Logger)
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<ColumnsDTO>> getAllWorkspaces() {
        List<ColumnsDTO> list = columnsService.getAllColumns();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @DeleteMapping("/{columnId}/delete")
    public ResponseEntity<String> deleteColumn(@PathVariable Long columnId) {
        try {
            columnsService.remove(columnId);
            return ResponseEntity.ok("Delete Column Succesfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Not found Column with ID: " + columnId);
        }
    }
    @GetMapping("{columnId}/cards")
    public ResponseEntity<List<CardDTO>> getAllColumnsByBoardId(@PathVariable Long columnId) {
        try {
            List<CardDTO> cardDTOList = columnsService.getAllCardDTOByBoardId(columnId);
            return ResponseEntity.ok(cardDTOList);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
