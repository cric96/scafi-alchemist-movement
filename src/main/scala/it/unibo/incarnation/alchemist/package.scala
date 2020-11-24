package it.unibo.incarnation

import it.unibo.alchemist.model.implementations.nodes.NodeManager

import scala.util.Try

package object alchemist {
  implicit class RichNodeManager(node : NodeManager) {
    def getOrElse[V](molecule : String, orElse : V) : V = Try { node.get[V](molecule) } getOrElse(orElse)
  }
}
