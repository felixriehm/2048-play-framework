package de.htwg.todrothferiehm.model

import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TileSpec extends Specification {
  "A new empty tile " should {

    val tile = new Tile(0, 0)

    "be empty" in {
      tile.isEmpty must beTrue
    }

    "not be filled" in {
      tile.isFilled must beFalse
    }

    "have a string representation" in {
      tile.toString must be_==("0")
    }

    "have a new number and same coords when changed" in {
      val newTile = tile.setNumber(2)
      newTile.number must be_==(2)
      newTile.row must be_==(tile.row)
      newTile.col must be_==(tile.col)
    }

  }
}