package it.unibo.lib
import it.unibo.alchemist.model.scafi.ScafiIncarnationForAlchemist._
import it.unibo.scafi.space.Point2D

trait Cartesian2DMovementSupport extends MovementSupport {
  self : AggregateProgram with StandardSensors =>
  override def distanceBetween(referencePoint: Velocity, targetPoint: Velocity): Double = math.hypot(referencePoint.x - targetPoint.x, referencePoint.y - targetPoint.y)
  /**
   * perform a global rotation fixed in a central point
   *
   * @param angle  the degree of rotation
   * @param center mass center
   */
  override def rotate(angle: Double, center: P): Velocity = {
    val distance = currentPosition().distance(center)
    val computed = Point2D(
      center.x + distance * math.cos(angle),
      center.y + distance * math.sin(angle)
    )
    computed - currentPosition
  }
  /**
   * concentrate each element of the field in the position passed
   *
   * @param point desired target position
   * @return the velocity that bring node into that point
   */
  override def collapseFieldIn(point: P): Velocity = {
    val velocity : P = point - currentPosition
    val distance : Double = math.floor(distanceBetween(point, currentPosition()))
    (distance) match {
      case distance if distance == 0.0 => Point2D(0, 0)
      case d => velocity / d
    }
  }
}
