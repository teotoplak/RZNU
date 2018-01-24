"use strict";
process.title = 'node-chat';

const webSocketsServerPort = 1337;
let webSocketServer = require('websocket').server;
let http = require('http');


// latest 100 messages
let history = [];
// list of currently connected clients (users)
let clients = [];


/**
 * Helper function for escaping input strings
 */
function htmlEntities(str) {
    return String(str)
        .replace(/&/g, '&amp;').replace(/</g, '&lt;')
        .replace(/>/g, '&gt;').replace(/"/g, '&quot;');
}

/**
 * HTTP server
 */
var server = http.createServer(function (request, response) {

});
server.listen(webSocketsServerPort, function () {
    console.log((new Date()) + " Server is listening on port "
        + webSocketsServerPort);
});
/**
 * WebSocket server
 */
var wsServer = new webSocketServer({
    // WebSocket server is tied to a HTTP server. WebSocket
    // request is just an enhanced HTTP request
    httpServer: server
});
// This callback function is called every time someone
// tries to connect to the WebSocket server
wsServer.on('request', function (request) {
    console.log((new Date()) + ' Connection from origin '
        + request.origin + '.');
    // accept connection - you should check 'request.origin' to
    // make sure that client is connecting from your website
    // (http://en.wikipedia.org/wiki/Same_origin_policy)
    var connection = request.accept(null, request.origin);
    // we need to know client index to remove them on 'close' event
    var index = clients.push(connection) - 1;
    var userName = false;
    console.log((new Date()) + ' Connection accepted.');
    // send back chat history
    if (history.length > 0) {
        connection.sendUTF(
            JSON.stringify({type: 'history', data: history}));
    }
    // user sent some message
    connection.on('message', function (message) {
        if (message.type === 'utf8') { // accept only text
            // first message sent by user is their name
            if (userName === false) {
                // remember user name
                userName = htmlEntities(message.utf8Data);
                connection.sendUTF(
                    JSON.stringify({type: 'ready'}));
                console.log((new Date()) + ' User is known as: ' + userName);
            } else { // log and broadcast the message
                console.log((new Date()) + ' Received Message from '
                    + userName + ': ' + message.utf8Data);

                // we want to keep history of all sent messages
                var obj = {
                    time: (new Date()).getTime(),
                    text: htmlEntities(message.utf8Data),
                    author: userName
                };
                history.push(obj);
                history = history.slice(-100);
                // broadcast message to all connected clients
                var json = JSON.stringify({type: 'message', data: obj});
                for (var i = 0; i < clients.length; i++) {
                    clients[i].sendUTF(json);
                }
            }
        }
    });
    // user disconnected
    connection.on('close', function (connection) {
        if (userName !== false) {
            console.log((new Date()) + " Peer "
                + connection.remoteAddress + " disconnected.");
            // remove user from the list of connected clients
            clients.splice(index, 1);
        }
    });
});