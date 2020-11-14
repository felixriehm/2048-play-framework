package de.htwg.todrothferiehm.model

case class Tile(row: Int, col: Int, number: Int = 0) {

  def setNumber(number: Int) = new Tile(row, col, number)

  def reset() = setNumber(0)

  def isEmpty = number == 0

  def isFilled = !isEmpty

  override def toString = number.toString

}
