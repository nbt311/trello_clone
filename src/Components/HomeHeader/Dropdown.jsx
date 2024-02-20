import React from 'react';
import {IoChevronDownSharp} from "react-icons/io5";

const Dropdown = ({title}) => {
    return (
        <div className='flex items-center flex-row space-x-1'>
            <button className='font-medium'>{title}</button>
            <IoChevronDownSharp className='mt-1'/>
        </div>
    );
};

export default Dropdown;