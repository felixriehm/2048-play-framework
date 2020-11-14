window.Game = window.Game || {};

Game.Config = {

    fieldElement: $('#field'),

    gameOverScreen: $('#gameOverScreen'),

    scoreGainElement: $('#scoreGain'),

    tileSize: 100,

    keyBindings: {
        38: "w",
        87: "w",
        65: "a",
        37: "a",
        83: "s",
        40: "s",
        68: "d",
        39: "d",
        78: "n4"
    },

    colors: [
        "#DDDDDD", //    0
        "#DFCFCF", //    2
        "#E1C1C1", //    4
        "#E3B4B4", //    8
        "#E6A6A6", //   16
        "#E89898", //   32
        "#EA8A8A", //   64
        "#EC7C7C", //  128
        "#EE6F6F", //  256
        "#F06161", //  512
        "#F25353", // 1024
        "#F44545"  // 2048
    ],

    getColorFor: function (number) {
        var colorIndex;
        switch (number) {
            case 0:
                colorIndex = 0;
                break;
            default:
                colorIndex = Math.round(Math.log(number) / Math.log(2));
                if (colorIndex >= Game.Config.colors.length) {
                    colorIndex = Game.Config.colors.length - 1;
                }
                break;
        }
        return Game.Config.colors[colorIndex];
    }

};