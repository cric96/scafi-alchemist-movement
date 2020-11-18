package it.unibo.alchemist

import it.unibo.alchemist.model.implementations.actions.AbstractMoveNode
import it.unibo.alchemist.model.implementations.nodes.SimpleNodeManager
import it.unibo.alchemist.model.implementations.positions.Euclidean2DPosition
import it.unibo.alchemist.model.interfaces.{Action, Environment, Node, Position, Reaction}

class DeltaMovement[T](env : Environment[T, Euclidean2DPosition], node : Node[T]) extends AbstractMoveNode[T, Euclidean2DPosition](env, node) {
  val manager = new SimpleNodeManager[T](node)

  def getDeltaVector : (Double, Double) = (manager.getOrElse("dx", 0.0), manager.getOrElse("dy", 0.0))
  override def getNextPosition: Euclidean2DPosition = {
    new Euclidean2DPosition(getDeltaVector._1,getDeltaVector._2)
  }

  override def cloneAction(n: Node[T], r: Reaction[T]): Action[T] = new DeltaMovement(getEnvironment, node)
}
