/*
* Socket logic interface for Group Chats
*/
const socketIo = require('socket.io');
const db = require('../Databases/Database.js');

const allowedOrigins = [
    'http://10.0.2.2:3000',
    '0.0.0.0',
]

class ChatManager {
    constructor(server) {
        this.initSocketIo(server);
    }
    
    // ChatGPT usage: Partial
    initSocketIo(server) {
        // ChatGPT usage: All
        const io = socketIo(server, {
            cors: {
                origin: allowedOrigins,
                methods: ['GET', 'POST'],
            }
        });
        
        // ChatGPT usage: Partial
        io.on('connection', (socket) => {
            console.log('A user connected');

            // ChatGPT usage: Partial
            socket.on('joinChatroom', (chatName) => {
                socket.join(chatName);
                socket.chatName = chatName;
            });
            
            // ChatGPT usage: Partial
            socket.on('leaveChatroom', () => {
                if (socket.chatName) {
                    socket.leave(socket.chatName);
                    socket.chatName = null;
                }
            });

            // ChatGPT usage: Partial
            socket.on('sendMessage', (message, sender) => { // message string and username
                if (socket.chatName) {
                    // format timestamp string
                    const now = new Date();
                    const year = now.getFullYear();
                    const month = String(now.getMonth() + 1).padStart(2, '0');
                    const day = String(now.getDate()).padStart(2, '0');
                    const hours = String(now.getHours()).padStart(2, '0');
                    const minutes = String(now.getMinutes()).padStart(2, '0');
                    const seconds = String(now.getSeconds()).padStart(2, '0');

                    let messageObj = {
                        sender: sender,
                        message: message,
                        timestamp: `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`,
                    }
                    // Broadcast the message to all sockets in the chatroom except the sender
                    socket.to(socket.chatName).except(socket.id).emit('message', messageObj);
                    db.addMessage(socket.chatName, messageObj); // store message in database
                }
            });

            // ChatGPT usage: All
            socket.on('disconnect', () => {
                console.log('A user disconnected');
                if (socket.chatName) {
                    socket.leave(socket.chatName);
                }
            });
        });
    }
}

module.exports = ChatManager;