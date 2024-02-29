import React from 'react';
import {Box, Heading, Text} from "@chakra-ui/react";
import {BsThreeDots} from "react-icons/bs";
import ListCards from "./ListCards/ListCards";
import {IoMdAdd} from "react-icons/io";
import {useSortable} from "@dnd-kit/sortable";
import {CSS} from '@dnd-kit/utilities';
import {mapOrder} from "../../../../Utils/Sort";

const Column = ({column}) => {
    const {
        attributes, listeners, setNodeRef, transform, transition, isDragging
    } = useSortable({id: column._id, data: {...column}});

    const dndKitColumnStyle = {
        transform: CSS.Translate.toString(transform),
        transition,
        opacity: isDragging ? 0.5 : undefined,
    };

    const orderedCards = mapOrder(column?.cards, column?.cardOrderIds, '_id')

    return (
        <Box ref={setNodeRef} style={dndKitColumnStyle} {...attributes} {...listeners}
            key={column._id}
            className='w-[280px] h-fit bg-gray-100 p-4 pr-2 rounded-md drop-shadow-md text-left'>
            <Heading className='flex items-center justify-between mb-4'>
                <p className='text-lg cursor-pointer'>{column.title}</p>
                <BsThreeDots className='text-2xl hover:bg-gray-200 p-1 rounded-md cursor-pointer'/>
            </Heading>

            <div className='max-h-[70vh] overflow-y-scroll' style={{
                scrollbarWidth: 'thin',
                scrollbarColor: '#888 #f1f1f1',
                paddingRight: '2px'
            }}>
                <ListCards cards = {orderedCards}/>
            </div>

            <Text className='mt-4 flex items-center space-x-2 font-medium text-gray-500
                    hover:text-black hover:bg-gray-200 rounded-md p-1 -ml-1 cursor-pointer'>
                <IoMdAdd className='text-lg'/>
                <p>Add a card</p>
            </Text>
        </Box>
    );
};

export default Column;