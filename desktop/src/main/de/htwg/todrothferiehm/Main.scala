package de.htwg.todrothferiehm

import de.htwg.todrothferiehm.ai.{Ai, AiContext, NaiveAi}
import de.htwg.todrothferiehm.util.GameUtil
import de.htwg.todrothferiehm.view.gui.Gui
import de.htwg.todrothferiehm.view.tui.Tui

object Main {

  object AiConfiguration extends AiContext {
    override val ai: Ai = NaiveAi
  }

  def main(args: Array[String]) = {

    val controllers = GameUtil.createControllers()
    startGui()
    startTui()

    def startGui() = {
      new Thread(new Runnable {
        override def run() = {
          new Gui(controllers).main(Array())
        }
      }).start()
    }

    def startTui() = {
      new Thread(new Runnable {
        override def run() = {
          new Tui(controllers).start()
        }
      }).start()
    }

  }
}
