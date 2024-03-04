import React, {useState} from 'react';
import {
    Menu,
    MenuButton,
    MenuList,
    Button,
    Select
} from "@chakra-ui/react";
import { IoChevronBack } from "react-icons/io5";
import { IoMdClose } from "react-icons/io";
import { PiHandWaving } from "react-icons/pi";
const CreateBoards = () => {
    const [boardTitle, setBoardTitle] = useState("")
    const isButtonDisabled = !boardTitle ;
    return (
            // <Menu>
            //     <MenuList minWidth='340px'>
        <div>

                    <div className="flex flex-col items-center justify-center">
                        <div className='flex w-full justify-between'>
                            <button className=''><IoChevronBack /></button>
                            <p className='font-semibold'>Create board</p>
                            <button className=''><IoMdClose /></button>
                        </div>

                        <div className="w-[90%]">
                            <p className='flex font-semibold'>Board title<span className="text-red-500">*</span></p>
                            <input value={boardTitle}
                                   onChange={(e) => setBoardTitle(e.target.value)}
                                className='w-full h-9 border-2 border-blue-400 outline-none focus:border-blue-400 hover:border-red-500 rounded-md'/>
                            {boardTitle ?
                                  null : <div className="flex">
                                    <PiHandWaving className='w-6 h-6 text-yellow-500' />
                                    <p className='h-6'>Board title is required</p>
                                </div>
                            }
                        </div>

                        <div className='w-[90%] '>
                            <p className='flex font-semibold'>Workspace</p>
                            <Select placeholder=''>
                                <option value='option1'>Option 1</option>
                                <option value='option2'>Option 2</option>
                                <option value='option3'>Option 3</option>
                            </Select>
                        </div>

                        <div className="w-[90%]">
                            <p className='flex font-semibold'>Visibility</p>
                            <Select placeholder=''>
                                <option value='option1'>Option 1</option>
                                <option value='option2'>Option 2</option>
                                <option value='option3'>Option 3</option>
                            </Select>
                        </div>

                        <div className="w-[90%] mt-3"><Button className="w-full" isDisabled={isButtonDisabled} colorScheme='blue' >Create</Button></div>
                        <div className="w-[90%] h-9 mt-3 border-2 rounded-md border-gray-100 hover:bg-gray-200"><button className="w-full" >Start with a template</button></div>
                        <div className='flex w-[90%] text-sm text-left mt-2'>
                            <p>By using images from Unsplash, you agree to their <a href="https://unsplash.com/license"  target="_blank" rel="noopener noreferrer" className="hover:underline">license </a> and <a href="https://unsplash.com/terms"  target="_blank" rel="noopener noreferrer" className="hover:underline"> Terms of Service</a> </p>
                        </div>
                    </div>
        </div>
            //     </MenuList>
            // </Menu>
    )
};

export default CreateBoards;