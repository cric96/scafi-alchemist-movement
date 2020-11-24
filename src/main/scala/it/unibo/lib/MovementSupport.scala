package it.unibo.lib

import it.unibo.alchemist.model.scafi.ScafiIncarnationForAlchemist._
import it.unibo.lib.MovementSupport.CoordinateMapping
trait MovementSupport {
  self : AggregateProgram with StandardSensors =>
  type Velocity = P
  object Velocity {
    val Zero = new Velocity(0,0,0)
    def apply(x : Double, y : Double) : Velocity = new Velocity(x, y, 0)
  }
  type SIMULATION_POSITION //environment should have another position type...
  implicit def mapping: CoordinateMapping[SIMULATION_POSITION, P]
  implicit def toSimulation(point : P) : SIMULATION_POSITION = mapping.toExternal(point)
  implicit def toInternal(point : SIMULATION_POSITION) : P = mapping.toInternal(point)

  override def currentPosition(): P = sense[SIMULATION_POSITION](LSNS_POSITION)
  /**
   * move following the velocity specified. It works like a side effect into the field
   * @param velocity delta movement of the node
   */
  def move(velocity : Velocity) : Unit
}

object MovementSupport {
  trait CoordinateMapping[EXTERNAL, INTERNAL] {
    def toInternal(p : EXTERNAL) : INTERNAL
    def toExternal(p : INTERNAL) : EXTERNAL
  }
}