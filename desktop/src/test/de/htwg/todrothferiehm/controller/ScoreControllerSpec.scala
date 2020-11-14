package de.htwg.todrothferiehm.controller

import de.htwg.todrothferiehm.model.Score
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ScoreControllerSpec extends Specification{

  // Den ScoreController innerhalb des "should"-Blockes zu definieren hat irgendwie zu Fehlern gefuehrt.
  "A new score controller" should {

    "reset the score to 0" in {
      val scoreController = new ScoreController(new Score, new TurnController)
      scoreController.add(2)
      scoreController.reset()
      scoreController.score.score must be_==(0)
    }

    "add a score of 2" in {
      val scoreController = new ScoreController(new Score, new TurnController)
      scoreController.add(2)
      scoreController.score.score must be_==(2)
    }

    "output the correct String representation" in {
      val scoreController = new ScoreController(new Score, new TurnController)
      scoreController.mkString must be_==("Score: 0")
    }

  }



}
