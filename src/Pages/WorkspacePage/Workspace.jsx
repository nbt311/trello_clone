import React, {useEffect, useState} from 'react';
import HomeHeader from "../../Components/HomeHeader/HomeHeader";
import WorkspaceSidebar from "../../Components/SideBar/WorkspaceSidebar";
import SidebarConfig from "../../Components/SideBar/SidebarConfig";
import WorkspaceMembers from "../../Components/Workspace/WorkspaceMembers";
import axios from "axios";
import {useParams} from "react-router-dom";


const Workspace = () => {
    const {id, name} = useParams();
    const [workspaceData, setWorkspaceData] = useState(null);

    useEffect(() => {
        const fetchWorkspaceData = () => {
            const response = axios.get(`http://localhost:8080/api/workspaces/${id}/workspace`).then(response => {
                setWorkspaceData(response.data);
                localStorage.setItem("workspaces", JSON.stringify(response.data));
                }
            );
        };

        fetchWorkspaceData();
    }, [id, name]);

    return (
        <div>
            <div className='border border-1-slate-500 py-1'>
                <HomeHeader workspaceData={workspaceData} setWorkspaceData={setWorkspaceData}/>

            </div>

            <div className='flex '>
                <div className='w-[13%]'>
                    <WorkspaceSidebar/>
                </div>
                <div className='w-[3%]'></div>
                <div className='w-[75%]'>
                    {workspaceData ? (
                        <WorkspaceMembers workspaceData={workspaceData}/>
                    ) : (
                        <div>Loading...</div>
                    )}
                </div>

            </div>
        </div>
    );
};

export default Workspace;