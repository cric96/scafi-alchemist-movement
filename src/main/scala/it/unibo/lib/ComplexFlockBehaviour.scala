package it.unibo.lib

import it.unibo.alchemist.model.scafi.ScafiIncarnationForAlchemist.P
import it.unibo.scafi.space.Point2D

trait ComplexFlockBehaviour extends FlockLib {
  self : FlockLib.Dependencies =>
  implicit class RichFlock(flock : FlockBehaviour) {
    def addBehaviour(v : => Velocity) = flock.copy(otherVelocityEvaluation = (() => v) :: flock.otherVelocityEvaluation )
    def withWind(v : Velocity) : FlockBehaviour = flock.addBehaviour(v.normalized)
    def withGoal(p : P, importance : Double = 1.0) : FlockBehaviour = flock.addBehaviour { (p - currentPosition()).normalized * importance }
  }
}
