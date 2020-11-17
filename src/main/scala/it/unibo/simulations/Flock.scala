package it.unibo.simulations

import it.unibo.alchemist.model.scafi.ScafiIncarnationForAlchemist._
import it.unibo.lib.Cartesian2DMovementSupport

class Flock extends AggregateProgram
  with StandardSensors with ScafiAlchemistSupport with BlockG with BlockC with BlockS with FieldUtils
  with Cartesian2DMovementSupport{

  override type MainResult = Any

  override def main = {
    move(collapseFieldIn(10.0, 0.0))
  }

}