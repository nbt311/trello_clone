import React, {useEffect, useState} from 'react';
import HomeHeader from "../../Components/HomeHeader/HomeHeader";
import ListColumns from "../../Components/BoardContent/ListColumns/ListColumns";
import CreateColumnButton from "../../Components/BoardContent/CreateColumnButton";
import {DndContext, PointerSensor, useSensor, useSensors} from "@dnd-kit/core";
import {mapOrder} from "../../Utils/Sort";
import {arrayMove} from "@dnd-kit/sortable";

const BoardContentPage = ({board}) => {
    const imageUrl = 'https://firebasestorage.googleapis.com/v0/b/trelloimageupload.appspot.com/o/data%2F56b1e5cc-ecb3-490b-a3e0-a3d2792f4016?alt=media&token=d61aaa92-8c0b-4620-9f37-b342343fe888'
    const divStyle = {
        backgroundImage: `url('${imageUrl}')`,
        backgroundSize: 'cover',
        backgroundPosition: 'center'
    };

    const [orderedColumns, setOrderedColumns] = useState([])
    const pointerSensor = useSensor(PointerSensor, {activationConstraint: {distance: 10}})
    const sensors = useSensors(pointerSensor)

    useEffect(() => {
        setOrderedColumns(mapOrder(board?.columns, board?.columnOrderIds, '_id'))
    }, [board])

    const handleDragEnd = (event) => {
        console.log('handleDragEnd: ', event)
        const {active, over} = event

        if (!over) return

        if (active.id !== over.id) {
            const oldIndex = orderedColumns.findIndex(c => c._id === active.id)
            const newIndex = orderedColumns.findIndex(c => c._id === over.id)

            const dndOrderedColumns = arrayMove(orderedColumns, oldIndex, newIndex)
            const dndOrderedColumnsIds = dndOrderedColumns.map(c => c._id)

            setOrderedColumns(dndOrderedColumns)
        }
    }

    return (
        <div className='h-dvh w-dvw max-w-full overflow-y-hidden'>
            <div>
                <HomeHeader/>
            </div>

            {/*<div>*/}
            {/*    <p>{board.title}</p>*/}
            {/*</div>*/}

            <div className='h-full' style={divStyle}>
                <DndContext onDragEnd={handleDragEnd} sensors={sensors}>
                    <div className='flex h-full p-3 space-x-4 max-w-full overflow-x-scroll'>
                        <ListColumns columns={orderedColumns}/>
                        <CreateColumnButton/>
                    </div>
                </DndContext>
            </div>
        </div>
    );
};

export default BoardContentPage;