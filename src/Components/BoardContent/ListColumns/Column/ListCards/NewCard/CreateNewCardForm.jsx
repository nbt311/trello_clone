import React from 'react';
import {RxPencil1} from "react-icons/rx";
import {Card, Input} from "@chakra-ui/react";
import {IoMdClose} from "react-icons/io";

const CreateNewCardForm = ({handleCreateCard}) => {
    return (
            <Card className='rounded-md my-3 p-2'>
                <div className='flex flex-row justify-between'>
                    <Input className='outline-none placeholder:text-sm pb-5 break-words'
                           placeholder='Enter a title for this card...'
                           variant='unstyled'
                    ></Input>
                    <div className='flex items-center space-x-2 font-medium text-black p-1 -ml-1'>
                        <button type="submit"
                                className='bg-blue-500 hover:bg-blue-600 py-1 px-3 text-white font-semibold rounded-md'>Add card
                        </button>
                        <IoMdClose className='text-3xl p-1 hover:bg-gray-300 rounded-md' onClick={handleCreateCard}/>
                    </div>
                </div>
            </Card>
    );
};

export default CreateNewCardForm;