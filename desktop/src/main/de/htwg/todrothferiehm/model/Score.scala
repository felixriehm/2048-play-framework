package de.htwg.todrothferiehm.model

class Score(val score: Int = 0) {

  def +(n: Int) = new Score(score + n)

  override def toString = score.toString
}
