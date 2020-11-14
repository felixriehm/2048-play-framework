window.Game = window.Game || {};

Game.Animation = {

    tileAnimationSize: 10,

    getAnimationCopy: function (row, col) {
        var tile = Game.Field.getTileEl(row, col);
        var copy;
        if (tile) {
            copy = tile.clone();
            copy.css({
                position: 'absolute',
                left: tile[0].offsetLeft,
                top: tile[0].offsetTop
            });
        }

        return copy;
    },

    animateSlide: function (from, to) {
        var animationTile = Game.Animation.getAnimationCopy(from[0], from[1]);
        Game.Config.fieldElement.append(animationTile);

        var moveLeft = (to[0] - from[0]) * Game.Config.tileSize;
        var moveTop = (to[1] - from[1]) * Game.Config.tileSize;

        animationTile.animate({
                left: animationTile[0].offsetLeft + moveLeft,
                top: animationTile[0].offsetTop + moveTop
            },
            'fast',
            function () {
                animationTile.remove();
            });

    },

    animateTileAdded: function (pos, number) {
        var animationTile = Game.Animation.getAnimationCopy(pos[0], pos[1]);
        animationTile.text(number);
        animationTile.css({
            backgroundColor: Game.Config.getColorFor(number)
        });
        Game.Config.fieldElement.append(animationTile);

        animationTile.animate({
                width: Game.Config.tileSize + Game.Animation.tileAnimationSize,
                height: Game.Config.tileSize + Game.Animation.tileAnimationSize,
                left: animationTile[0].offsetLeft - (Game.Animation.tileAnimationSize / 2),
                top: animationTile[0].offsetTop - (Game.Animation.tileAnimationSize / 2)
            },
            'fast');
        animationTile.animate({
                width: Game.Config.tileSize,
                height: Game.Config.tileSize,
                left: animationTile[0].offsetLeft,
                top: animationTile[0].offsetTop
            },
            'fast',
            function () {
                animationTile.remove();
            });
    },

    animateMerge: function (pos, number) {
        Game.Animation.animateTileAdded(pos, number);
    },

    animateGameOver: function () {
        var fieldSize = Game.Config.tileSize * Game.Field.size;
        Game.Config.gameOverScreen.css({
            width: fieldSize + "px",
            height: fieldSize + "px",
            'font-size': fieldSize / 8,
            'line-height': fieldSize + "px"
        });
        Game.Config.gameOverScreen.show();
    },

    animateNewGame: function () {
        Game.Config.gameOverScreen.hide();
    },

    animateScoreGained: function (score) {
        var el = Game.Config.scoreGainElement;
        el.text('+' + score);
        el.stop();
        el.animate({
            opacity: .5,
            top: -15
        }, 100);
        el.animate({
                opacity: 0,
                top: -30
            },
            100,
            function () {
                el.css({
                    top: 0
                });
            });
    }

};