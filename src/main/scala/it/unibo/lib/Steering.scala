package it.unibo.lib

import it.unibo.alchemist.model.scafi.ScafiIncarnationForAlchemist._
import it.unibo.scafi.space.Point2D
trait Steering {
  self : Basic2DMovementBehaviour with MovementSupport with AggregateProgram =>
  def explore(minCoord : P, maxCoord : P, trajectoryTime : Int, reachGoalRange : Double = 0) : Velocity = {
    require(trajectoryTime > 0)
    def randomCoord : Point2D = Point2D(
      minCoord.x + (math.random() * (maxCoord.x - minCoord.y)), //todo use correct random
      minCoord.y + (math.random() * (maxCoord.y - minCoord.y)))
    val (_, _, velocity) = rep((randomCoord, trajectoryTime, Zero)){
      case (goal, decay, v) if (decay == 0) => (randomCoord, trajectoryTime, v)
      case (goal, decay, v) if (goal.distance(currentPosition()) < reachGoalRange)=> (goal, 0, goToPoint(goal))
      case (goal, decay, v) => (goal, decay - 1, goToPoint(goal))
    }
    velocity
  }
}
