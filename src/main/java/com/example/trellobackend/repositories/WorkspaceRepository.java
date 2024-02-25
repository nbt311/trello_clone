package com.example.trellobackend.repositories;

import com.example.trellobackend.models.WorkSpace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspaceRepository extends JpaRepository<WorkSpace,Long> {

}
