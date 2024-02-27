import React from 'react';
import {FaRegClock} from "react-icons/fa";
import BoardCard from "./BoardCard";
import WorkspaceControlBar from "./WorkspaceControlBar";
import {IoMdInformationCircleOutline} from "react-icons/io";
import {BsPeople} from "react-icons/bs";

const BoardsPage = () => {
    return (
        <div className='text-lg font-bold ml-2 my-3 p-2'>
            <div className='flex items-center'>
                <FaRegClock/>
                <p className='ml-2'>Recently viewed</p>
            </div>

            <div className='flex space-x-9 mt-4'>
                {[1, 1, 1, 1].map((item) => (
                    <BoardCard/>
                ))}
            </div>

            <div className='text-left text-gray-600 mt-10'>
                <p>YOUR WORKSPACES</p>
            </div>

            <div>
                {[1, 1, 1].map(() => (
                    <div className='mt-8'>
                        <WorkspaceControlBar/>
                        <div className='flex space-x-9 mt-4'>
                            {[1, 1, 1, 1].map((item) => (
                                <BoardCard/>
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