package com.example.trellobackend.services;

<<<<<<< HEAD
import com.example.trellobackend.models.workspace.Workspace;
=======
>>>>>>> parent of fad964f (recreate api create workspace. need to add type and permission)

import java.util.Optional;

public interface WorkspaceService {
    Iterable<Workspace> findAll();
    Optional<Workspace> findById(Long id);
    Workspace save(Workspace workspace);
    void delete(Long id);
}
