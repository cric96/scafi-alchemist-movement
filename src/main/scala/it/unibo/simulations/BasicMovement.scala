package it.unibo.simulations

import it.unibo.lib.movement.AlchemistMovementIncarnation._
import it.unibo.lib.movement._
class BasicMovement extends Movement2DProgram with Movement2D {

  override def movementLogic(): Velocity = sense[String]("mode") match {
    case "goto" => goToPoint((50.0, 50.0))
    case "rotation_clock_wise" => clockwiseRotation((-10.0, 10.0))
    case "rotation_anticlock_wise" => anticlockwiseRotation((-10.0, 10.0))
    case "stand" => standStill
  }
}
