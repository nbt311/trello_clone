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
    const { id } = useParams();
    const { isOpen, onOpen, onClose } = useDisclosure();

    const [isInputFilled, setIsInputFilled] = useState(false);
    const [inputValue, setInputValue] = useState('');

    const workspaceNew = JSON.parse(localStorage.getItem('workspaces'));
    const workspaceId = workspaceNew.id;
    const [members, setMembers] = useState([]);

    const [workspace, setWorkspace] = useState([]);
    const [user, setUser] = useState({})

    useEffect(() => {
        const user = JSON.parse(localStorage.getItem('user'));
        setUser(user);

        axios.get(`http://localhost:8080/api/users/${user.id}/workspaces`).then((response) => {
            localStorage.setItem("workspacelist", JSON.stringify(response.data));
            setWorkspace(response.data);
        })
    }, []);

    const handleInputChange = (e) => {
        const value = e.target.value;
        setInputValue(value);
        // Kiểm tra xem giá trị input có khớp với điều kiện không
        setIsInputFilled(value.includes('@gmail.com'));
    };

    useEffect(() => {
        const fetchWorkspaceData = () => {
            const response = axios.get(`http://localhost:8080/api/workspaces/${id}/workspace`).then(response => {
                localStorage.setItem("workspaces", JSON.stringify(response.data));
            });
        };

        fetchWorkspaceData();
    }, [id]);

    useEffect(() => {
        const fetchMembers = () => {
            axios.get(`http://localhost:8080/api/workspaces/${workspaceId}/members`).then(response => {
                    setMembers(response.data);
                }
            ).catch(error => {
                console.error('Error fetching members:', error);
            });

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
                    <WorkspaceSidebar />
                </div>
                <div className='w-[3%]'></div>
                <div className='w-[75%]'>
                    {/*{workspaceData ? (*/}
                        <WorkspaceMembers onOpen={onOpen} onClose={onClose} members={members} setMembers={setMembers} workspace={workspaceNew}/>
                    {/*) : (*/}
                    {/*    <div>Loading...</div>*/}
                    {/*)}*/}
                </div>

                {/*{isInputFilled ? (*/}
                    <InvitePopupTwo isOpen={isOpen} onOpen={onOpen} onClose={onClose} />
                {/*) : (*/}
                {/*    <InvitePopup onInputChange={handleInputChange} isOpen={isOpen} onOpen={onOpen} onClose={onClose} />*/}
                {/*)}*/}

            </div>
        </div>
    );
};

export default Workspace;