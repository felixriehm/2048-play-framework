window.Game = window.Game || {};

Game.WebSocket = {

    socket: (function () {

        function onSocketOpen(evt) {
            console.log("WebSocket opened.");
            Game.WebSocket.socket.send("getField");
        }

        function onSocketClose(evt) {
            console.log("WebSocket closed.");
        }

        function onSocketMessage(evt) {
            Game.EventHandler.execute(JSON.parse(evt.data));
        }

        function onSocketError(evt) {
            console.log("WebSocket error.")
        }

        var wsUri = "ws://" + location.host + "/socket";
        var socket = new WebSocket(wsUri);

        socket.onopen = onSocketOpen;
        socket.onclose = onSocketClose;
        socket.onmessage = onSocketMessage;
        socket.onerror = onSocketError;

        return socket;
    })()
};
