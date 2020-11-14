package de.htwg.todrothferiehm.model

class Field(val tiles: Set[Tile], val rotation: Int = 0) {

  def get(row: Int, col: Int) = tiles.find(tile => tile.row == row && tile.col == col).get

  def size = Math.sqrt(tiles.size).toInt

  def setTile(newTile: Tile) = new Field(tiles.filterNot(tile => tile.row == newTile.row && tile.col == newTile.col) + newTile, rotation)

  def setTiles(newTiles: Set[Tile]) = new Field(newTiles)

  def freeTiles = tiles.filter(_.isEmpty)

  def rotate = {
    val newTiles = tiles.map(tile => {
      val rotatedCoords = rotateCoordsOnce(tile.row, tile.col)
      new Tile(rotatedCoords._1, rotatedCoords._2, tile.number)
    })
    new Field(newTiles, (rotation + 1) % 4)
  }

  def rotateCoordsOnce(row: Int, col: Int): (Int, Int) = (size - 1 - col, row)

  def unrotateCoords(row: Int, col: Int): (Int, Int) = {
    var rotatedCoords = (row, col)
    for (i <- 0 until Math.abs(rotation - 4) % 4) {
      rotatedCoords = rotateCoordsOnce(rotatedCoords._1, rotatedCoords._2)
    }
    rotatedCoords
  }

  override def toString = {
    val maxLength = tiles.map(_.number.toString.length).max
    val sb = new StringBuilder
    def separator = (("+--" + ("-" * maxLength)) * size) + "+\n"

    sb append separator
    for (col <- 0 until size) {
      for (row <- 0 until size) {
        val tile = get(row, col)
        sb append "| " + (" " * (maxLength - tile.toString.length)) + tile.toString + " "
      }
      sb append "|\n" append separator
    }

    sb.toString()
  }

}
