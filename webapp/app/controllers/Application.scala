package controllers

import core.de.htwg.todrothferiehm.util.GameUtil
import core.de.htwg.todrothferiehm.view.gui.Gui
import core.de.htwg.todrothferiehm.view.tui.Tui
import play.api.Play.current
import play.api.libs.json.JsValue
import play.api.mvc._

object Application extends Controller {

  val controllers = GameUtil.createControllers()

  new Thread(new Runnable {
    override def run() = {
      new Tui(controllers).start()
    }
  }).start()

  new Thread(new Runnable {
    override def run() = {
      new Gui(controllers).main(Array())
    }
  }).start()

  def index = Action {
    Ok(views.html.index())
  }

  def socket() = WebSocket.acceptWithActor[String, JsValue] { request => out =>
    WebSocketActor.props(controllers, out)
  }

}