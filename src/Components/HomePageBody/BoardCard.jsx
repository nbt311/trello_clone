import React from 'react';

const BoardCard = () => {
    const imageUrl = 'https://firebasestorage.googleapis.com/v0/b/trelloimageupload.appspot.com/o/data%2F56b1e5cc-ecb3-490b-a3e0-a3d2792f4016?alt=media&token=d61aaa92-8c0b-4620-9f37-b342343fe888'

    const divStyle = {
        backgroundImage: `url('${imageUrl}')`,
        backgroundSize: 'cover', // Thêm thuộc tính backgroundSize
        // backgroundPosition: 'center'
    };

    return (
        <div className='w-[20%] h-24 rounded-sm flex text-start' style={divStyle}>
            <h1 className='mt-1 ml-3 text-xl font-extrabold text-white'>daad</h1>
        </div>
    );
};

export default BoardCard;