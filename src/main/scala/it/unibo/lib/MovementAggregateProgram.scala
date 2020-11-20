package it.unibo.lib
import it.unibo.alchemist.model.scafi.ScafiIncarnationForAlchemist._
trait MovementAggregateProgram extends AggregateProgram {
  self : MovementSupport  =>
  override type MainResult = Any

  def movementBody() : Velocity

  override final def main(): MainResult = move(movementBody())
}
