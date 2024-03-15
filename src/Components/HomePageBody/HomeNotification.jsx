import React from 'react';
import {FaRegClock} from "react-icons/fa";
import {Avatar, Menu, MenuButton, MenuList} from "@chakra-ui/react";
import {IoChevronDownSharp} from "react-icons/io5";
import {MdAdd, MdAddBox} from "react-icons/md";
import {IoIosAdd} from "react-icons/io";
import CreateBoards from "../CreateBoards/CreateBoards";

const HomeNotification = ({workspace, user}) => {
    return (
        <div className='flex h-full'>
            <div className='w-[45%]'>
                <div className='drop-shadow-md'>
                    <div className='flex justify-center h-48 rounded-t-md'>
                        <img className='w-full max-h-full rounded-t-md'
                             src="https://i.pinimg.com/564x/bf/01/d4/bf01d4afa08f2612571f6a5fd9405503.jpg" alt=""/>
                    </div>
                    <div className='rounded-b-md bg-white'>
                        <p className='pt-4 text-lg font-bold'>Stay on track and up to date</p>
                        <p className='mt-2 text-md px-4 pb-6'>Invite people to boards and cards, leave comments, add due
                            dates, and we'll show the most important activity here.</p>
                    </div>
                </div>
            </div>

            <div className='ml-16 w-[35%]'>
                <div className='flex items-center pl-4 text-base font-bold'>
                    <FaRegClock style={{strokeWidth: '1'}}/>
                    <p className='ml-2'>Recently viewed</p>
                </div>

                <div className='mt-4'>
                    {/*{workspace.map((item) => (*/}
                    {/*    <div className='mt-2 hover:bg-gray-200 rounded-md'>*/}
                    {/*        <div className='flex items-center space-x-3 pl-2'>*/}
                    {/*            <Avatar name={item.name} size='sm' borderRadius='md'/>*/}

                    {/*            <div className='text-left'>*/}
                    {/*                <p className='text-lg font-bold'>daad</p>*/}
                    {/*                <p className='text-sm '>{item.name}</p>*/}
                    {/*            </div>*/}
                    {/*        </div>*/}
                    {/*    </div>*/}
                    {/*))}*/}
                </div>

                <p className='flex pl-4 items-center mt-10 text-base font-bold'>Links</p>

                <div className='group flex items-center  mt-3 hover:bg-gray-200 py-2 pl-2 rounded-md'>
                    <Menu>
                        <MenuButton className='w-full cursor-pointer'>
                            <div className='flex items-center'>
                                <IoIosAdd className='text-4xl rounded-sm bg-gray-100 group-hover:bg-gray-400'/>
                                <p className='ml-2'>Create a board</p>
                            </div>
                        </MenuButton>
                        <MenuList minWidth='340px'>
                            <CreateBoards user={user} workspace={workspace}/>
                        </MenuList>
                    </Menu>
                </div>

            </div>

        </div>
    );
};

export default HomeNotification;