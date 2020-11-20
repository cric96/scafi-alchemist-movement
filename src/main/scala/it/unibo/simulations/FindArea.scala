package it.unibo.simulations

import it.unibo.alchemist.model.scafi.ScafiIncarnationForAlchemist.{ScafiAlchemistSupport, _}
import it.unibo.lib.{AlchemistEuclideanSupport, Basic2DMovementBehaviour, ComplexFlockBehaviour, FlockLib, MovementAggregateProgram, RichPoint3D, Steering, Zero}
import it.unibo.scafi.space.Point2D
class FindArea extends MovementAggregateProgram with StandardSensors with ScafiAlchemistSupport with AlchemistEuclideanSupport
  with FlockLib with Basic2DMovementBehaviour with BuildingBlocks with Steering with ComplexFlockBehaviour {

  def localFoundZone : Boolean = senseEnv[Double]("target") > 2.0
  def someoneFoundZone : Boolean = rep(false)(init => foldhood(init)(_ || _)(nbr(localFoundZone || init)))

  val separationDistance = 15
  val maxBound = Point2D(200.0, 200.0)
  val trajectoryTime = 300
  val reachingGoalRange = 30

  override def movementBody(): Velocity = {
    val (vel, _) = rep[(Velocity, Option[P])]((Zero, None)){
      case (v, oldGoal) => {
        val localFound = mux[Option[P]](localFoundZone) {
          Some(currentPosition())
        } {
          Option.empty[P]
        }
        val otherFound = mux(someoneFoundZone && ! localFoundZone) {
          foldhoodPlus(Option.empty[P])((acc, p) => acc.orElse(p))(nbr(localFound))
        } {
          None
        }

        val sensedGoal = localFound.orElse(otherFound)
        val toGoal = sensedGoal.map(point => withSeparation(goToPoint(point))(separationDistance))
        val exploration = FlockBehaviour().addBehaviour(explore(-maxBound, maxBound, trajectoryTime, reachingGoalRange)).run()
        (toGoal.getOrElse(exploration), sensedGoal)
      }
    }
    vel
  }
}
