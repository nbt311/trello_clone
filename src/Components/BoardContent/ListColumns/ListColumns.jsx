import React from 'react';
import {horizontalListSortingStrategy, SortableContext} from "@dnd-kit/sortable";
import Column from "./Column/Column";

const ListColumns = ({columns}) => {
    return (
        <SortableContext items={columns?.map(c => c._id)} strategy={horizontalListSortingStrategy}>
            <div className='flex space-x-4'>
                {columns?.map((column) => (
                    <Column column={column}/>
                ))}
            </div>
        </SortableContext>
    );
};

export default ListColumns;