$(function () {
    "use strict";
    var content = $('#content');
    var input = $('#input');
    var status = $('#status');
    var myName = false;

    // if user is running mozilla then use it's built-in WebSocket
    window.WebSocket = window.WebSocket || window.MozWebSocket;
    // if browser doesn't support WebSocket, just show
    // some notification and exit
    if (!window.WebSocket) {
        content.html($('<p>',
            { text:'Sorry, but your browser doesn\'t support WebSocket.'}
        ));
        input.hide();
        $('span').hide();
        return;
    }
    // open connection
    var connection = new WebSocket('ws://127.0.0.1:1337');
    connection.onopen = function () {
        // first we want users to enter their names
        input.removeAttr('disabled');
        status.text('Choose name:');
    };
    connection.onerror = function (error) {
        // just in there were some problems with connection...
        content.html($('<p>', {
            text: 'Sorry, but there\'s some problem with your '
            + 'connection or the server is down.'
        }));
    };
    connection.onmessage = function (message) {
        try {
            var json = JSON.parse(message.data);
        } catch (e) {
            console.log('Invalid JSON: ', message.data);
            return;
        }
        if(json.type == 'ready') {
            input.removeAttr('disabled');
            status.text('Send message');
            input.focus();

        } else if (json.type === 'history') { // entire message history
            // insert every single message to the chat window
            for (var i=0; i < json.data.length; i++) {
                addMessage(json.data[i].author, json.data[i].text, new Date(json.data[i].time));
            }
        } else if (json.type === 'message') { // it's a single message
            // let the user write another message
            input.removeAttr('disabled');
            addMessage(json.data.author, json.data.text, new Date(json.data.time));
            input.focus();
        } else {
            console.log('Hmm..., I\'ve never seen JSON like this:', json);
        }
    };
    /**
     * Send message when user presses Enter key
     */
    input.keydown(function(e) {
        if (e.keyCode === 13) {
            var msg = $(this).val();
            if (!msg) {
                return;
            }
            // send the message as an ordinary text
            connection.send(msg);
            $(this).val('');
            // disable the input field to make the user wait until server
            // sends back response
            input.attr('disabled', 'disabled');
            // we know that the first message sent from a user their name
            if (myName === false) {
                myName = msg;
            }

        }
    });
    /**
     * This method is optional. If the server wasn't able to
     * respond to the in 3 seconds then show some error message
     * to notify the user that something is wrong.
     */
    setInterval(function() {
        if (connection.readyState !== 1) {
            status.text('Error');
            input.attr('disabled', 'disabled').val(
                'Unable to communicate with the WebSocket server.');
        }
    }, 3000);
    /**
     * Add message to the chat window
     */
    function addMessage(author, message, dt) {
        $(` <p class="message"><span>
             ${author} </span> <span class="time">(${
            (dt.getHours() < 10 ? '0' + dt.getHours() : dt.getHours()) + ':' 
            + (dt.getMinutes() < 10
                ? '0' + dt.getMinutes() : dt.getMinutes())})</span> : ${message} </p>`).prependTo(content).hide().slideDown();
    }
});