package it.unibo.simulations

import it.unibo.alchemist.model.scafi.ScafiIncarnationForAlchemist._
import it.unibo.lib._
class Flock extends MovementAggregateProgram
  with StandardSensors with ScafiAlchemistSupport with FlockLib with AlchemistEuclideanSupport {

  override def movementBody(): Velocity = flock()
}