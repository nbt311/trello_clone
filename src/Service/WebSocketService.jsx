import { io } from 'socket.io-client';

const socket = io('http://localhost:8080/data'); // Thay thế bằng địa chỉ WebSocket của backend

const WebSocketService = {
    connect: () => {
        socket.connect();
    },

    disconnect: () => {
        socket.disconnect();
    },

    onCommentNotification: (callback) => {
        socket.on('commentNotification', (notification) => {
            callback(notification);
        });
    },
};

export default WebSocketService;