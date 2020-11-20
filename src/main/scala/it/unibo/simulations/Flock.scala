package it.unibo.simulations

import it.unibo.alchemist.model.scafi.ScafiIncarnationForAlchemist._
import it.unibo.lib.{FlockLib, _}
class Flock extends AggregateProgram
  with StandardSensors with ScafiAlchemistSupport with BlockG with BlockC with BlockS with FieldUtils
  with Basic2DMovementBehaviour with FlockLib with AlchemistEuclideanSupport with Steering with BlockT {

  override type MainResult = Any
  override def main = {
    val flockValue = flock()
    move(flockValue)
  }

}