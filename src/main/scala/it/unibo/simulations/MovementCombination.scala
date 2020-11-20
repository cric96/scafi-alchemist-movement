package it.unibo.simulations

import it.unibo.alchemist.model.scafi.ScafiIncarnationForAlchemist.ScafiAlchemistSupport
import it.unibo.lib.{AlchemistEuclideanSupport, FlockLib, MovementAggregateProgram}
import it.unibo.alchemist.model.scafi.ScafiIncarnationForAlchemist._
import it.unibo.lib._

class MovementCombination extends MovementAggregateProgram with StandardSensors with ScafiAlchemistSupport with AlchemistEuclideanSupport
    with FlockLib with Basic2DMovementBehaviour with BlockT {

  override def movementBody(): Velocity = withSeparation(goToPoint((100.0, 200.0)))(30.0)
}
