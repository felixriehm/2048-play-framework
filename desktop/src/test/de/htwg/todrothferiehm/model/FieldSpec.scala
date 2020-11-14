package de.htwg.todrothferiehm.model

import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class FieldSpec extends Specification {

  "A new 2x2 field filled with a 0-Tiles" should {
    var set = Set[Tile]()
    for (col <- 0 until 2; row <- 0 until 2) {
      set = set + new Tile(row, col)
    }
    val field = new Field(set)

    "have an empty tile at (0, 0)" in {
      field.get(0, 0).isEmpty
    }

    "have the size of 2" in {
      field.size must be_==(2)
    }

    "have a 2-tile at (0, 0) after setting it" in {
      val newField = field.setTile(new Tile(0, 0, 2))
      newField.get(0, 0).number must be_==(2)
    }

    "have 4 2-tiles after setting them" in {
      val newField = field.setTiles {
        var set = Set[Tile]()
        for (col <- 0 until 2; row <- 0 until 2) {
          set = set + new Tile(row, col, 2)
        }
        set
      }
      newField.get(0, 0).number must be_==(2)
      newField.get(0, 1).number must be_==(2)
      newField.get(1, 0).number must be_==(2)
      newField.get(1, 1).number must be_==(2)
    }

    "have 4 free tiles" in {
      field.freeTiles.size must be_==(4)
    }

    "be rotated once after rotating" in {
      val beforeRotation = field.setTile(new Tile(0, 0, 2))
      beforeRotation.rotation must be_==(0)
      beforeRotation.get(0, 0).number must be_==(2)
      beforeRotation.get(0, 1).number must be_==(0)
      beforeRotation.get(1, 0).number must be_==(0)
      beforeRotation.get(1, 1).number must be_==(0)

      val afterRotation = beforeRotation.rotate
      afterRotation.rotation must be_==(1)
      afterRotation.get(0, 0).number must be_==(0)
      afterRotation.get(1, 0).number must be_==(2)
      afterRotation.get(0, 1).number must be_==(0)
      afterRotation.get(1, 1).number must be_==(0)
    }

    "be able to rotate coords" in {
      field.rotateCoordsOnce(0, 0) must be_==((1, 0))
      field.rotateCoordsOnce(0, 1) must be_==((0, 0))
      field.rotateCoordsOnce(1, 0) must be_==((1, 1))
      field.rotateCoordsOnce(1, 1) must be_==((0, 1))
    }

    "have a string representation" in {
      field.toString must be_==("+---+---+\n| 0 | 0 |\n+---+---+\n| 0 | 0 |\n+---+---+\n")
    }

  }

}
