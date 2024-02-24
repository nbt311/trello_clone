import React from 'react';
import HomeHeader from "../../Components/HomeHeader/HomeHeader";
import WorkspaceSidebar from "../../Components/SideBar/WorkspaceSidebar";
import SidebarConfig from "../../Components/SideBar/SidebarConfig";
import WorkspaceMembers from "../../Components/Workspace/WorkspaceMembers";

const Workspace = () => {

    return (
        <div>
            <div className='border border-1-slate-500 py-1'>
                <HomeHeader/>

            </div>

            <div className='flex '>
                <div className='w-[13%]'>
                    <WorkspaceSidebar/>
                </div>
                <div className='w-[3%]'></div>
                <div className='w-[75%]'>
                    <WorkspaceMembers/>
                </div>
            </div>
        </div>
    );
};

export default Workspace;