package de.htwg.todrothferiehm.model

import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ScoreSpec extends Specification {

  "A new empty score" should {

    val score = new Score()

    "have the value 0" in {
      score.score must be_==(0)
    }

    "be equal to a score created with 0" in {
      score.score must be_==(new Score(0).score)
    }

    "change its score when something is added" in {
      (score + 2).score must be_==(2)
    }

    "output 0 in its String representation" in {
      score.toString must be_==("0")
    }
    
  }

}
