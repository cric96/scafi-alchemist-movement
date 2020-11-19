package it.unibo.lib
import it.unibo.alchemist.model.scafi.ScafiIncarnationForAlchemist._ //TODO: fix, avoid to import entire cake, create a lib that might be extended by other platform
import it.unibo.scafi.space.Point3D
/**
 *
   * implementation taken by https://gamedevelopment.tutsplus.com/tutorials/3-simple-rules-of-flocking-behaviors-alignment-cohesion-and-separation--gamedev-3444
 */
trait FlockLib extends FieldUtils {
  self: AggregateProgram with StandardSensors with MovementSupport =>

  private def concatenateVectors(vectors : (Velocity)*) : Velocity = {
    vectors.fold(Zero)(_ + _)
  }

  private def getValuesFromActiveNode[E](flockingField : Boolean)(value : => E) : Seq[E] = {
    foldhoodPlus[Seq[E]](Seq.empty[E])(_ ++ _){
      mux(flockingField){
        Seq[E](nbr(value))
      } /* else */ {
        Seq.empty[E]
      }
    }
  }

  def flocking(flockingField: Boolean,
               attractionForce: Double = 1.0, alignmentForce: Double = 1.0, repulsionForce: Double = 1.0, separationDistance: Double = Double.PositiveInfinity): Velocity = {

    rep[Velocity](Zero){
      v => {
        val activeNodes = getValuesFromActiveNode(flockingField)(currentPosition())
        val resultingVector = concatenateVectors(
          this.separation(activeNodes, separationDistance) * repulsionForce,
          this.alignment(flockingField, v, activeNodes.size) * alignmentForce,
          this.cohesion(activeNodes) * attractionForce,
        )
        normalize(v + resultingVector)
      }
    }
  }

  def antiflocking(flockingField: Boolean,
                   attractionForce: Double = 1.0, alignmentForce: Double = 1.0, repulsionForce: Double = 1.0, separationDistance: Double = Double.PositiveInfinity) : Velocity = {
    flocking(flockingField, -attractionForce, -alignmentForce, -repulsionForce, separationDistance)
  }
  def alignment(flockingSensor: Boolean, velocity: Velocity, neighbourCount : Int): Velocity = {
    val alignmentVector: P = getValuesFromActiveNode(flockingSensor)(velocity).fold(Zero)(_ + _)
    normalize(alignmentVector / neighbourCount)
  }

  def cohesion(neighbors: Seq[Point3D]): Velocity = if(neighbors.isEmpty) {
      Zero
    } else {
      val cohesionVector = neighbors.fold(Zero)((a,b) => a + b)
      normalize((cohesionVector / neighbors.size) - currentPosition())
    }

  def separation(activeNode : Seq[Point3D], separationDistance: Double): Velocity = {
    val closestNeighbours = inRange(activeNode, separationDistance).map(_ - currentPosition())
    val separationVector = closestNeighbours.fold[P](Zero)((acc, b) => acc + b)
    normalize(-separationVector) //todo eval if it change with neighbour division
  }

  def normalize(point : P): P = point.normalized

  def withSeparation(selector : Boolean)(velocity: Velocity)(separationDistance: Double) : P = {
    val activeNodeInRange = inRange(getValuesFromActiveNode(selector)(currentPosition()), separationDistance)
    ((velocity / (activeNodeInRange.size + 1)) + separation(activeNodeInRange, separationDistance)).normalized
  }

  def withSeparation(velocity: Velocity)(separationDistance: Double) : P = withSeparation(true)(velocity)(separationDistance)

  private def inRange(neighbours : Seq[Point3D], range : Double) : Seq[Point3D] = neighbours
    .filter(vector => currentPosition().distance(vector) < range)
}
