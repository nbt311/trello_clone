import React, {useEffect, useState} from 'react';
import {Avatar, Button} from "@chakra-ui/react";
import {FaLink} from "react-icons/fa";
import { AiOutlineUserAdd } from "react-icons/ai";
import {MdCheckCircleOutline} from "react-icons/md";
import { AiOutlineClose } from "react-icons/ai";
const WorkspaceMembers = () => {
    const [user, setUser] = useState({});
    const [showNotification, setShowNotification] = useState(true);
    const [selectedOption, setSelectedOption] = useState("workspaceMembers");

    const handleClick = (option) => {
        setSelectedOption(option);
    };
    const hideNotification = () => {
        setShowNotification(true);
    };
    if (showNotification) {
        setTimeout(() => {
            setShowNotification(false);
        }, 3000);
    }

    useEffect(() => {
        const user = JSON.parse(localStorage.getItem('user'));
        setUser(user);
    }, []);
    return (
        <div className=''>
            <div className='flex mt-5'>
                <div className='w-[50%] flex place-content-center '>
                        <Avatar size='lg' borderRadius='md' name={user.username} src=''/>
                        <div className='ml-2 mt-3'>
                            <p className='font-medium text-xl'>{user.username}</p>
                        </div>
                </div>

                <div className='w-[50%] mt-2 self-center'>
                        <Button colorScheme='blue'><AiOutlineUserAdd/>Invite Workspace members</Button>
                </div>
            </div>

            <hr className='border-1-slate-500 py-1 w-full mt-4'/>

            <div className='flex mt-10'>
                <div className='w-[25%] flex flex-col items-start'>
                        <h1 className='text-xl font-semibold'>Members</h1>
                        <p className='text-sm mt-5'>Members of Workspace boards</p>

                    <div className={`hover:bg-gray-200 w-48 h-9 py-3 pr-4 rounded-md ${selectedOption === "workspaceMembers" ? 'bg-blue-100' : ''}`} onClick={() => handleClick("workspaceMembers")}>
                        <button className={`font-semibold flex justify-center ${selectedOption === "workspaceMembers" ? "text-blue-700" : ""}`}>Workspace members</button>
                    </div>

                    <div className={`hover:bg-gray-200 w-48 h-9 py-3 pr-4 rounded-md ${selectedOption === "guests" ? 'bg-blue-100' : ''}`} onClick={() => handleClick("guests")}>
                        <button className={`font-semibold flex justify-center ${selectedOption === "guests" ? "text-blue-700" : ""}`}>Guests</button>
                    </div>

                    <div className={`hover:bg-gray-200 w-48 h-9 py-3 pr-4 rounded-md ${selectedOption === "pending" ? 'bg-blue-100' : ''}`} onClick={() => handleClick("pending")}>
                        <button className={`font-semibold flex justify-center ${selectedOption === "pending" ? "text-blue-700" : ""}`}>Pending</button>
                    </div>

                </div>

                <div className='w-[75%] relative'>
                    <div className='flex flex-col items-start'>
                        <p className='text-xl font-semibold'>Workspace members</p>
                        <p className='text-left text-lg mt-3'>Workspace members can view and join all Workspace visible boards and create new boards in the Workspace.</p>
                    </div>

                    <hr className='border-1-slate-500 py-1 w-full mt-4'/>

                    <div className='flex'>
                        <div className='w-[60%] flex flex-col items-start '>
                            <p className='text-xl font-semibold mt-5'>Invite members to join you</p>
                            <p className='text-left text-lg mt-3'>Anyone with an invite link can join this Free Workspace. You can also disable and create a new invite link for this Workspace at any time.</p>
                        </div>

                        <div className="w-[26%]"></div>

                        <div className='w-[14%] place-self-end space-y-10'>
                            {showNotification &&
                            <div className="absolute w-[16%] flex bg-emerald-200 rounded-md">
                                <MdCheckCircleOutline className="mt-0.5 text-emerald-600"/>
                                <p className="text-sm text-emerald-600">Link copied to clipboard</p>
                            </div>}
                            <div className='flex hover:bg-gray-300 ml-5'>
                                <FaLink className='mt-1'/>
                                <button className='ml-1' onClick={hideNotification}>Invite with link</button>
                            </div>
                        </div>
                    </div>

                    <hr className='border-1-slate-500 py-1 w-full mt-4'/>

                    <div className='flex justify-start mt-5'>
                        <form className="">
                                <input
                                    type="text"
                                    placeholder="Fillter by name"
                                    className="w-48 h-9 py-3 pr-4 text-gray-500 border rounded-md outline-none bg-gray-50 focus:bg-white focus:border-blue-400 focus:border-2"
                                />
                        </form>
                    </div>

                    <hr className='border-1-slate-500 py-1 w-full mt-4'/>

                    <div className='mt-2'>
                        <div className='flex'>
                            <div className='flex w-[62%]'>
                                <Avatar className='mt-1' size='sm' name={user.username} src=''/>
                                <div className='ml-2'>
                                    <p className='text-base font-medium'>{user.username}</p>
                                    <p className='text-sm'>{user.email}</p>
                                </div>
                            </div>

                            <div className='w-[15%]'>
                                <p className='mt-1'>On 0 boards</p>
                            </div>
                            <div className='w-[10%]'>
                                <Button variant='outline'>Admin</Button>
                            </div>
                            <div className='w-[13%]'>
                                <Button variant='outline'><AiOutlineClose/>Leave</Button>
                            </div>
                        </div>

                    </div>

                    <hr className='border-1-slate-500 py-1 w-full mt-4'/>
                </div>
            </div>
        </div>
    );
};

export default WorkspaceMembers;