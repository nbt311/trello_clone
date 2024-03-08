import React, {useState} from 'react';
import {RxPencil1} from "react-icons/rx";
import {Button, Card, Input} from "@chakra-ui/react";
import {useSortable} from "@dnd-kit/sortable";
import {CSS} from "@dnd-kit/utilities";
import CardService from "../../../../../../Service/CardService";

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

    const [isEditing, setIsEditing] = useState(false);
    const [editedTitle, setEditedTitle] = useState(card.title);

    const handleEdit = () => {
        setIsEditing(true);
    };

    const handleSave = () => {
        setIsEditing(false);
        CardService.changeCardTitle(card.id,editedTitle)
    };

    const handleCancel = () => {
        setEditedTitle(card.title);
        setIsEditing(false);
    };

    const handleBlur = () => {
        if (isEditing === false) {
            handleCancel()
        }else {
            handleSave();
        }
    };

    return (
        <Card ref={setNodeRef} style={dndKitCardStyle} {...attributes} {...listeners}
            key={card.id}
            className='rounded-md my-3 p-2'>
            {isEditing ? (
                <div className='flex flex-row justify-between items-center'>
                    <Input
                        className='outline-none placeholder:text-sm pb-5 break-words'
                        placeholder='Enter a title for this card...'
                        variant='unstyled'
                        value={editedTitle}
                        onChange={e => setEditedTitle(e.target.value)}
                        onBlur={handleBlur}
                        autoFocus
                    />
                    <div>
                        <Button
                            onClick={handleSave}
                            colorScheme='blue'
                            size='sm'
                            mr={2}
                        >
                            Save
                        </Button>
                    </div>
                </div>
            ) : (
                <div className='flex flex-row justify-between items-center'>
                    <p className='text-sm font-medium max-w-[88%]'>{editedTitle}</p>
                    <RxPencil1
                        className='text-2xl p-1 rounded-full hover:bg-gray-200 cursor-pointer'
                        onClick={handleEdit}
                    />
                </div>
            )}
        </Card>
    );
};

export default CardContent;