import React, {useContext, useState} from 'react';
import {RxPencil1} from "react-icons/rx";
import {Card, Input, useToast} from "@chakra-ui/react";
import {IoMdAdd, IoMdClose} from "react-icons/io";
import ColumnService from "../../../../../../Service/ColumnService";
import BoardService from "../../../../../../Service/BoardService";
import CardService from "../../../../../../Service/CardService";
import BoardContext from "../../../../../../Context/BoardContext";
import column from "../../Column";

const CreateNewCardForm = ({handleCreateCard, column, isCreateCard}) => {
    const [cardTitle, setCardTitle] = useState('');
    const {board, updateBoard} = useContext(BoardContext);

    const toast = useToast()

    const createNewCard = async () => {
        try {
            const response = await CardService.createNewCard(cardTitle, board.id, column.id);
            toast({
                title: 'Create Card Successful',
                description: 'You have successfully card a new column.',
                status: 'success',
                duration: 3000,
            });
            return response.data;
        } catch (error) {
            console.error('Error creating column', error);
            throw error;
        }
    };

    const handleSubmit = async () => {
        try {
            if (!cardTitle.trim()) {
                toast.closeAll();
                toast({
                    title: 'Create Card Fail',
                    description: 'Card title cannot be empty.',
                    status: 'error',
                    duration: 2000,
                });
                return;
            }

            const newCardList = await createNewCard();
            console.log("Column ", column)
            console.log("Update local and json column: ", newCardList)
            updateBoard(newCardList)
            localStorage.setItem('board', JSON.stringify(newCardList))
            setCardTitle(null)
            handleCreateCard()
        } catch (error) {
            console.error(error)
        }

    }

    return (
        <div>
            <Card className='rounded-md my-3 p-2'>
                <Input className='outline-none placeholder:text-sm pb-5 break-words'
                       placeholder='Enter a title for this card...'
                       variant='unstyled'
                       value={cardTitle}
                       onChange={e => setCardTitle(e.target.value)}
                ></Input>
            </Card>

            <div className='flex items-center space-x-2 font-medium text-black p-1 -ml-1'>
                <button type="submit"
                        className='bg-blue-500 hover:bg-blue-600 py-1 px-3 text-white font-semibold rounded-md'
                        onClick={handleSubmit}>
                    Add card
                </button>
                <IoMdClose className='text-3xl p-1 hover:bg-gray-300 rounded-md' onClick={handleCreateCard}/>
            </div>
        </div>
    );
};

export default CreateNewCardForm;