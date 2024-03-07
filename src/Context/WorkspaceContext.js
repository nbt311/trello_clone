import { createContext, useState } from 'react';

const WorkspaceContext = createContext();

export const WorkspaceProvider = ({ children }) => {
    const [workspace, setWorkspace] = useState(() => {
        return JSON.parse(localStorage.getItem('workspacelist'))
    });

    const updateWorkspace = (newWorkspace) => {
        setWorkspace(newWorkspace);
    };

    return (
        <WorkspaceContext.Provider value={{ workspace, updateWorkspace }}>
            {children}
        </WorkspaceContext.Provider>
    );
};

export default WorkspaceContext;