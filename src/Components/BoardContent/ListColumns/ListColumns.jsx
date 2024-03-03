import React, {useState} from 'react';
import {horizontalListSortingStrategy, SortableContext} from "@dnd-kit/sortable";
import Column from "./Column/Column";
import CreateColumnButton from "./NewColumn/CreateColumnButton";
import CreateNewColumnForm from "./NewColumn/CreateNewColumnForm";

const ListColumns = ({columns}) => {
    const [openNewColumnForm, setOpenNewColumnForm] = useState(false);
    const toggleOpenNewColumnForm = () => setOpenNewColumnForm(!openNewColumnForm)

    const handleCreateList = (title) => {

    };


    return (
        <SortableContext items={columns?.map(c => c._id)} strategy={horizontalListSortingStrategy}>
            <div className='flex space-x-4'>
                {columns?.map((column) => (
                    <Column column={column}/>
                ))}
            </div>
            {!openNewColumnForm ? <CreateColumnButton toggle={toggleOpenNewColumnForm}/> : <CreateNewColumnForm toggle={toggleOpenNewColumnForm} onSubmit={handleCreateList}/>}
        </SortableContext>
    );
};

export default ListColumns;