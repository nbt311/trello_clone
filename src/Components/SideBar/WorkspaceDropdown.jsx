import React from 'react';
import {IoChevronDownSharp} from "react-icons/io5";

const WorkspaceDropdown = () => {
    return (
        <div>
            <div className='flex items-center justify-between cursor-pointer font-medium'>
                <div className='flex items-center space-x-3 '>
                    <div className='w-8 h-8 bg-amber-500 rounded-sm'></div>
                    <p>My Workspace</p>
                </div>

                <div>
                    <IoChevronDownSharp className='mt-1 mr-2'/>
                </div>
            </div>
        </div>
    );
};

export default WorkspaceDropdown;