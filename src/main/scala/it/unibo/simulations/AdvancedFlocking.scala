package it.unibo.simulations

import it.unibo.lib.movement.AlchemistMovementIncarnation._
import it.unibo.scafi.space.Point2D
import it.unibo.lib.movement._

class AdvancedFlocking extends Movement2DProgram with FlockLib with AdvancedFlock with BlockT with Movement2D {
  override def movementLogic(): AlchemistMovementIncarnation.Velocity = sense[String]("mode") match {
    case "goto" => FlockBehaviour().withGoal(Point2D(200.0, 100.0), 0.1).run()
    case "wind" => mux(T(100) != 0) {
      FlockBehaviour().withWind((1.0, 1.0)).run()
    } {
      antiflock()
    }
    case "your" => FlockBehaviour().addBehaviour(anticlockwiseRotation((0.0, 0.0))).run()
  }
}
