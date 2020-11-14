package de.htwg.todrothferiehm.util

import de.htwg.todrothferiehm.controller.{TurnController, ScoreController}
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class GameUtilSpec extends Specification {

  "GameUtil-Object" should {
    val controllers = GameUtil.createControllers()

    "create Controllers" in {
      controllers.scoreController should beAnInstanceOf[ScoreController]
      controllers.turnController should beAnInstanceOf[TurnController]
    }

    "return correct continuation boolean" in {
      GameUtil.processInput("w", controllers) should beTrue
      GameUtil.processInput("a", controllers) should beTrue
      GameUtil.processInput("s", controllers) should beTrue
      GameUtil.processInput("d", controllers) should beTrue
      GameUtil.processInput("n2", controllers) should beTrue
      GameUtil.processInput("XXX", controllers) should beTrue
      GameUtil.processInput("q", controllers) should beFalse
    }
  }

}
