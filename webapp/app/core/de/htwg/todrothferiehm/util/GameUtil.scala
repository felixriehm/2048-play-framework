package core.de.htwg.todrothferiehm.util

import core.de.htwg.todrothferiehm.ai.NaiveAi
import core.de.htwg.todrothferiehm.controller.{ScoreController, TurnController}
import core.de.htwg.todrothferiehm.model.Score

import scala.util.Try

case class ControllerContainer(turnController: TurnController, scoreController: ScoreController)

object GameUtil {

  def createControllers() = {
    val turnController = new TurnController()
    val scoreController = new ScoreController(new Score(), turnController)
    ControllerContainer(turnController, scoreController)
  }

  def processInput(input: String, controllers: ControllerContainer) = {
    var continue = true
    input match {
      case "w" => controllers.turnController.up()
      case "a" => controllers.turnController.left()
      case "s" => controllers.turnController.down()
      case "d" => controllers.turnController.right()
      case "step" => NaiveAi.step(controllers) // Hardcoded AI
      case n if n != null && n.startsWith("n") => Try(n.stripPrefix("n").toInt).toOption match {
        case Some(size) => controllers.turnController.newGame(size)
        case None => ;
      }
      case "q" =>
        continue = false
      case _ =>
    }
    continue
  }
}
