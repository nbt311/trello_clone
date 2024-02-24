import React, {useEffect, useState} from 'react';
import WorkspaceDropdown from "./WorkspaceDropdown";
import {Avatar} from "@chakra-ui/react";
import WorkspaceSidebarConfig from "./WorkspaceSidebarConfig";

const WorkspaceSidebar = () => {
    const [user, setUser] = useState({});

    useEffect(() => {
        const user = JSON.parse(localStorage.getItem('user'));
        setUser(user);
    }, []);
    return (
        <div className='flex flex-col items-start' >
            <div className="flex mt-4 ml-4">
                <Avatar size='sm' borderRadius='md' name={user.username} src=''/>
                <div className='ml-2 mt-1'>
                    <p className='text-base font-medium'>{user.username}</p>
                </div>
            </div>

            <hr className='border-1-slate-500 py-1 w-full mt-4'/>

            <div className='mt-4'>
                {WorkspaceSidebarConfig.map((item) => (
                    <div key={item.title} className='flex flex-row ml-2 my-3 text-lg space-x-4 cursor-pointer items-center font-medium hover:bg-gray-200 rounded-md p-2 w-64'>
                        {item.icon}
                        <p>{item.title}</p>
                    </div>
                ))}
            </div>

            <hr className='border-1-slate-500 py-1 w-full'/>

            <div className='ml-2 my-3'>
                <p className='text-sm font-bold'>Workspaces</p>
            </div>

            <div className='mx-auto w-full hover:bg-gray-200 rounded-md p-2'>
                <WorkspaceDropdown/>
            </div>
        </div>
    );
};

export default WorkspaceSidebar;