import React from 'react';
import {RxPencil1} from "react-icons/rx";
import {Card, Input} from "@chakra-ui/react";

const CreateNewCardForm = () => {
    return (
            <Card className='rounded-md my-3 p-2'>
                <div className='flex flex-row justify-between'>
                    <Input className='outline-none placeholder:text-sm pb-5 break-words'
                           placeholder='Enter a title for this card...'
                           variant='unstyled'
                    ></Input>
                    {/*<p className='text-sm font-medium max-w-[88%]'>{card.title}</p>*/}
                </div>
            </Card>
    );
};

export default CreateNewCardForm;