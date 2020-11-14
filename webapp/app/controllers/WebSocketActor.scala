package controllers

import akka.actor.{Actor, ActorRef, Props}
import core.de.htwg.todrothferiehm.model._
import core.de.htwg.todrothferiehm.util.{GameUtil, ControllerContainer}
import play.api.libs.json._

import scala.swing.Reactor
import scala.swing.event.Event

object WebSocketActor {
  def props(controllers: ControllerContainer, out: ActorRef) = Props(new WebSocketActor(controllers, out))
}

class WebSocketActor(controllers: ControllerContainer, out: ActorRef) extends Reactor with Actor {

  listenTo(controllers.turnController, controllers.scoreController)

  reactions += {
    case e: Event => out ! buildJson(e)
  }

  override def receive: Receive = {
    case input: String => input match {
      case "getField" => out ! (buildJson(FieldChangedEvent()) ++ JsObject(Seq(
        "new" -> JsBoolean(true)
      )))
      case _ => GameUtil.processInput(input, controllers)
    }
  }

  def buildJson(e: Event): JsObject = {
    val json = JsObject(Seq(
      "name" -> JsString(e.getClass.getSimpleName)
    ))

    e match {
      case e: FieldChangedEvent => json ++ JsObject(Seq(
        "field" -> JsArray(controllers.turnController.field.tiles.toSeq.map(tile =>
          JsObject(Seq(
            "row" -> JsNumber(tile.row),
            "col" -> JsNumber(tile.col),
            "number" -> JsNumber(tile.number)
          ))
        )),
        "size" -> JsNumber(controllers.turnController.field.size)
      ))


      case e: ScoresGainedEvent => json ++ JsObject(Seq(
        "score" -> JsNumber(e.score)
      ))

      case e: GameOverEvent => json

      case e: NewGameStartedEvent => json ++ JsObject(Seq(
        "size" -> JsNumber(controllers.turnController.field.size)
      ))

      case e: ScoreChangedEvent => json ++ JsObject(Seq(
        "score" -> JsNumber(e.score)
      ))

      case e: TileAddedEvent => json ++ JsObject(Seq(
        "pos" -> JsArray(Seq(JsNumber(e.pos._1), JsNumber(e.pos._2))),
        "number" -> JsNumber(e.number)
      ))

      case e: SlideEvent => json ++ JsObject(Seq(
        "start" -> JsArray(Seq(JsNumber(e.start._1), JsNumber(e.start._2))),
        "end" -> JsArray(Seq(JsNumber(e.end._1), JsNumber(e.end._2)))
      ))

      case e: MergeEvent => json ++ JsObject(Seq(
        "pos" -> JsArray(Seq(JsNumber(e.pos._1), JsNumber(e.pos._2))),
        "number" -> JsNumber(e.number)
      ))
    }
  }
}