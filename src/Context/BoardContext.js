import {createContext, useEffect, useState} from 'react';

const BoardContext = createContext();

export const BoardProvider = ({ children }) => {
    const [board, setBoard] = useState(() => {
        const storedBoard = JSON.parse(localStorage.getItem('board'));
        return storedBoard || null;
    });

    useEffect(() => {
        localStorage.setItem('board', JSON.stringify(board));
    }, []);

    const updateBoard = (newBoard) => {
        setBoard(newBoard);
    };

    return (
        <BoardContext.Provider value={{ board, updateBoard }}>
            {children}
        </BoardContext.Provider>
    );
};

export default BoardContext;