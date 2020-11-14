window.Game = window.Game || {};

window.addEventListener("keyup", function (evt) {
    Game.InputHandler.input(evt.keyCode || evt.which);
});

document.getElementById("newgame").addEventListener("click", function() {
    Game.InputHandler.startGameWithSize();
});

document.getElementById("size-input").addEventListener("click", function(evt) {
    evt.stopPropagation();
    return false;
});

document.getElementById("ai-step").addEventListener("click", function() {
    Game.WebSocket.socket.send("step");
});

Game.AiInterval = null;

document.getElementById("ai-run").addEventListener("click", function() {
    if (Game.AiInterval === null) {
        Game.WebSocket.socket.send("step");
        Game.AiInterval = window.setInterval(function() {
            Game.WebSocket.socket.send("step");
        }, 500)
    } else {
        window.clearInterval(Game.AiInterval);
        Game.AiInterval = null;
    }

});

Game.InputHandler = {

    input: function (keyCode) {
        if (Game.Config.keyBindings.hasOwnProperty(keyCode)) {
            Game.WebSocket.socket.send(Game.Config.keyBindings[keyCode]);
        } else if (keyCode === 13) {// enter
            if ($('#size-input').is(':focus')) {
                Game.InputHandler.startGameWithSize();
            }
        }
    },

    startGameWithSize: function() {
        var sizeInput = document.getElementById("size-input");
        var size = parseInt(sizeInput.value);
        if (!size) {
            size = 4;
        }
        Game.WebSocket.socket.send('n' + size);
        sizeInput.blur();
    }

};