package it.unibo.simulations


import it.unibo.lib.movement.AlchemistMovementIncarnation._
import it.unibo.scafi.space.Point2D

class SteeringMovement extends Movement2DProgram with Movement2D with Steering {
  override def movementLogic(): Velocity = explore(Point2D(-200.0, -200.0), Point2D(200.0, 200.0), 100)
}
