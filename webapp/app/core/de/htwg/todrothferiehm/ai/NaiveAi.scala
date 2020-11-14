package core.de.htwg.todrothferiehm.ai

import core.de.htwg.todrothferiehm.controller.{ScoreController, TurnController}
import core.de.htwg.todrothferiehm.model.Score
import core.de.htwg.todrothferiehm.util.{GameUtil, ControllerContainer}

import scala.util.Random

object NaiveAi extends Ai{

  val rand = new Random

  case class Move(direction: String, score: Int)

  override def step(controllers: ControllerContainer): Unit = {

    def score(direction: String): Move = {
      val check = new TurnController
      check.field = controllers.turnController.field
      val scores = new ScoreController(new Score(), check)
      direction match {
        case "w" => check.up()
        case "s" => check.down()
        case "a" => check.left()
        case "d" => check.right()
        case _ =>
      }
      Move(direction, scores.score.score)
    }

    val scores = score("w") :: score("s") :: score("a") :: score("d") :: Nil
    val maxScores = scores.filter(_.score == scores.maxBy(_.score).score)
    GameUtil.processInput(maxScores(rand.nextInt(maxScores.length)).direction, controllers)
  }

}
