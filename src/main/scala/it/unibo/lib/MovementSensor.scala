package it.unibo.lib

trait MovementSensor {
  self : MovementSupport =>
  //TODO think if this could be used for nice examples
  def obstacle(range : Double) : Boolean
}
