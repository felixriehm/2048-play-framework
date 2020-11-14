package de.htwg.todrothferiehm.view.tui

import de.htwg.todrothferiehm.model.{FieldChangedEvent, GameOverEvent, ScoreChangedEvent}
import de.htwg.todrothferiehm.util.{ControllerContainer, GameUtil}

import scala.io.StdIn
import scala.swing.Reactor

class Tui(val controllers: ControllerContainer) extends Reactor {

  listenTo(controllers.turnController, controllers.scoreController)

  reactions += {
    case e: FieldChangedEvent => printField()
    case e: Any => println(e.toString)
  }

  println("Use wasd to move, nX for new game with size X, q to quit.")
  printField()

  def printField() = {
    println(controllers.turnController.mkString)
  }

  def printScore(score: Int) = {
    println("Score: " + score)
  }

  def printGameOver() = println("Game Over!")

  def start() = {
    while (GameUtil.processInput(StdIn.readLine(), controllers)) {}
  }
}
