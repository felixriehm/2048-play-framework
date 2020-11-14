window.Game = window.Game || {};

Game.Field = {

    size: 0,

    setField: function (field, size) {
        if (Game.Field.size !== size) {
            Game.Field.size = size;
            Game.Field.create(size);
        }

        field.forEach(function (newTile) {
            Game.Field.setTile(newTile.row, newTile.col, newTile.number);
        });
    },

    getTileEl: function (row, col) {
        return $($(".tile[data-row='" + row + "'][data-col='" + col + "']")[0]);
    },

    setTile: function (row, col, number) {
        var tileEl = Game.Field.getTileEl(row, col);
        switch (number) {
            case 0: tileEl.text("");
                break;
            default: tileEl.text(number);
        }
        Game.Field.setColor(tileEl, number);
    },

    setColor: function (tileEl, number) {
        tileEl.css('background-color', Game.Config.getColorFor(number));
    },

    create: function (size) {
        Game.Config.fieldElement.empty();
        Game.Config.fieldElement.css({
            width: Game.Config.tileSize * size,
            height: Game.Config.tileSize * size
        });
        for (var col = 0; col < size; ++col) {
            for (var row = 0; row < size; ++row) {
                var div = "<div class='tile text-middle' data-row='" + row + "' data-col='" + col + "'></div>";
                Game.Config.fieldElement.append(div);
            }
        }
    }

};