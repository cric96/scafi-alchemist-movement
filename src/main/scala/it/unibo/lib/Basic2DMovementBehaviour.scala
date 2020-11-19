package it.unibo.lib
import it.unibo.alchemist.model.scafi.ScafiIncarnationForAlchemist._
import it.unibo.scafi.space.Point2D
trait Basic2DMovementBehaviour {
  self : MovementSupport =>

  def clockwiseRotation(center : P) : Velocity = {
    val centerVector = currentPosition() - center
    Point2D(centerVector.y, - centerVector.x).normalized
  }

  def anticlockwiseRotation(center : P) : Velocity = -clockwiseRotation(center)

  def goToPoint(point : P) : Velocity = point - currentPosition()
}
