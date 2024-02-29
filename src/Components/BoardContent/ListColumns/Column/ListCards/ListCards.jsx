import React from 'react';
import {RxPencil1} from "react-icons/rx";
import {Card} from "@chakra-ui/react";
import {horizontalListSortingStrategy, SortableContext, verticalListSortingStrategy} from "@dnd-kit/sortable";
import CardContent from "./CardContent/CardContent";

const ListCards = ({cards}) => {
    return (
        <SortableContext items={cards?.map(c => c._id)} strategy={verticalListSortingStrategy}>
        <div>
            {cards?.map((card) => (
                <CardContent card={card}/>
            ))}
        </div>
        </SortableContext>
    );
};

export default ListCards;