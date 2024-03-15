export const mockData = {
    board: {
        _id: 'board-id-01',
        title: 'Board title',
        description: 'Pro MERN stack Course',
        type: 'public',
        ownerIds: [],
        memberIds: [],
        columnOrderIds: ['column-id-01', 'column-id-02', 'column-id-03', 'column-id-04', 'column-id-05'], // Thứ tự sắp xếp / vị trí của các Columns trong 1 boards
        columns: [
            {
                _id: 'column-id-01',
                boardId: 'board-id-01',
                title: 'To Do',
                cardOrderIds: ['card-id-01', 'card-id-02', 'card-id-03', 'card-id-04', 'card-id-05', 'card-id-06', 'card-id-07'],
                cards: [
                    {
                        _id: 'card-id-01',
                        boardId: 'board-id-01',
                        columnId: 'column-id-01',
                        title: 'Title of card 01',
                        description: 'Markdown Syntax (sẽ ở khóa nâng cao nhé)',
                        memberIds: ['test-user-id-01'],
                        comments: ['test comment 01', 'test comment 02'],
                        attachments: ['test attachment 01', 'test attachment 02', 'test attachment 03']
                    },
                    { _id: 'card-id-02', boardId: 'board-id-01', columnId: 'column-id-01', title: 'Title of card 02', description: null, cover: null, memberIds: [], comments: [], attachments: [] },
                    { _id: 'card-id-03', boardId: 'board-id-01', columnId: 'column-id-01', title: 'Title of card 03', description: null, cover: null, memberIds: [], comments: [], attachments: [] },
                    { _id: 'card-id-04', boardId: 'board-id-01', columnId: 'column-id-01', title: 'Title of card 04', description: null, cover: null, memberIds: [], comments: [], attachments: [] },
                    { _id: 'card-id-05', boardId: 'board-id-01', columnId: 'column-id-01', title: 'Title of card 05', description: null, cover: null, memberIds: [], comments: [], attachments: [] },
                    { _id: 'card-id-06', boardId: 'board-id-01', columnId: 'column-id-01', title: 'Title of card 06', description: null, cover: null, memberIds: [], comments: [], attachments: [] },
                    { _id: 'card-id-07', boardId: 'board-id-01', columnId: 'column-id-01', title: 'Title of card 07', description: null, cover: null, memberIds: [], comments: [], attachments: [] }
                ]
            },
            {
                _id: 'column-id-02',
                boardId: 'board-id-01',
                title: 'Doing',
                cardOrderIds: ['card-id-08', 'card-id-09', 'card-id-10'],
                cards: [
                    { _id: 'card-id-08', boardId: 'board-id-01', columnId: 'column-id-02', title: 'Title of card 08', description: null, cover: null, memberIds: [], comments: [], attachments: [] },
                    { _id: 'card-id-09', boardId: 'board-id-01', columnId: 'column-id-02', title: 'Title of card 09', description: null, cover: null, memberIds: [], comments: [], attachments: [] },
                    { _id: 'card-id-10', boardId: 'board-id-01', columnId: 'column-id-02', title: 'Title of card 10', description: null, cover: null, memberIds: [], comments: [], attachments: [] }
                ]
            },
            {
                _id: 'column-id-03',
                boardId: 'board-id-01',
                title: 'Done',
                cardOrderIds: ['card-id-11', 'card-id-12', 'card-id-13'],
                cards: [
                    { _id: 'card-id-11', boardId: 'board-id-01', columnId: 'column-id-03', title: 'Title of card 11', description: null, cover: null, memberIds: [], comments: [], attachments: [] },
                    { _id: 'card-id-12', boardId: 'board-id-01', columnId: 'column-id-03', title: 'Title of card 12', description: null, cover: null, memberIds: [], comments: [], attachments: [] },
                    { _id: 'card-id-13', boardId: 'board-id-01', columnId: 'column-id-03', title: 'Title of card 13', description: null, cover: null, memberIds: [], comments: [], attachments: [] }
                ]
            },
            {
                _id: 'column-id-04',
                boardId: 'board-id-01',
                title: 'Review',
                cardOrderIds: ['card-id-14', 'card-id-15', 'card-id-16', 'card-id-17'],
                cards: [
                    { _id: 'card-id-14', boardId: 'board-id-01', columnId: 'column-id-04', title: 'Title of card 14', description: null, cover: null, memberIds: [], comments: [], attachments: [] },
                    { _id: 'card-id-15', boardId: 'board-id-01', columnId: 'column-id-04', title: 'Title of card 15', description: null, cover: null, memberIds: [], comments: [], attachments: [] },
                    { _id: 'card-id-16', boardId: 'board-id-01', columnId: 'column-id-04', title: 'Title of card 16', description: null, cover: null, memberIds: [], comments: [], attachments: [] },
                    { _id: 'card-id-17', boardId: 'board-id-01', columnId: 'column-id-04', title: 'Title of card 17', description: null, cover: null, memberIds: [], comments: [], attachments: [] }
                ]
            },
            {
                _id: 'column-id-05',
                boardId: 'board-id-01',
                title: 'Bug',
                cardOrderIds: ['card-id-18', 'card-id-19', 'card-id-20'],
                cards: [
                    { _id: 'card-id-18', boardId: 'board-id-01', columnId: 'column-id-05', title: 'Title of card 18', description: null, cover: null, memberIds: [], comments: [], attachments: [] },
                    { _id: 'card-id-19', boardId: 'board-id-01', columnId: 'column-id-05', title: 'Title of card 19', description: null, cover: null, memberIds: [], comments: [], attachments: [] },
                    { _id: 'card-id-20', boardId: 'board-id-01', columnId: 'column-id-05', title: 'Title of card 20', description: null, cover: null, memberIds: [], comments: [], attachments: [] }
                ]
            }
        ]
    }
}