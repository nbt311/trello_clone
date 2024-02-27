package com.example.trellobackend.services;

import com.example.trellobackend.models.workspace.Members;

public interface IWorkspaceMemberService extends IGeneralService<Members>{
    Iterable<Members> findAllByWorkspace(Long workspace_id);
}
