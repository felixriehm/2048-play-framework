package de.htwg.todrothferiehm.ai

import de.htwg.todrothferiehm.util.ControllerContainer

trait Ai {
  def step(controllers: ControllerContainer)

}
trait AiContext {
  val ai: Ai
}
