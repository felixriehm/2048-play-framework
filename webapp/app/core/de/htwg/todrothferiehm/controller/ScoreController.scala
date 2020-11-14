package core.de.htwg.todrothferiehm.controller

import core.de.htwg.todrothferiehm.model.{ScoreChangedEvent, NewGameStartedEvent, ScoresGainedEvent, Score}

import scala.swing.{Publisher, Reactor}


class ScoreController(var score: Score, turnController: TurnController) extends Reactor with Publisher{
  listenTo(turnController)
  reactions += {
    case e: ScoresGainedEvent => add(e.score)
    case e: NewGameStartedEvent => reset()
  }

  def add(n: Int) = {
    score = score + n
    publish(ScoreChangedEvent(score.score))
  }

  def reset() = {
    score = new Score
    publish(ScoreChangedEvent(0))
  }

  def mkString = "Score: " + score.toString
}
