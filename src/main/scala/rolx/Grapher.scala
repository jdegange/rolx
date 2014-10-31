package rolx

import scala.io.Source._
import rolx.Messages.{Graph, Link, Node}


object Grapher {

  def main(args: Array[String]): Unit = {
    val List(graphFile, featuresFile) = args.toList
    val graph = readGraph(graphFile)
  }

  def readGraph(graphFile: String): Graph = {
    makeGraph(fromFile(graphFile).getLines.map(toLink))
  }

  def makeGraph(links: Iterator[Link]): Graph = {
    var nodes = Map[Int, Node]()
    links.foreach { link =>
      val inNode = addInLink(nodes.get(link.dst.id), link)
      val outNode = addOutLink(nodes.get(link.src.id), link)
      nodes = nodes ++ Map(inNode.id -> inNode, outNode.id -> outNode)
    }
    Graph(nodes)
  } 
  
  def addInLink(node: Option[Node], link: Link): Node = node match {
    case Some(nd) => nd.copy(inLink = nd.inLink ++ Set(link))
    case None => Node(link.dst.id, inLink = Set(link))
  }

  def addOutLink(node: Option[Node], link: Link): Node = node match {
    case Some(nd) => nd.copy(outLink = nd.outLink ++ Set(link))
    case None => Node(link.src.id, outLink = Set(link))
  }

  def toLink(link: String) = {
    val List(src, dst, wt) = link.split(",").toList
    Link(Node(src.toInt), Node(dst.toInt), wt.toDouble)
  }
}
