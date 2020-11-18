package it.unibo.lib
import it.unibo.alchemist.model.scafi.ScafiIncarnationForAlchemist._
trait MovementSupport {
  self : AggregateProgram =>
  type Velocity = P
  type RAW_POSITION

  object Velocity {
    val Zero = new Velocity(0,0,0)
  }
  /**
   * a shorthand to calculate the position field
   * @return the node position
   */
  def position : RAW_POSITION = sense[RAW_POSITION](LSNS_POSITION)

  /**
   * return a scalar distance between two position
   * @param referencePoint the reference position
   * @param targetPoint the target position
   * @return a scalar that measure the distance.
   */
  def distanceBetween(referencePoint : P, targetPoint : P) : Double
  /**
   * move following the velocity specified.
   * @param velocity delta movement of the node
   */
  def move(velocity : Velocity) : Unit
  /**
   * perform a global rotation fixed in a central point
   * @param angle the degree of rotation
   * @param center mass center
   */
  def rotate(angle : Double, center : P) : Velocity
  /**
   * concentrate each element of the field in the position passed
   * @param point desired target position
   * @return the velocity that bring node into that point
   */
  def collapseFieldIn(point : P) : Velocity
}
