package it.unibo.alchemist

import it.unibo.alchemist.model.implementations.actions.AbstractMoveNode
import it.unibo.alchemist.model.implementations.nodes.SimpleNodeManager
import it.unibo.alchemist.model.interfaces.{Action, Environment, Node, Position, Reaction}

class DeltaMovement[T, P <: Position[P]](env : Environment[T, P], node : Node[T]) extends AbstractMoveNode[T, P](env, node) {
  val manager = new SimpleNodeManager[T](node)

  def getDeltaVector : (Double, Double) = (manager.getOrElse("dx", 0.0), manager.getOrElse("dx", 0.0))
  override def getNextPosition: P = {
    println(getDeltaVector)
    getEnvironment.getPosition(node)
  }

  override def cloneAction(n: Node[T], r: Reaction[T]): Action[T] = new DeltaMovement(getEnvironment, node)
}
