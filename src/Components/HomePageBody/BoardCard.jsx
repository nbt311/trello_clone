import React from 'react';

const BoardCard = ({board}) => {
    const imageUrl = 'https://marketplace.canva.com/EAE-g6znT-s/1/0/1600w/canva-soft-purple-fun-modern-minimalist-cats-hi-desktop-wallpaper-Gqj2XviD4_E.jpg'
    const divStyle = {
        backgroundImage: `url('${imageUrl}')`,
        backgroundSize: 'cover', // Thêm thuộc tính backgroundSize
        // backgroundPosition: 'center'
    };

    return (
        <div className='h-24 w-full rounded-sm flex text-start' style={divStyle}>
            <h1 className='mt-1 ml-3 text-xl font-extrabold text-black'>{board?.title}</h1>
        </div>
    );
};

export default BoardCard;