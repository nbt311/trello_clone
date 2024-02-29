import React from 'react';
import {Box, Text} from "@chakra-ui/react";
import {IoMdAdd} from "react-icons/io";

const CreateColumnButton = () => {
    return (
        <div>
            <Box className='w-[280px] h-fit bg-gray-100 p-2 bg-opacity-40 cursor-pointer hover:bg-black hover:bg-opacity-10 rounded-md text-left'>
                <Text className='flex items-center space-x-2 font-medium text-black p-1 -ml-1'>
                    <IoMdAdd className='text-lg'/>
                    <p>Add another list</p>
                </Text>
            </Box>
        </div>
    );
};

export default CreateColumnButton;