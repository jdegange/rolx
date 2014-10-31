package rolx

object Messages {

  case class Link(src: Node, dst: Node, weight: Double = 1.0) {

  }

  case class Node(id: Int, inLink: Set[Link] = Set.empty, outLink: Set[Link] = Set.empty, attributes: Map[String, Double] = Map.empty) {

  }

  case class Graph(nodes: Map[Int, Node])
}
