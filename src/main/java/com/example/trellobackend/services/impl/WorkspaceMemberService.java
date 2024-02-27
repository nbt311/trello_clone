package com.example.trellobackend.services.impl;

import com.example.trellobackend.models.workspace.Members;
import com.example.trellobackend.repositories.WorkspaceMemberRepository;
import com.example.trellobackend.services.IWorkspaceMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WorkspaceMemberService implements IWorkspaceMemberService {
    @Autowired
    private WorkspaceMemberRepository workspaceMemberRepository;

    @Override
    public Iterable<Members> findAll() {
        return workspaceMemberRepository.findAll();
    }

    @Override
    public Optional<Members> findById(Long id) {
        return workspaceMemberRepository.findById(id);
    }

    @Override
    public void save(Members members) {
        workspaceMemberRepository.save(members);
    }

    @Override
    public void remove(Long id) {
        workspaceMemberRepository.deleteById(id);
    }

    @Override
    public Iterable<Members> findAllByWorkspace(Long workspace_id) {
        return workspaceMemberRepository.findMembersByWorkspace_Id(workspace_id);
    }
}
