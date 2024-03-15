import React, {useEffect, useState} from 'react';
import {RxPencil1} from "react-icons/rx";
import {Avatar, AvatarGroup, Button, Card, Input, Tooltip, useDisclosure} from "@chakra-ui/react";
import {useSortable} from "@dnd-kit/sortable";
import {CSS} from "@dnd-kit/utilities";
import CardService from "../../../../../../Service/CardService";
import CardModal from "../../../../../CardModal/CardModal";

const CardContent = ({card}) => {
    const {isOpen, onOpen, onClose} = useDisclosure();
    const [members, setMembers] = useState([]);
    const [labels, setLabels] = useState([]);
    const {
        attributes, listeners, setNodeRef, transform, transition, isDragging,
    } = useSortable({id: card.id, data: {...card}});

    const dndKitCardStyle = {
        transform: CSS.Translate.toString(transform),
        transition,
        opacity: isDragging ? 0.5 : undefined,
        border: isDragging ? '1px solid #2ecc71' : undefined
    };

    useEffect(() => {
        const fetchMembers = async () => {
            try {
                const response = await CardService.showMemberToCard(card.id);
                setMembers(response.data);
            } catch (error) {
                console.error('Error fetching members:', error);
            }
        };

        fetchMembers();
        const fetchLabels = async () => {
            try {
                const response = await CardService.showLabelToCard(card.id);
                setLabels(response.data);
            } catch (error) {
                console.error('Error fetching labels:', error);
            }
        };

        fetchLabels();
    }, [card]);

    const handleOpenModal = () => {
        onOpen();
    }
    const [selectedColors, setSelectedColors] = useState([]);

    const toggleVisibility = (color) => {
        if (selectedColors.includes(color)) {
            setSelectedColors(selectedColors.filter((c) => c !== color));
        } else {
            setSelectedColors([...selectedColors, color]);
        }
    };

    return (
        <div>
        <CardModal isOpen={isOpen} onOpen={onOpen} onClose={onClose} toggleVisibility={toggleVisibility}
                   card={card} showMembers={members}
                  />
        <Card ref={setNodeRef} style={dndKitCardStyle} {...attributes} {...listeners}
            key={card.id}
            className='rounded-md my-3 p-2'>
                <div className='flex flex-row justify-between items-center'>
                    <div className='flex flex-col max-w-[88%]'>
                        <div className="flex">
                            {labels.map((label) => (
                                // <div className={`w-7 h-2 bg-${label.color}-500 ml-2 rounded-full ${selectedColors.includes(label.color) ? '' : 'hidden'}`}></div>
                                <div  className={`w-7 h-2 bg-${label.color}-500 ml-2 rounded-full`}></div>
                            ))}

                        </div>
                        <p className='text-sm font-medium'>{card.title}</p>
                    </div>
                    <div className='flex flex-col'>
                        <RxPencil1
                            className='text-2xl p-1 rounded-full hover:bg-gray-200 cursor-pointer'
                            onClick={handleOpenModal}
                        />
                        <AvatarGroup className='mt-3' size='xs' max={2}>
                            {members.map((member) => (
                                <Tooltip key={member.id} label={member.username}>
                                <Avatar key={member.id} name={member.username} src={member.avatarUrl} boxSize='20px'/>
                                </Tooltip>
                                ))}
                        </AvatarGroup>
                    </div>
                </div>
        </Card>
            </div>
    );
};

export default CardContent;