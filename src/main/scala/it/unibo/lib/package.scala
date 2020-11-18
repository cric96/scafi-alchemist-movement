package it.unibo

import it.unibo.scafi.space.{Point2D, Point3D}

package object lib {
  implicit class RichPoint3D(p : Point3D) {
    val module : Double = math.hypot(p.x, p.y)
    val unary : Point2D = Point2D(p.x / module, p.y / module)
    def unary_- : Point3D = Point3D(-p.x, -p.y, -p.z)
    def -(other : Point3D) : Point3D = p + (- other)
    def *(alpha : Double) : Point3D = Point3D(p.x * alpha, p.y * alpha, p.z * alpha)
    def /(alpha : Double) : Point3D = p * (1.0 / alpha)
    def ===(other : Point3D) : Boolean = other.x == p.x && other.y == p.x && other.z == p.z //todo solve the bug in scafi lib
  }
}
