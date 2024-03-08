import React, {useState} from 'react';
import {RxPencil1} from "react-icons/rx";
import {Card, Input, Button} from "@chakra-ui/react";

const EditableCard = ({card}) => {
    const [isEditing, setIsEditing] = useState(false);
    const [editedTitle, setEditedTitle] = useState(card.title);

    const handleEdit = () => {
        setIsEditing(true);
    };

    const handleSave = () => {
        setIsEditing(false);
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
        <Card className='rounded-md my-3 p-2'>
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

export default EditableCard;
