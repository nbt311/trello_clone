// import React, { useState, useEffect } from 'react';
// import io from 'socket.io-client';
//
// const CommentComponent = () => {
//     const [comments, setComments] = useState([]);
//     const [content, setContent] = useState('');
//     const socket = io('http://localhost:8080/data'); // Thay đổi URL server của bạn
//
//     useEffect(() => {
//         socket.on('/topic/comments', (newComment) => {
//             setComments((prevComments) => [...prevComments, newComment]);
//         });
//
//         return () => {
//             socket.disconnect();
//         };
//     }, [socket]);
//
//     const handleCommentSubmit = () => {
//         const newComment = {
//             content,
//             username: sessionStorage.getItem('username'),
//         };
//
//         socket.emit('/app/addComment', newComment);
//         setContent('');
//     };
//
//     return (
//         <div>
//             <ul>
//                 {comments.map((comment, index) => (
//                     <li key={index}>{comment.username}: {comment.content}</li>
//                 ))}
//             </ul>
//             <textarea value={content} onChange={(e) => setContent(e.target.value)} />
//             <button onClick={handleCommentSubmit}>Submit</button>
//         </div>
//     );
// };
//
// export default CommentComponent;