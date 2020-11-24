package it.unibo.lib

import java.util.OptionalInt

import it.unibo.alchemist.model.implementations.molecules.SimpleMolecule
import it.unibo.alchemist.model.implementations.positions.Euclidean2DPosition
import it.unibo.alchemist.model.interfaces.{Layer, Position}
import it.unibo.alchemist.model.scafi.ScafiIncarnationForAlchemist._
import MovementSupport.CoordinateMapping
import it.unibo.scafi.space.Point2D

import scala.util.{Success, Try}
trait AlchemistEuclideanSupport extends MovementSupport {
  self : AggregateProgram with StandardSensors with ScafiAlchemistSupport =>
  override type SIMULATION_POSITION = Euclidean2DPosition
  override implicit val mapping: CoordinateMapping[Euclidean2DPosition, P] = new CoordinateMapping[Euclidean2DPosition, P] {
    override def toInternal(p: SIMULATION_POSITION): P = Point2D(p.getX, p.getY)

    override def toExternal(p: P): SIMULATION_POSITION = new SIMULATION_POSITION(p.x, p.y)
  }

  override def move(velocity: P): Unit = {
    node.put[Double]("dx", velocity.x)
    node.put[Double]("dy", velocity.y)
  }

  private def findInLayers[A](name : String) : Option[A] = {
    val layer : Option[Layer[Any, Position[_]]] = alchemistEnvironment.getLayer(new SimpleMolecule(name))
    val node = alchemistEnvironment.getNodeByID(mid())
    layer.map(l => l.getValue(alchemistEnvironment.getPosition(node)))
      .map(value => Try(value.asInstanceOf[A]))
      .collect { case Success(value) => value }
  }

  //TODO fix in the alchemist port
  def senseEnv[A](name: String): A = {
    findInLayers[A](name).get
  }
}
