import React, {useEffect, useState} from 'react';
import {FaRegClock} from "react-icons/fa";
import BoardCard from "./BoardCard";
import WorkspaceControlBar from "./WorkspaceControlBar";
import {IoMdInformationCircleOutline} from "react-icons/io";
import {BsPeople} from "react-icons/bs";
import {Link} from "react-router-dom";
import axios from "axios";

const BoardsPage = ({workspace, user}) => {
    const [board, setBoard] = useState([])


    useEffect(() => {
        const fetchData = async () => {
            const boardsByWorkspace = {};

            const axiosRequests = workspace.map(async (data) => {
                try {
                    const response = await axios.get(`http://localhost:8080/api/workspaces/${data.id}/boards`);
                    boardsByWorkspace[data.id] = response.data;
                    localStorage.setItem("boardList", JSON.stringify(response.data));
                } catch (error) {
                    console.error(`Error fetching boards for workspace ${data.id}:`, error);
                }
            });

            try {
                await Promise.all(axiosRequests);
                setBoard(boardsByWorkspace);
            } catch (error) {
                console.error("Error fetching boards:", error);
            }
        };

        fetchData();
    }, [workspace]);

    return (
        <div className='text-lg font-bold ml-2 my-3 p-2'>
            <div className='flex items-center'>
                <FaRegClock/>
                <p className='ml-2'>Recently viewed</p>
            </div>

            <Link to='/content'>
                <div className='flex space-x-9 mt-4'>
                    <BoardCard/>
                </div>
            </Link>

            <div className='text-left text-gray-600 mt-10'>
                <p>YOUR WORKSPACES</p>
            </div>

            <div>
                {workspace.map((data) => (
                    <div key={data.id} className='mt-8'>
                        <WorkspaceControlBar workspace={data}/>
                        <div className='flex space-x-9 mt-4'>
                            {board[data.id]?.map((item) => (
                                <BoardCard key={item.id} board={item}/>
                            ))}
                        </div>
                    </div>
                ))}
            </div>

            <div className='flex items-center text-left text-gray-600 mt-10'>
                <p>GUEST WORKSPACES</p>
                <IoMdInformationCircleOutline className='text-xl ml-2 text-black'/>
            </div>

            <div>
                <div className='flex items-center space-x-3 mt-5 text-left text-xl text-gray-600'>
                    <BsPeople style={{strokeWidth: '0.8'}}/>
                    <p>Dự án C08</p>
                </div>

                <div className='flex space-x-9 mt-4'>
                    <BoardCard/>
                </div>
            </div>
        </div>
    )
        ;
};

export default BoardsPage;