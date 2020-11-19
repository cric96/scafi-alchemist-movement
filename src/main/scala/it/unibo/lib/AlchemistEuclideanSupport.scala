package it.unibo.lib
import it.unibo.alchemist.model.implementations.positions.Euclidean2DPosition
import it.unibo.alchemist.model.scafi.ScafiIncarnationForAlchemist._
import it.unibo.lib.MovementSupport.CoordinateMapping
import it.unibo.scafi.space.Point2D
trait AlchemistEuclideanSupport {
  self : MovementSupport with AggregateProgram with StandardSensors with ScafiAlchemistSupport =>
  override type SIMULATION_POSITION = Euclidean2DPosition
  override implicit val mapping: CoordinateMapping[Euclidean2DPosition, P] = new CoordinateMapping[Euclidean2DPosition, P] {
    override def toInternal(p: SIMULATION_POSITION): P = Point2D(p.getX, p.getY)

    override def toExternal(p: P): SIMULATION_POSITION = new SIMULATION_POSITION(p.x, p.y)
  }

  override def move(velocity: P): Unit = {
    node.put[Double]("dx", velocity.x)
    node.put[Double]("dy", velocity.y)
  }
}
