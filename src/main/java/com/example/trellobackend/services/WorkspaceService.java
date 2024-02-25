package com.example.trellobackend.services;

import com.example.trellobackend.models.Workspace;

import java.util.Optional;

public interface WorkspaceService {
    Iterable<Workspace> findAll();
    Optional<Workspace> findById(Long id);
    Workspace save(Workspace workspace);
    void delete(Long id);
}
