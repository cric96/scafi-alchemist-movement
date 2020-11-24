package it.unibo.lib.movement

import it.unibo.alchemist.model.implementations.molecules.SimpleMolecule
import it.unibo.alchemist.model.implementations.positions.Euclidean2DPosition
import it.unibo.alchemist.model.interfaces.{Layer, Position}
import it.unibo.incarnation.alchemist.AlchemistIncarnationLike
import it.unibo.scafi.space.Point3D

import scala.util.{Success, Try}

object AlchemistMovementIncarnation extends AlchemistIncarnationLike with MovementLibrary {
  trait AlchemistMovementSupport[ALCHEMIST_POSITION <: Position[ALCHEMIST_POSITION]] extends MovementSupport {
    self : FieldCalculusSyntax with ScafiAlchemistSupport with StandardSensors =>
    implicit def conversion : SpaceConversion[ALCHEMIST_POSITION]

    override def currentPosition(): P = conversion.toScafi(sense[ALCHEMIST_POSITION](LSNS_POSITION))

    override def move(velocity: AlchemistMovementIncarnation.Velocity): AlchemistMovementIncarnation.Velocity = {
      node.put[Double]("dx", velocity.x)
      node.put[Double]("dy", velocity.y)
      velocity
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

  trait AlchemistMovement2DSupport extends AlchemistMovementSupport[Euclidean2DPosition] {
    self : FieldCalculusSyntax with ScafiAlchemistSupport with StandardSensors =>

    override implicit def conversion: SpaceConversion[Euclidean2DPosition] = Cartesian2DSpace
  }

  trait Movement2DProgram extends MovementProgram with ScafiAlchemistSupport with AlchemistMovement2DSupport with StandardSensors

  trait SpaceConversion[P <: Position[P]] {
    def toScafi(p : P) : Point3D
  }

  object Cartesian2DSpace extends SpaceConversion[Euclidean2DPosition] {
    override def toScafi(p : Euclidean2DPosition): Point3D = Point3D(p.getX, p.getY, 0.0)
  }
}
