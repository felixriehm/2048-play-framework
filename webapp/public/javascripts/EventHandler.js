window.Game = window.Game || {};

Game.EventHandler = {

    execute: function (evt) {
        var evtFnName = evt.name.charAt(0).toLowerCase() + evt.name.slice(1);
        if (typeof Game.EventHandler[evtFnName] === "function") {
            Game.EventHandler[evtFnName](evt);
        }
    },

    fieldChangedEvent: function (evt) {
        Game.Field.setField(evt.field, evt.size);
    },

    scoresGainedEvent: function (evt) {
        Game.Animation.animateScoreGained(evt.score);
    },

    gameOverEvent: function (evt) {
        window.clearInterval(Game.AiInterval);
        Game.AiInterval = null;
        Game.Animation.animateGameOver();
    },

    newGameStartedEvent: function (evt) {
        window.clearInterval(Game.AiInterval);
        Game.AiInterval = null;
        Game.Animation.animateNewGame();
    },

    scoreChangedEvent: function (evt) {
        $("#score").text(evt.score);
    },

    tileAddedEvent: function (evt) {
        Game.Animation.animateTileAdded(evt.pos, evt.number);
    },

    slideEvent: function (evt) {
        Game.Animation.animateSlide(evt.start, evt.end);
    },

    mergeEvent: function (evt) {
        Game.Animation.animateMerge(evt.pos, evt.number);
    }

};