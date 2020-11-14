package core.de.htwg.todrothferiehm.ai

import core.de.htwg.todrothferiehm.util.ControllerContainer

trait Ai {

  def step(controllers: ControllerContainer): Unit

}
