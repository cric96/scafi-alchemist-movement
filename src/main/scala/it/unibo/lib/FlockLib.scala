package it.unibo.lib
import it.unibo.alchemist.model.scafi.ScafiIncarnationForAlchemist._
import it.unibo.scafi.space.{Point2D, Point3D}
trait FlockLib extends FieldUtils {
  self:  AggregateProgram with StandardSensors with Cartesian2DMovementSupport =>

  def flocking(flockingField: Boolean,
               attractionForce: Double = 1.0, alignmentForce: Double = 1.0, repulsionForce: Double = 1.0, separationDistance: Double = Double.PositiveInfinity): Velocity = {

    rep[Velocity](Zero){
      v => {
        val activeNodes = foldhood[Seq[Point3D]](Seq.empty[Point3D])(_ ++ _){
          mux(flockingField)(Seq[P](nbr(position)))(Seq.empty[Point3D])
        }
        val vectorRepulsion: Velocity = this.separation(separationDistance, v)
        val vectorAlignment: Velocity = this.alignment(flockingField, v, activeNodes.size)
        val vectorAttraction: Velocity = this.cohesion(activeNodes)
        val vectorX: Double = (vectorAttraction.x * attractionForce
          + vectorRepulsion.x * repulsionForce
          + vectorAlignment.x * alignmentForce)
        val vectorY: Double = (vectorAttraction.y * attractionForce
          + vectorRepulsion.y * repulsionForce
          + vectorAlignment.y * alignmentForce)

        normalize(v + (vectorX, vectorY))
      }
    }
  }

  def flockingAvoidObstacle(flockingField: Boolean, obstacleField: Boolean, separationDistance: Double,
                            attractionForce: Double, alignmentForce: Double, repulsionForce: Double, obstacleForce: Double) : Velocity = {
    rep[Velocity](Zero){
      v => {
        val activeNodes = foldhood[Seq[Point3D]](Seq.empty[Point3D])(_ ++ _){
          mux(flockingField)(Seq(nbrVector()))(Seq.empty[Point3D])
        }
        val vectorRepulsion: Velocity = this.separation(separationDistance, v)
        val vectorAlignment: Velocity = this.alignment(flockingField, v, activeNodes.size)
        val vectorAttraction: Velocity = this.cohesion(activeNodes)
        val vectorObstacle: Velocity = this.obstacle(obstacleField)
        val vectorX: Double = (vectorAttraction.x * attractionForce
          + vectorRepulsion.x * repulsionForce
          + vectorAlignment.x * alignmentForce
          + vectorObstacle.x * obstacleForce)
        val vectorY: Double = (vectorAttraction.y * attractionForce
          + vectorRepulsion.y * repulsionForce
          + vectorAlignment.y * alignmentForce
          + vectorObstacle.y * obstacleForce)

        normalize(v + (vectorX, vectorY))
      }
    }
  }

  private def obstacle(obstacleField: Boolean): P = {
    val obstaclesVector = foldhood[Seq[Point3D]](Seq.empty[P])(_ ++ _){
      mux(obstacleField)(Seq(nbrVector()))(Seq())
    }.fold(Zero)(_ + _)
    - normalize(obstaclesVector)
  }

  private def alignment(flockingSensor: Boolean, velocity: Velocity, neighbourCount : Int): P = {
    val alignmentVector: P =
      foldhood(Zero)(_ + _){
        mux(flockingSensor) {
          nbr(velocity)
        } {
          Zero
        }
      }

    normalize(alignmentVector / neighbourCount)
  }

  private def cohesion(neighbors: Seq[Point3D]): P = {
    val cohesionVector = neighbors.fold(Zero)((a,b) => a + b)
    normalize((cohesionVector / neighbors.size) - position)
  }

  private def separation(separationDistance: Double, velocity: Velocity): P = {
    val closestNeighbours = foldhood(Seq.empty[P])(_ ++ _)(Seq(nbr(position)))
      .filter(point => point.module < separationDistance)

    val separationVector = closestNeighbours.fold[P](Zero)((acc, b) => acc + (b - position))
    normalize(-(separationVector / closestNeighbours.size))
  }

  def normalize(point : P): P = {
    //todo fix it
    mux(point.module == 0.0 || point.module.isNaN) { Zero } { point.unary }
  }

  def goToPointWithSeparation(point: P, separationDistance: Double): P = {
    val currentPos = position
    val pointVect =  point - currentPos
    val separationVector: P = this.separation(separationDistance, Zero)

    normalize(pointVect + separationVector)
  }
}
