package com.example.trellobackend.serviceImpls;

import com.example.trellobackend.models.Workspace;
import com.example.trellobackend.repositories.WorkspaceRepository;
import com.example.trellobackend.services.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {
    @Autowired
    private WorkspaceRepository workspaceRepository;
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
}
