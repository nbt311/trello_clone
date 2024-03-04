import React, {useEffect, useState} from 'react';
import HomeHeader from "../../Components/HomeHeader/HomeHeader";
import WorkspaceSidebar from "../../Components/SideBar/WorkspaceSidebar";
import SidebarConfig from "../../Components/SideBar/SidebarConfig";
import WorkspaceMembers from "../../Components/Workspace/WorkspaceMembers";
import axios from "axios";
import {useParams} from "react-router-dom";
import {useDisclosure} from "@chakra-ui/react";
import InvitePopup from "../../Components/WorkspaceModal/InvitePopup";
import InvitePopupTwo from "../../Components/WorkspaceModal/InvitePopupTwo";


const Workspace = () => {
    const {id} = useParams();
    const {isOpen, onOpen, onClose} = useDisclosure();

    const [isInputFilled, setIsInputFilled] = useState(false);
    const [inputValue, setInputValue] = useState('');

    const [workspace, setWorkspace] = useState([]);
    console.log('workspace: ', workspace)
    const [user, setUser] = useState({})

    const workspaceNew = JSON.parse(localStorage.getItem('workspaces'));
    const workspaceId = workspaceNew.id;
    const [members, setMembers] = useState([]);

    console.log('workspaceID: ', workspaceId)


    // useEffect(() => {
    //     console.log('useEffect - fetchData');
    //     const fetchData = async () => {
    //         const userFromLocalStorage = JSON.parse(localStorage.getItem('user'));
    //         setUser(userFromLocalStorage);
    //
    //         const workspaceResponse = await axios.get(`http://localhost:8080/api/users/${userFromLocalStorage.id}/workspaces`);
    //         localStorage.setItem("workspacelist", JSON.stringify(workspaceResponse.data));
    //         setWorkspace(workspaceResponse.data);
    //     };
    //
    //     fetchData()
    // }, []);

    const handleInputChange = (e) => {
        const value = e.target.value;
        setInputValue(value);
        setIsInputFilled(value.includes('@gmail.com'));
    };

    useEffect(() => {
        console.log('useEffect - fetchData');
        const fetchData = async () => {
            const userFromLocalStorage = JSON.parse(localStorage.getItem('user'));
            setUser(userFromLocalStorage);

            const workspaceResponse = await axios.get(`http://localhost:8080/api/users/${userFromLocalStorage.id}/workspaces`);
            localStorage.setItem("workspacelist", JSON.stringify(workspaceResponse.data));
            setWorkspace(workspaceResponse.data);
        };

        console.log('useEffect - fetchWorkspaceData');
        const fetchWorkspaceData = async () => {
            const response = await axios.get(`http://localhost:8080/api/workspaces/${id}/workspace`);
            localStorage.setItem("workspaces", JSON.stringify(response.data));
        };

        const fetchDataAndWorkspaceData = async () => {
            await Promise.all([fetchData(), fetchWorkspaceData()]);
        };

        fetchDataAndWorkspaceData();
    }, [id]);

    useEffect(() => {
        const fetchMembers = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/workspaces/${id}/members`);
                setMembers(response.data);
            } catch (error) {
                console.error('Error fetching members:', error);
            }
        };

        fetchMembers();
    }, [workspaceId]);

    return (
        <div>
            <div className='border border-1-slate-500 py-1'>
                <HomeHeader workspace={workspace}/>
            </div>

            <div className='flex '>
                <div className='w-[13%]'>
                    <WorkspaceSidebar/>
                </div>
                <div className='w-[3%]'></div>
                <div className='w-[75%]'>
                    <WorkspaceMembers onOpen={onOpen} onClose={onClose} members={members} setMembers={setMembers}
                                      workspace={workspaceNew}/>
                </div>

                {/*{isInputFilled ? (*/}
                <InvitePopupTwo isOpen={isOpen} onOpen={onOpen} onClose={onClose}/>
                {/*) : (*/}
                {/*    <InvitePopup onInputChange={handleInputChange} isOpen={isOpen} onOpen={onOpen} onClose={onClose} />*/}
                {/*)}*/}

            </div>
        </div>
    );
};

export default Workspace;