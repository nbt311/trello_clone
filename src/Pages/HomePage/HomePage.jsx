import React from 'react';
import HomeHeader from "../../Components/HomeHeader/HomeHeader";
import Sidebar from "../../Components/SideBar/Sidebar";
import {useDisclosure} from "@chakra-ui/react";
import CreateWorkspaceModal from "../../Components/WorkspaceModal/CreateWorkspaceModal";

const HomePage = () => {
    const { isOpen, onOpen, onClose } = useDisclosure()

    return (
        <div>
            <div className='border border-1-slate-500 py-1'>
                <HomeHeader onOpen={onOpen} onClose={onClose}/>
            </div>

            <div className='w-[20%] ml-20'>
                <Sidebar/>
            </div>

            <CreateWorkspaceModal onOpen={onOpen} isOpen={isOpen} onClose={onClose}/>
        </div>
    );
};

export default HomePage;