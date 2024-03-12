import React, {useContext, useEffect, useState} from 'react';
import {FaRegClock} from "react-icons/fa";
import BoardCard from "./BoardCard";
import WorkspaceControlBar from "./WorkspaceControlBar";
import {IoMdInformationCircleOutline} from "react-icons/io";
import {BsPeople} from "react-icons/bs";
import {Link, useNavigate} from "react-router-dom";
import UserContext from "../../Context/UserContext";
import BoardContext from "../../Context/BoardContext";
import WorkspaceContext from "../../Context/WorkspaceContext";

const BoardsPage = ({workspaceList}) => {
    const {user, updateUser} = useContext(UserContext);
    const {workspace, updateWorkspace} = useContext(WorkspaceContext);
    const {board, updateBoard} = useContext(BoardContext);
    const navigate = useNavigate();

    const handleDivClick = (data, item) => {
        updateWorkspace(data)
        localStorage.setItem('board', JSON.stringify(item));
        navigate(`/board/${item.id}`);
    };

    return (
        <div className='text-lg font-bold ml-2 my-3 p-2'>
           <div>
               <div className='flex items-center'>
                   <FaRegClock/>
                   <p className='ml-2'>Recently viewed</p>
               </div>


               <div className='flex w-[20%] space-x-9 mt-4'>
                   <BoardCard/>
               </div>
           </div>

            <div>
                <div className='text-left text-gray-600 mt-10'>
                    <p>YOUR WORKSPACES</p>
                </div>

                <div>
                    {user.ownedWorkspaces.map((data) => (
                        <div key={data.id} className='mt-8'>
                            <WorkspaceControlBar workspace={data}/>
                            <div className='flex space-x-9 mt-4 w-full'>
                                {data.boards.map((item) => (
                                    <div className='w-[20%] cursor-pointer' onClick={() => handleDivClick(data, item)}>
                                        <BoardCard key={item.id} board={item}/>
                                    </div>
                                ))}
                            </div>
                        </div>
                    ))}
                </div>
            </div>

            <div className='flex items-center text-left text-gray-600 mt-10'>
                <p>GUEST WORKSPACES</p>
                <IoMdInformationCircleOutline className='text-xl ml-2 text-black'/>
            </div>

            <div>
                {user?.memberWorkspaces.map((data) =>(
                    <div>
                        <div className='flex items-center space-x-3 mt-5 text-left text-xl text-gray-600'>
                            <BsPeople style={{strokeWidth: '0.8'}}/>
                            <p>{data.name}</p>
                        </div>

                        <div className='flex space-x-9 mt-4 w-full'>
                            {data.boards.map((item) => (
                                <div className='w-[20%] cursor-pointer' onClick={() => handleDivClick(data, item)}>
                                    <BoardCard key={item.id} board={item}/>
                                </div>
                            ))}
                        </div>
                </div>
                   ))}

            </div>
        </div>
    )
        ;
};

export default BoardsPage;