import React from 'react';
import HomeHeader from "../../Components/HomeHeader/HomeHeader";
import Sidebar from "../../Components/SideBar/Sidebar";
import {useDisclosure} from "@chakra-ui/react";
import CreateWorkspaceModal from "../../Components/WorkspaceModal/CreateWorkspaceModal";
import BoardsPage from "../../Components/HomePageBody/BoardsPage";
import {Route, Routes} from "react-router-dom";

const HomePage = () => {
    const { isOpen, onOpen, onClose } = useDisclosure()

    return (
        <div>
            <div className='border border-1-slate-500 py-1'>
                <HomeHeader onOpen={onOpen} onClose={onClose}/>
            </div>

            <div className='flex'>
                <div className='w-[20%] ml-20 h-fit mt-4'>
                    <Sidebar/>
                </div>

                <div className='ml-10 w-full mt-4 overflow-visible'>
                    <Routes>
                        <Route path='/boards' element={<BoardsPage/>}></Route>
                    </Routes>
                </div>
            </div>

            <CreateWorkspaceModal onOpen={onOpen} isOpen={isOpen} onClose={onClose}/>
        </div>
    );
};

export default HomePage;