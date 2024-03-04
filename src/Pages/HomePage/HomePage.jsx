import React, {useEffect, useState} from 'react';
import HomeHeader from "../../Components/HomeHeader/HomeHeader";
import Sidebar from "../../Components/SideBar/Sidebar";
import {useDisclosure} from "@chakra-ui/react";
import CreateWorkspaceModal from "../../Components/WorkspaceModal/CreateWorkspaceModal";
import axios from "axios";
import CreateBoards from "../../Components/CreateBoards/CreateBoards";
import BoardsPage from "../../Components/HomePageBody/BoardsPage";
import {Route, Routes} from "react-router-dom";
import HomeNotification from "../../Components/HomePageBody/HomeNotification";

const HomePage = () => {
    const {isOpen, onOpen, onClose} = useDisclosure()
    const [workspaceName, setWorkspaceName] = useState("");
    const [workspaceType, setWorkspaceType] = useState("");
    const [workspaceDescription, setWorkspaceDescription] = useState("");
    const [workspace, setWorkspace] = useState([]);
    const [user, setUser] = useState({});

    // useEffect( () => {
    //      axios.get('http://localhost:8080/api/workspaces').then((response) => {
    //         localStorage.setItem("workspacelist", JSON.stringify(response.data));
    //         setWorkspace(response.data);
    //     })
    // }, []);
    useEffect(() => {
        const user = JSON.parse(localStorage.getItem('user'));
        setUser(user);
        axios.get(`http://localhost:8080/api/users/${user.id}/workspaces`).then((response) => {
            localStorage.setItem("workspacelist", JSON.stringify(response.data));
            setWorkspace(response.data);
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
                        <Route path='/boards' element={<BoardsPage workspace={workspace}/>}></Route>
                        <Route path='/' element={<HomeNotification workspace={workspace}/>}></Route>
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