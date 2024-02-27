import React, {useState} from 'react';
import SidebarConfig from "./SidebarConfig";
import WorkspaceDropdown from "./WorkspaceDropdown";
import {useNavigate} from "react-router-dom";

const Sidebar = () => {
    const [selectedTab, setSelectedTab] = useState({});
    const navigate = useNavigate();
    const handleTabClick = (item) => {
        setSelectedTab(item);

        switch (item.title) {
            case 'Boards':
                navigate('/boards');
                break;
            case 'Home':
                navigate('/');
                break;
            // Thêm các trường hợp khác nếu cần
            default:
                break;
        }
    };

    return (
        <div className='flex flex-col items-start'>
            <div>
                {SidebarConfig.map((item) => (
                    <div key={item.title}
                         className={`flex flex-row ml-2 my-3 text-lg space-x-4 cursor-pointer items-center font-medium 
                         ${selectedTab === item ? 'bg-blue-100 bg-opacity-50 text-blue-700' : 'hover:bg-gray-200'} rounded-md p-2 w-64`} onClick={() => handleTabClick(item)}>
                        {item.icon}
                        <p>{item.title}</p>
                    </div>
                ))}
            </div>

            <hr className='border-t-1 border-black my-2 mx-auto w-full'/>

            <div className='ml-2 my-3'>
                <p className='text-sm font-bold'>Workspaces</p>
            </div>

            <div className='mx-auto w-full hover:bg-gray-200 rounded-md p-2'>
                <WorkspaceDropdown/>
            </div>
        </div>
    );
};

export default Sidebar;