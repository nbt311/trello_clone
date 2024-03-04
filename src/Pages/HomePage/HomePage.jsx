import React, {useContext, useEffect, useState} from 'react';
import HomeHeader from "../../Components/HomeHeader/HomeHeader";
import Sidebar from "../../Components/SideBar/Sidebar";
import {useDisclosure} from "@chakra-ui/react";
import CreateWorkspaceModal from "../../Components/WorkspaceModal/CreateWorkspaceModal";
import BoardsPage from "../../Components/HomePageBody/BoardsPage";
import {Route, Routes} from "react-router-dom";
import HomeNotification from "../../Components/HomePageBody/HomeNotification";
import UserContext from "../../Context/UserContext";
import UserService from "../../Service/UserService";

const HomePage = () => {
    const {isOpen, onOpen, onClose} = useDisclosure()
    const [workspaceName, setWorkspaceName] = useState("");
    const [workspaceType, setWorkspaceType] = useState("");
    const [workspaceDescription, setWorkspaceDescription] = useState("");
    const [workspace, setWorkspace] = useState([]);
    const {user, updateUser} = useContext(UserContext);
    useEffect(() => {
        const newUser = JSON.parse(localStorage.getItem('user'));
        updateUser(newUser);
        UserService.getWorkspaceByUser(newUser.id).then((data) => {
            setWorkspace(data)
        })

    }, []);

    return (
        <div>
            <div className='border border-1-slate-500 py-1'>
                <HomeHeader onOpen={onOpen} onClose={onClose} workspace={workspace}/>
            </div>

            <div className='flex'>
                <div className='w-[20%] ml-20 h-fit mt-4'>
                    <Sidebar workspace={workspace}/>
                </div>

                <div className='ml-10 w-full mt-4 overflow-visible'>
                    <Routes>
                        <Route path='/boards' element={<BoardsPage setWorkspace={setWorkspace} workspace={workspace} user={user}/>}></Route>
                        <Route path='/' element={<HomeNotification workspace={workspace} user={user}/>}></Route>
                    </Routes>
                </div>
            </div>

            <CreateWorkspaceModal onOpen={onOpen} isOpen={isOpen} onClose={onClose}
                                  workspaceName={workspaceName} setWorkspaceName={setWorkspaceName}
                                  workspaceType={workspaceType} setWorkspaceType={setWorkspaceType}
                                  workspaceDescription={workspaceDescription}
                                  setWorkspaceDescription={setWorkspaceDescription}
            />
        </div>
    );
};

export default HomePage;