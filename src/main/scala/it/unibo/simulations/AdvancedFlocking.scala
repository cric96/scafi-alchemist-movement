package it.unibo.simulations

import it.unibo.alchemist.model.scafi.ScafiIncarnationForAlchemist.ScafiAlchemistSupport
import it.unibo.lib.{AlchemistEuclideanSupport, ComplexFlockBehaviour, FlockLib, MovementAggregateProgram}
import it.unibo.alchemist.model.scafi.ScafiIncarnationForAlchemist._
import it.unibo.lib._
import it.unibo.scafi.space.Point2D

class AdvancedFlocking extends MovementAggregateProgram
  with StandardSensors with ScafiAlchemistSupport with FlockLib with AlchemistEuclideanSupport with ComplexFlockBehaviour with BlockT
  with Basic2DMovementBehaviour {
  override def movementBody(): Velocity = sense[String]("mode") match {
    case "goto" => FlockBehaviour().withGoal(Point2D(200.0, 100.0), 0.1).run()
    case "wind" => mux(T(100) != 0) {
      FlockBehaviour().withWind((1.0, 1.0)).run()
    } {
      antiflock()
    }
    case "your" => FlockBehaviour().addBehaviour(anticlockwiseRotation((0.0, 0.0))).run()
  }
}
