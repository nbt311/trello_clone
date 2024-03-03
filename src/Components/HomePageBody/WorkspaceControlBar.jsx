import React from 'react';
import {Avatar} from "@chakra-ui/react";
import {BsPerson, BsTrello} from "react-icons/bs";
import {GoPerson} from "react-icons/go";
import {IoIosSettings} from "react-icons/io";

const WorkspaceControlBar = ({workspace}) => {
    return (
        <div className='flex items-center w-[90%] justify-between mt-5'>
            <div className='flex items-center space-x-3 '>
                <Avatar name={workspace.name} size='sm' borderRadius='md'/>
                <p>{workspace.name}</p>
            </div>

            <div className='flex space-x-4'>
                <div className='flex flex-row text-base space-x-3 cursor-pointer items-center justify-center font-medium bg-gray-100 hover:bg-gray-200 rounded-sm py-1 px-3 w-fit'>
                    <BsTrello />
                    <p>Boards</p>
                </div>

                <div className='flex flex-row text-base space-x-3 cursor-pointer items-center justify-center font-medium bg-gray-100 hover:bg-gray-200 rounded-sm py-1 px-3 w-fit'>
                    <GoPerson style={{strokeWidth: '0.8'}}/>
                    <p>Members (1)</p>
                </div>

                <div className='flex flex-row text-base space-x-3 cursor-pointer items-center justify-center font-medium bg-gray-100 hover:bg-gray-200 rounded-sm py-1 px-3 w-fit'>
                    <IoIosSettings />
                    <p>Settings</p>
                </div>
            </div>
        </div>
    );
};

export default WorkspaceControlBar;