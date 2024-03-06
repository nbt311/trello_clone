import React, {useState} from 'react';
import {horizontalListSortingStrategy, SortableContext} from "@dnd-kit/sortable";
import Column from "./Column/Column";
import CreateColumnButton from "./NewColumn/CreateColumnButton";
import CreateNewColumnForm from "./NewColumn/CreateNewColumnForm";

const ListColumns = ({columns, setColumns}) => {
    const [openNewColumnForm, setOpenNewColumnForm] = useState(false);
    const toggleOpenNewColumnForm = () => setOpenNewColumnForm(!openNewColumnForm)

    const handleCreateList = (title) => {

    };


    return (
        <SortableContext items={columns?.map(c => c.id)} strategy={horizontalListSortingStrategy}>
            <div className='flex space-x-4'>
                {columns?.map((column) => (
                    <Column key={column.id} column={column}/>
                ))}
            </div>
            {!openNewColumnForm ? <CreateColumnButton toggle={toggleOpenNewColumnForm}/> :
                <CreateNewColumnForm columns={columns} setColumns={setColumns} toggle={toggleOpenNewColumnForm} onSubmit={handleCreateList}/>}
        </SortableContext>
    );
};

export default ListColumns;