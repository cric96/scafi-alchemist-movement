package it.unibo.lib
import it.unibo.alchemist.model.scafi.ScafiIncarnationForAlchemist._
import it.unibo.lib.MovementSupport.CoordinateMapping
trait MovementSupport {
  self : AggregateProgram with StandardSensors =>
  type Velocity = P
  type SIMULATION_POSITION //environment should have another position type...
  implicit def mapping: CoordinateMapping[SIMULATION_POSITION, P]
  implicit def toSimulation(point : P) : SIMULATION_POSITION = mapping.toExternal(point)
  implicit def toInternal(point : SIMULATION_POSITION) : P = mapping.toInternal(point)
  object Velocity {
    val Zero = new Velocity(0,0,0)
  }

  override def currentPosition(): P = sense[SIMULATION_POSITION](LSNS_POSITION)
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

object MovementSupport {
  trait CoordinateMapping[EXTERNAL, INTERNAL] {
    def toInternal(p : EXTERNAL) : INTERNAL
    def toExternal(p : INTERNAL) : EXTERNAL
  }
}