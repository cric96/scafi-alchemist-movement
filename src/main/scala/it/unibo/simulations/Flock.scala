package it.unibo.simulations

import it.unibo.lib.movement.AlchemistMovementIncarnation._
class Flock extends Movement2DProgram with FlockLib {
  override def movementLogic(): Velocity = flock()
}