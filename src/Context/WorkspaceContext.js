import { createContext, useState } from 'react';

const WorkspaceContext = createContext();

export const WorkspaceProvider = ({ children }) => {
    const [workspace, setWorkspace] = useState(null);

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