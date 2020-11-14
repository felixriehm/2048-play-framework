package core.de.htwg.todrothferiehm.controller

import core.de.htwg.todrothferiehm.model._

import scala.swing.Publisher
import scala.util.Random

class TurnController() extends Publisher {

  var field: Field = new Field(Set.empty[Tile])
  val startSize = 4
  newGame(startSize)

  def newGame(size: Int) = {
    if (size >= 2) {
      var set = Set[Tile]()
      for (col <- 0 until size; row <- 0 until size) {
        set = set + new Tile(row, col)
      }
      field = field.setTiles(set)
      startGame()
    }

    def startGame() = {
      addRandomNumber()
      addRandomNumber()
      publish(NewGameStartedEvent(size))
      publish(FieldChangedEvent())
    }
  }


  def addRandomNumber() = {
    val freeTiles = field.freeTiles
    val tile = freeTiles.toList(Random.nextInt(freeTiles.size))
    val newTile = if (Random.nextInt(10) == 0) tile.setNumber(4) else tile.setNumber(2)
    field = field.setTile(newTile)
    publish(TileAddedEvent(field.unrotateCoords(newTile.row, newTile.col), newTile.number))
  }

  def left() = {
    if (canSlide) {
      slide()
      addRandomNumber()
    }
    publish(FieldChangedEvent())
    if (gameEnded()) publish(GameOverEvent())
  }

  def right() = {
    field = field.rotate.rotate
    if (canSlide) {
      slide()
      addRandomNumber()
    }
    field = field.rotate.rotate
    publish(FieldChangedEvent())
    if (gameEnded()) publish(GameOverEvent())
  }

  def up() = {
    field = field.rotate.rotate.rotate
    if (canSlide) {
      slide()
      addRandomNumber()
    }
    field = field.rotate
    publish(FieldChangedEvent())
    if (gameEnded()) publish(GameOverEvent())
  }

  def down() = {
    field = field.rotate
    if (canSlide) {
      slide()
      addRandomNumber()
    }
    field = field.rotate.rotate.rotate
    publish(FieldChangedEvent())
    if (gameEnded()) publish(GameOverEvent())
  }

  def gameEnded(): Boolean = {
    var ended = true
    for (i <- 0 until 4) {
      if (canSlide) {
        ended = false
      }
      field = field.rotate
    }
    ended
  }


  def slide() = {
    for (col <- 0 until field.size; row <- 1 until field.size) {
      val tile = field.get(row, col)
      if (tile.isFilled) {
        slideTile(tile, field.get(row - 1, col))
      }
    }

    def slideTile(tile: Tile, checkTile: Tile): Any = {
      //letzte Reihe und leeres Feld
      if (checkTile.row == 0 && checkTile.isEmpty) {
        field = field.setTile(tile.reset())
        field = field.setTile(checkTile.setNumber(tile.number))
        publish(SlideEvent(field.unrotateCoords(tile.row, tile.col), field.unrotateCoords(checkTile.row, checkTile.col)))
        return
      }

      //besetztes Feld und Nummer verschieden
      if (checkTile.number != tile.number && checkTile.isFilled) {
        field = field.setTile(tile.reset())
        field = field.setTile(field.get(checkTile.row + 1, checkTile.col).setNumber(tile.number))
        publish(SlideEvent(field.unrotateCoords(tile.row, tile.col), field.unrotateCoords(checkTile.row + 1, checkTile.col)))
        return
      }

      //besetztes Feld und Nummer gleich
      if (checkTile.number == tile.number && checkTile.isFilled) {
        val mergedNumber = tile.number + checkTile.number
        field = field.setTile(tile.reset())
        field = field.setTile(checkTile.setNumber(mergedNumber))
        publish(ScoresGainedEvent(mergedNumber))
        publish(SlideEvent(field.unrotateCoords(tile.row, tile.col), field.unrotateCoords(checkTile.row, checkTile.col)))
        publish(MergeEvent(field.unrotateCoords(checkTile.row, checkTile.col), mergedNumber))
        if (mergedNumber == 2048) publish(WinGameEvent())
        return
      }

      //leeres Feld
      if (checkTile.isEmpty) {
        slideTile(tile, field.get(checkTile.row - 1, checkTile.col))
        return
      }

    }
    field
  }


  def canSlide: Boolean = {
    for (col <- 0 until field.size; row <- 0 until field.size - 1) {
      val upperTile = field.get(row, col)
      val lowerTile = field.get(row + 1, col)
      if (canMerge(upperTile, lowerTile) || canSlideUp(upperTile, lowerTile)) return true
    }

    def canMerge(upperTile: Tile, lowerTile: Tile) = upperTile.number == lowerTile.number && upperTile.number != 0 && lowerTile.number != 0
    def canSlideUp(upperTile: Tile, lowerTile: Tile) = upperTile.number == 0 && lowerTile.number != 0

    false
  }

  def mkString = field.toString
}
