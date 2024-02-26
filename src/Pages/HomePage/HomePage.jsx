import React, {useEffect, useState} from 'react';
import HomeHeader from "../../Components/HomeHeader/HomeHeader";
import Sidebar from "../../Components/SideBar/Sidebar";
import {useDisclosure} from "@chakra-ui/react";
import CreateWorkspaceModal from "../../Components/WorkspaceModal/CreateWorkspaceModal";
import axios from "axios";

const HomePage = () => {
    const {isOpen, onOpen, onClose} = useDisclosure()
    const [workspaceName, setWorkspaceName] = useState("");
    const [workspaceType, setWorkspaceType] = useState("");
    const [workspaceDescription, setWorkspaceDescription] = useState("");
    const [workspace, setWorkspace] = useState([]);

    useEffect( () => {
         axios.get('http://localhost:8080/api/workspaces').then((response) => {
            // localStorage.setItem("workspace", JSON.stringify(response.data));
            setWorkspace(response.data);
        })
    }, []);

    return (
        <div>
            <div className='border border-1-slate-500 py-1'>
                <HomeHeader onOpen={onOpen} onClose={onClose}/>
            </div>

            <div className='w-[20%] ml-20'>
                <Sidebar workspace={workspace} setWorkspace={setWorkspace}/>
            </div>

            <CreateWorkspaceModal onOpen={onOpen} isOpen={isOpen} onClose={onClose}
                                  workspaceName={workspaceName} setWorkspaceName={setWorkspaceName}
                                  workspaceType={workspaceType} setWorkspaceType={setWorkspaceType}
                                  workspaceDescription={workspaceDescription}
                                  setWorkspaceDescription={setWorkspaceDescription}
            />
        </div>
    );
};

export default HomePage;