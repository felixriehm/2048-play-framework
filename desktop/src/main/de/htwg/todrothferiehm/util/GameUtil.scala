package de.htwg.todrothferiehm.util

import de.htwg.todrothferiehm.Main.AiConfiguration
import de.htwg.todrothferiehm.controller.{ScoreController, TurnController}
import de.htwg.todrothferiehm.model.Score

import scala.util.{Success, Try}

case class ControllerContainer(turnController: TurnController, scoreController: ScoreController)

object GameUtil {

  def createControllers() = {
    val turnController = new TurnController()
    val scoreController = new ScoreController(new Score(), turnController)
    ControllerContainer(turnController, scoreController)
  }

  def processInput(input: String, controllers: ControllerContainer): Boolean = {
    var continue = true
    input match {
      case "w" => controllers.turnController.up()
      case "a" => controllers.turnController.left()
      case "s" => controllers.turnController.down()
      case "d" => controllers.turnController.right()
      case "step" => AiConfiguration.ai.step(controllers)
      case n if n != null && n.startsWith("n") => Try(n.stripPrefix("n").toInt) match {
        case Success(size) => controllers.turnController.newGame(size)
        case _ => ;
      }
      case "q" =>
        continue = false
      case _ => ;
    }
    continue
  }

}

