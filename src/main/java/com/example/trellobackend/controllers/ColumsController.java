package com.example.trellobackend.controllers;

import com.example.trellobackend.dto.BoardResponseDTO;
import com.example.trellobackend.dto.ColumnsDTO;
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
import java.util.NoSuchElementException;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/columns")
public class ColumsController {
    @Autowired
    private ColumnsService columnsService;
    @Autowired
    private ColumnsRepository columnsRepository;

    @GetMapping("{id}")
//    public ResponseEntity<?> getColumsById(@PathVariable Long id){
//        Optional<Columns> columns = columnsRepository.findById(id);
//        return new ResponseEntity<>(columns, HttpStatus.OK);
//    }
    public ColumnsDTO getColumnsById(@PathVariable Long id) {
        ColumnsDTO columnsDTO = new ColumnsDTO();
        Optional<Columns> columnOptional = columnsRepository.findById(id);
        if (columnOptional.isPresent()) {
            Columns columns = columnOptional.get();
            columnsDTO.setId(columns.getId());
            columnsDTO.setTitle(columns.getTitle());
            return columnsDTO;
        } else {
            throw new NoSuchElementException("Column not found");
        }
    }


    @PostMapping("/create")
    public ResponseEntity<BoardResponseDTO> createColumn(@RequestBody ColumnRequest columnRequest) {
        try {
            BoardResponseDTO responseDTO = columnsService.createNewColumn(columnRequest);
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<ColumnsDTO>> getAllWorkspaces() {
        List<ColumnsDTO> list = columnsService.getAllColumns();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
