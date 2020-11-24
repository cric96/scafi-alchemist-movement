package it.unibo.simulations

import it.unibo.lib.movement.AlchemistMovementIncarnation._
import it.unibo.lib.movement._
import it.unibo.scafi.space.Point2D
class FindArea extends Movement2DProgram with FlockLib with Movement2D with BuildingBlocks with Steering with AdvancedFlock {

  def localFoundZone : Boolean = senseEnv[Double]("target") > 2.0
  def someoneFoundZone : Boolean = rep(false)(init => foldhood(init)(_ || _)(nbr(localFoundZone || init)))

  val separationDistance = 15
  val maxBound = Point2D(200.0, 200.0)
  val trajectoryTime = 300
  val reachingGoalRange = 30

  override def movementLogic(): Velocity = {
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
