package it.unibo.simulations

import it.unibo.alchemist.model.scafi.ScafiIncarnationForAlchemist._
import it.unibo.alchemist.model.scafi.ScafiIncarnationForAlchemist.ScafiAlchemistSupport
import it.unibo.lib.{AlchemistEuclideanSupport, Basic2DMovementBehaviour, MovementAggregateProgram}
import it.unibo.lib._ //help conversion
class BasicMovement extends MovementAggregateProgram with StandardSensors with ScafiAlchemistSupport with Basic2DMovementBehaviour with AlchemistEuclideanSupport {
  override def movementBody(): Velocity = sense[String]("mode") match {
    case "goto" => goToPoint((50.0, 50.0))
    case "rotation_clock_wise" => clockwiseRotation((-10.0, 10.0))
    case "rotation_anticlock_wise" => anticlockwiseRotation((-10.0, 10.0))
    case "stand" => standStill
  }
}
