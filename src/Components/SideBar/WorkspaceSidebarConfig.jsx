import React from 'react';
import {BsTrello} from "react-icons/bs";
import { IoPersonOutline } from "react-icons/io5";
import { IoSettingsOutline } from "react-icons/io5";

const WorkspaceSidebarConfig = [
    {
        title: 'Boards', icon: <BsTrello />
    },
    {
        title: 'Members', icon: <IoPersonOutline/>
    },
    {
        title: 'Workspace settings', icon: <IoSettingsOutline />
    }
]

export default WorkspaceSidebarConfig;