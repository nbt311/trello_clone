import React, {useEffect, useState} from 'react';
import {IoChevronDownSharp} from "react-icons/io5";
import {Avatar} from "@chakra-ui/react";


const WorkspaceDropdown = ({workspace}) => {
    return (
        <div>
            <div className='flex items-center justify-between cursor-pointer font-medium'>
                <div className='flex items-center space-x-3 '>
                    <Avatar name= {workspace.name} size='sm' borderRadius='md'/>
                    <p>{workspace.name}</p>
                </div>
                <div>
                    <IoChevronDownSharp className='mt-1 mr-2'/>
                </div>
            </div>
        </div>
    );
};

export default WorkspaceDropdown;