package it.unibo.simulations

import it.unibo.lib.movement.AlchemistMovementIncarnation._
class ConstantMovement extends Movement2DProgram  {
  override def movementLogic(): Velocity = Velocity(1, 0)
}
