package it.unibo.simulations

import it.unibo.alchemist.model.scafi.ScafiIncarnationForAlchemist._
import it.unibo.lib.{Cartesian2DMovementSupport, FlockLib}
import it.unibo.lib._
class Flock extends AggregateProgram
  with StandardSensors with ScafiAlchemistSupport with BlockG with BlockC with BlockS with FieldUtils
  with Cartesian2DMovementSupport with FlockLib with AlchemistEuclideanSupport {

  override type MainResult = Any
  override def main = {
    val flockValue = flocking(true)
    move(flockValue)
  }

}