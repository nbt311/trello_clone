import React, {useContext, useEffect, useState} from 'react';
import HomeHeader from "../../Components/HomeHeader/HomeHeader";
import ListColumns from "../../Components/BoardContent/ListColumns/ListColumns";
import {
    closestCorners,
    defaultDropAnimationSideEffects,
    DndContext,
    DragOverlay,
    PointerSensor,
    useSensor,
    useSensors
} from "@dnd-kit/core";
import {mapOrder} from "../../Utils/Sort";
import {arrayMove} from "@dnd-kit/sortable";
import Column from "../../Components/BoardContent/ListColumns/Column/Column";
import CardContent from "../../Components/BoardContent/ListColumns/Column/ListCards/CardContent/CardContent";
import {cloneDeep} from "lodash";
import BoardContext from "../../Context/BoardContext";
import BoardService from "../../Service/BoardService";

const ACTIVE_DRAG_ITEM_TYPE = {
    COLUMN: 'ACTIVE_DRAG_ITEM_TYPE_COLUMN',
    CARD: 'ACTIVE_DRAG_ITEM_TYPE_CARD'
}

const BoardContentPage = () => {
    const { board, updateBoard } = useContext(BoardContext);

    useEffect(() => {
        const storedBoard = localStorage.getItem('board');

        if (storedBoard) {
            updateBoard(JSON.parse(storedBoard));
        }
    }, []);

    const imageUrl = 'https://marketplace.canva.com/EAE-g6znT-s/1/0/1600w/canva-soft-purple-fun-modern-minimalist-cats-hi-desktop-wallpaper-Gqj2XviD4_E.jpg'
    const divStyle = {
        backgroundImage: `url('${imageUrl}')`,
        backgroundSize: 'cover',
        backgroundPosition: 'center'
    };

    const [orderedColumns, setOrderedColumns] = useState([])

    const [activeDragItemId, setActiveDragItemId] = useState(null)
    const [activeDragItemType, setActiveDragItemType] = useState(null)
    const [activeDragItemData, setActiveDragItemData] = useState(null)

    const [oldColumn, setOldColumn] = useState(null)

    const pointerSensor = useSensor(PointerSensor, {activationConstraint: {distance: 10}})
    const sensors = useSensors(pointerSensor)

    useEffect(() => {
        setOrderedColumns(mapOrder(board?.columns, board?.columnOrderIds, 'id'))
    }, [board])

    const moveColumns = (dndOrderedColumns) => {
        // Gọi API cập nhật dữ liệu column vào back end
        const dndOrderedColumnsIds = dndOrderedColumns.map(c => c.id)
        console.log("Column Ids" ,dndOrderedColumnsIds)

        const newBoard = {...board}
        newBoard.columns = dndOrderedColumns
        newBoard.columnOrderIds = dndOrderedColumnsIds
        updateBoard(newBoard)
        localStorage.setItem('board', JSON.stringify(newBoard));

        BoardService.updateBoardDetail(newBoard.id, { columnOrderIds: dndOrderedColumnsIds })
    }


    // const findColumnByCardId = (cardId) => {
    //     return orderedColumns.find(column => column?.cards?.map(card => card.id)?.includes(cardId))
    // }
    //
    // const moveCardBetweenDifferentColumns = (
    //     overColumn,
    //     overCardId,
    //     active,
    //     over,
    //     activeColumn,
    //     activeDraggingCardId,
    //     activeDraggingCardData
    // ) => {
    //     setOrderedColumns(prevColumns => {
    //         const overCardIndex = overColumn?.cards?.findIndex(card => card.id === overCardId)
    //         let newCardIndex
    //
    //         const isBelowOverItem = active.rect.current.translated &&
    //             active.rect.current.translated.top >
    //             over.rect.top + over.rect.height;
    //         const modifier = isBelowOverItem ? 1 : 0;
    //
    //         newCardIndex = overCardIndex >= 0 ? overCardIndex + modifier : overColumn?.cards?.length + 1;
    //
    //         const nextColumns = cloneDeep(prevColumns)
    //         const nextActiveColumns = nextColumns.find(column => column.id === activeColumn.id)
    //         const nextOverColumns = nextColumns.find(column => column.id === overColumn.id)
    //
    //         if (nextActiveColumns) {
    //             nextActiveColumns.cards = nextActiveColumns.cards.filter(card => card.id !== activeDraggingCardId)
    //             nextActiveColumns.cardOrderIds = nextActiveColumns.cards.map(card => card.id)
    //         }
    //
    //         const rebuild_activeDraggingCardData = {
    //             ...activeDraggingCardData,
    //             columnId: nextOverColumns.id
    //         }
    //
    //         if (nextOverColumns) {
    //             nextOverColumns.cards = nextOverColumns.cards.filter(card => card.id !== activeDraggingCardId)
    //             nextOverColumns.cards = nextOverColumns.cards.toSpliced(newCardIndex, 0, rebuild_activeDraggingCardData)
    //             nextOverColumns.cardOrderIds = nextOverColumns.cards.map(card => card.id)
    //         }
    //         return nextColumns;
    //     })
    // }

    const handleDragStart = (event) => {
        setActiveDragItemId(event?.active?.id)
        setActiveDragItemType(event?.active?.data?.current?.columnId ? ACTIVE_DRAG_ITEM_TYPE.CARD : ACTIVE_DRAG_ITEM_TYPE.COLUMN)
        setActiveDragItemData(event?.active?.data?.current)

    //     if (event?.active?.data?.current?.columnId) {
    //         setOldColumn(findColumnByCardId(event?.active?.id))
    //     }
    // }
    //
    // const handleDragOver = (event) => {
    //     if (activeDragItemType === ACTIVE_DRAG_ITEM_TYPE.COLUMN) return
    //
    //     const {active, over} = event
    //     if (!active || !over) return
    //
    //     const {id: activeDraggingCardId, data: {current: activeDraggingCardData}} = active
    //     const {id: overCardId} = over
    //
    //     const activeColumn = findColumnByCardId(activeDraggingCardId)
    //     const overColumn = findColumnByCardId(overCardId)
    //
    //     if (!activeColumn || !overColumn) return
    //
    //     if (activeColumn.id !== overColumn.id) {
    //        moveCardBetweenDifferentColumns(
    //             overColumn,
    //                 overCardId,
    //                 active,
    //                 over,
    //                 activeColumn,
    //                 activeDraggingCardId,
    //                 activeDraggingCardData
    //         )
    //     }
    }

    const handleDragEnd = (event) => {
        const {active, over} = event
        if (!active || !over) return

        // if (activeDragItemType === ACTIVE_DRAG_ITEM_TYPE.CARD) {
        //     const {id: activeDraggingCardId, data: {current: activeDraggingCardData}} = active
        //     const {id: overCardId} = over
        //
        //     const activeColumn = findColumnByCardId(activeDraggingCardId)
        //     const overColumn = findColumnByCardId(overCardId)
        //
        //     if (!activeColumn || !overColumn) return
        //
        //     if (oldColumn.id !== overColumn.id) {
        //         moveCardBetweenDifferentColumns(
        //             overColumn,
        //             overCardId,
        //             active,
        //             over,
        //             activeColumn,
        //             activeDraggingCardId,
        //             activeDraggingCardData
        //         )
        //     } else {
        //         const oldCardIndex = oldColumn?.cards?.findIndex(c => c.id === activeDragItemId)
        //         const newCardIndex = overColumn?.cards?.findIndex(c => c.id === overCardId)
        //         const dndOrderedCards = arrayMove(oldColumn?.cards, oldCardIndex, newCardIndex)
        //         setOrderedColumns(prevColumns => {
        //             const nextColumns = cloneDeep(prevColumns)
        //             const targetColumn = nextColumns.find(column => column.id === overColumn.id)
        //
        //             targetColumn.cards = dndOrderedCards
        //             targetColumn.cardOrderIds = dndOrderedCards.map(card => card.id)
        //             return nextColumns
        //         })
        //     }
        // }

        if (activeDragItemType === ACTIVE_DRAG_ITEM_TYPE.COLUMN) {
            if (active.id !== over.id) {
                const oldColumnIndex = orderedColumns.findIndex(c => c.id === active.id)
                const newColumnIndex = orderedColumns.findIndex(c => c.id === over.id)

                const dndOrderedColumns = arrayMove(orderedColumns, oldColumnIndex, newColumnIndex)
                moveColumns(dndOrderedColumns)

                setOrderedColumns(dndOrderedColumns)
            }
        }

        setActiveDragItemId(null)
        setActiveDragItemType(null)
        setActiveDragItemData(null)
        // setOldColumn(null)
    }

    const customDropAnimation = {
        sideEffects: defaultDropAnimationSideEffects({
            styles: {
                active: {
                    opacity: '0.5',
                },
            },
        }),
    };


    return (
        <div className='h-dvh w-dvw max-w-full overflow-y-hidden'>
            <div>
                <HomeHeader/>
            </div>


            <div className='h-full' style={divStyle}>
                <DndContext sensors={sensors}
                            collisionDetection={closestCorners}
                            onDragStart={handleDragStart}
                            // onDragOver={handleDragOver}
                            onDragEnd={handleDragEnd}
                >
                    <div className='flex h-full p-3 space-x-4 overflow-x-scroll'>
                        <ListColumns columns={orderedColumns} setColumns={setOrderedColumns}/>
                        <DragOverlay dropAnimation={customDropAnimation}>
                            {!activeDragItemType && null}
                            {(activeDragItemType === ACTIVE_DRAG_ITEM_TYPE.COLUMN) &&
                                <Column column={activeDragItemData}/>}
                            {(activeDragItemType === ACTIVE_DRAG_ITEM_TYPE.CARD) &&
                                <CardContent card={activeDragItemData}/>}
                        </DragOverlay>
                    </div>
                </DndContext>
            </div>
        </div>
    );
};

export default BoardContentPage;