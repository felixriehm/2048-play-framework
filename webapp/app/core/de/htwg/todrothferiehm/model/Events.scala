package core.de.htwg.todrothferiehm.model

import scala.swing.event.Event

case class FieldChangedEvent() extends Event

case class ScoresGainedEvent(score: Int) extends Event

case class GameOverEvent() extends Event

case class NewGameStartedEvent(size: Int) extends Event

case class ScoreChangedEvent(score: Int) extends Event

case class TileAddedEvent(pos: (Int, Int), number: Int) extends Event

case class SlideEvent(start: (Int, Int), end: (Int, Int)) extends Event

case class MergeEvent(pos: (Int, Int), number: Int) extends Event

case class WinGameEvent() extends Event