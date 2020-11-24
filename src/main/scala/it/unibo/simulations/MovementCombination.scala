package it.unibo.simulations


import it.unibo.lib.movement.AlchemistMovementIncarnation._
import it.unibo.lib.movement._

class MovementCombination extends Movement2DProgram with FlockLib with Movement2D {

  override def movementLogic(): Velocity = withSeparation(goToPoint((100.0, 200.0)))(30.0)
}
