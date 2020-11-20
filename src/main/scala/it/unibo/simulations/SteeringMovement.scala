package it.unibo.simulations

import it.unibo.alchemist.model.scafi.ScafiIncarnationForAlchemist.ScafiAlchemistSupport
import it.unibo.lib.{AlchemistEuclideanSupport, Basic2DMovementBehaviour, MovementAggregateProgram, Steering}
import it.unibo.alchemist.model.scafi.ScafiIncarnationForAlchemist._
import it.unibo.scafi.space.Point2D

class SteeringMovement extends MovementAggregateProgram with StandardSensors with ScafiAlchemistSupport with AlchemistEuclideanSupport
  with Steering with Basic2DMovementBehaviour  {
  override def movementBody(): Velocity = explore(Point2D(-200.0, -200.0), Point2D(200.0, 200.0), 100)
}
