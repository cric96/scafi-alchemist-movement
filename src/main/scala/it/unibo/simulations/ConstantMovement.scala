package it.unibo.simulations

import it.unibo.alchemist.model.scafi.ScafiIncarnationForAlchemist.{ScafiAlchemistSupport, _}
import it.unibo.lib.{AlchemistEuclideanSupport, MovementAggregateProgram}
import it.unibo.alchemist.model.scafi.ScafiIncarnationForAlchemist._
class ConstantMovement extends MovementAggregateProgram with StandardSensors with ScafiAlchemistSupport with AlchemistEuclideanSupport {
  override def movementBody(): Velocity = Velocity(1, 0)
}
