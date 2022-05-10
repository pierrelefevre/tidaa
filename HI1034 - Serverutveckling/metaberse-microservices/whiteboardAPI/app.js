var http = require('http');
var WebSocketServer = require('ws').Server;
var server = new WebSocketServer({ port: 9898 });

// connect to mongodb database
// var mongoose = require('mongoose');
// mongoose.connect('mongodb://localhost/test');

var sockets = [];
var whiteboards = [];

console.log("Server started")

server.on('connection', function (socket) {
    socket.on('message', function (message) {
        var data = JSON.parse(message);

        switch (data.type) {
            case 'hello':
                if (sockets[data.id] == null) {
                    sockets[data.id] = [socket];
                    console.log("New canvas connected with id: " + data.id);
                } else {
                    sockets[data.id].push(socket);
                    console.log("New user connected to canvas with id: " + data.id);
                }

                if (whiteboards[data.id] != null) {
                    whiteboards[data.id].forEach(function (item) {
                        socket.send(JSON.stringify(item));
                    });
                }

                break;
            case 'line':
                //save the message
                if (whiteboards[data.id] == null) {
                    whiteboards[data.id] = [data];
                } else {
                    whiteboards[data.id].push(data);
                }

                //distribute the message to all sockets
                sockets[data.id].forEach(function (client) {
                    console.log(data.id + " " + client._socket.remoteAddress)
                    if (client !== socket) {
                        client.send("" + message);
                    }
                });
                break;
            case 'clear':
                //clear the whiteboard
                whiteboards[data.id] = [];

                //distribute the message to all sockets
                sockets[data.id].forEach(function (client) {
                    console.log(data.id + " " + client._socket.remoteAddress)
                    if (client !== socket) {
                        client.send("" + message);
                    }
                });
                break;
            default:
                console.log('unknown message type ' + JSON.stringify(data));
        }
    });

    //socket.send('connected');
});

server.on("close", function (socket) {
    for (var id in sockets) {
        sockets[id].splice(sockets[id].indexOf(socket), 1);
    }
});

// var server = http.createServer(function (request, response) {
//     response.writeHead(200, { 'Content-Type': 'text/plain' });
//     response.end('Hello World\n');
// });

// server.listen(8080, function () {
//     console.log('Server is listening on port 8080');
// });