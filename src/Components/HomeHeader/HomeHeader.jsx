import React from 'react';
import {IoAppsSharp} from "react-icons/io5";
import Dropdown from "./Dropdown";
import {MdAddBox} from "react-icons/md";
import {TbBellRinging2} from "react-icons/tb";
import {FaRegQuestionCircle} from "react-icons/fa";
import {CgProfile} from "react-icons/cg";

const HomeHeader = () => {
    return (
        <div className='bg-white border-gray-200  w-full lg:px-6 py-1'>
            <div className='flex flex-row items-center justify-between mx-auto max-w-screen relative'>
                <div className='flex items-center space-x-2'>
                    <div>
                        <IoAppsSharp className='text-4xl cursor-pointer hover:bg-gray-200 rounded-md p-2'/>
                    </div>

                    <img className='w-[10%] cursor-pointer hover:bg-gray-200 rounded-md p-2'
                         src="https://upload.wikimedia.org/wikipedia/en/thumb/8/8c/Trello_logo.svg/1280px-Trello_logo.svg.png"
                         alt=""/>

                    <div className='flex space-x-4'>
                        <Dropdown title='Workspaces'/>
                        <Dropdown title='Recent'/>
                        <Dropdown title='Starred'/>
                        <Dropdown title='Templates'/>
                    </div>

                    <div>
                        <MdAddBox className='text-4xl cursor-pointer opacity-90 hover:opacity-100' color='#2435FA'/>
                    </div>
                </div>

                <div className='flex items-center space-x-3'>
                    <div>
                        <form className="max-w-sm px-1">
                            <div className="relative">
                                <svg
                                    xmlns="http://www.w3.org/2000/svg"
                                    className="absolute top-0 bottom-0 w-5 h-5 my-auto text-gray-400 left-3"
                                    fill="none"
                                    viewBox="0 0 24 24"
                                    stroke="currentColor"
                                >
                                    <path
                                        strokeLinecap="round"
                                        strokeLinejoin="round"
                                        strokeWidth={2}
                                        d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"
                                    />
                                </svg>
                                <input
                                    type="text"
                                    placeholder="Search"
                                    className="w-48 h-9 py-3 pl-10 pr-4 text-gray-500 border rounded-md outline-none bg-gray-50
                                focus:bg-white focus:border-blue-400 focus:border-2 focus:w-96"
                                />
                            </div>
                        </form>
                    </div>

                    <div>
                        <TbBellRinging2 className='text-2xl cursor-pointer' color='gray'/>
                    </div>

                    <div>
                        <FaRegQuestionCircle className='text-2xl cursor-pointer' color='gray'/>
                    </div>

                    <div>
                        <CgProfile className='text-2xl cursor-pointer' color='gray'/>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default HomeHeader;