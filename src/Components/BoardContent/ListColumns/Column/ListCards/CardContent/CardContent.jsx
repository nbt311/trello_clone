import React from 'react';
import {RxPencil1} from "react-icons/rx";
import {Card} from "@chakra-ui/react";
import {useSortable} from "@dnd-kit/sortable";
import {CSS} from "@dnd-kit/utilities";

const CardContent = ({card}) => {
    const {
        attributes, listeners, setNodeRef, transform, transition, isDragging,
    } = useSortable({id: card.id, data: {...card}});

    const dndKitCardStyle = {
        transform: CSS.Translate.toString(transform),
        transition,
        opacity: isDragging ? 0.5 : undefined,
        border: isDragging ? '1px solid #2ecc71' : undefined
    };
    return (
        <Card ref={setNodeRef} style={dndKitCardStyle} {...attributes} {...listeners}
            key={card.id}
            className='rounded-md my-3 p-2'>
            <div className='flex flex-row justify-between'>
                <p className='text-sm font-medium max-w-[88%]'>{card.title}</p>
                <RxPencil1 className='text-2xl p-1 rounded-full hover:bg-gray-200'/>
            </div>
        </Card>
    );
};

export default CardContent;