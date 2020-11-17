package it.unibo.lib
import it.unibo.alchemist.model.implementations.positions.Euclidean2DPosition
import it.unibo.alchemist.model.interfaces.Position
import it.unibo.alchemist.model.scafi.ScafiIncarnationForAlchemist._
import it.unibo.scafi.space.{Point2D, Point3D}
trait Cartesian2DMovementSupport extends MovementSupport {
  self : AggregateProgram with ScafiAlchemistSupport =>
  override type RAW_POSITION = Position[_]
  implicit def alchemistToScafi(p : P) : Position[_] = new Euclidean2DPosition(p.x, p.y)
  implicit def scafiToAlchemist(p : Position[_]) : P = Point3D(p.getCoordinate(0), p.getCoordinate(1), 0.0)
  implicit def tupleToVelocity(p : (Double, Double)) : Velocity = new Velocity(p._1, p._2, 0)

  /**
   * move following the velocity specified.
   *
   * @param velocity delta movement of the node
   */
  override def move(velocity: P): Unit = {
    val myself = alchemistEnvironment.getNodeByID(mid())
    alchemistEnvironment.moveNode(myself, velocity)
  }

  /**
   * perform a global rotation fixed in a central point
   *
   * @param angle  the degree of rotation
   * @param center mass center
   */
  override def rotate(angle: Double, center: P): Velocity = Velocity.Zero

  /**
   * concentrate each element of the field in the position passed
   *
   * @param point desired target position
   * @return the velocity that bring node into that point
   */
  override def collapseFieldIn(point: P): Velocity = {
    val velocity : P = Point2D(point.x - position.x, point.y - position.y)
    val distance : Double = math.hypot(point.x - position.x, point.y - position.y)
    (velocity.x / distance, velocity.y / distance) match {
      case (Double.NaN, _) => Point2D(0, 0)
      case (_, Double.NaN) => Point2D(0, 0)
      case (vx, vy) => Point2D(vx, vy)
    }
  }
}
