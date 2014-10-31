package rolx

import Grapher._
import org.scalatest.{Matchers, FlatSpec}
import rolx.Messages.{Node, Link}

class GrapherSpec extends FlatSpec with Matchers {

  it should "return a valid graph link" in {
    toLink("1,2,2") should be (Link(Node(1), Node(2), 2))
  }

  it should "add out-link to a node, with zero out-links" in {
    val node = Node(1)
    val link = Link(Node(1), Node(2), 2)

    addOutLink(None, link) should be (node.copy(outLink = Set(link)))
    addOutLink(Some(node), link) should be (node.copy(outLink = node.outLink ++ Set(link)))
  }

  it should "add out-link to a node, with few out-links" in {
    val link = Link(Node(1), Node(2), 2)
    val node = Node(1, outLink = Set(Link(Node(1), Node(3), 1)))
    addOutLink(Some(node), link) should be (node.copy(outLink = node.outLink ++ Set(link)))
  }

  it should "add in-link to a node, with zero out-links" in {
    val link = Link(Node(1), Node(2), 2)
    addInLink(None, link) should be (Node(2, inLink = Set(link)))

    val node = Node(2)
    addInLink(Some(node), link) should be (node.copy(inLink = node.inLink ++ Set(link)))
  }

  it should "add in link to a node, with few in-links" in {
    val link = Link(Node(1), Node(2), 2)
    val node = Node(1, inLink = Set(Link(Node(1), Node(3), 1)))
    addInLink(Some(node), link) should be (node.copy(inLink = node.inLink ++ Set(link)))
  }

  it should "return a graph for set of links" in {
    implicit def idToNode(id: Int) = Node(id)

    //0,1,1
    //1,0,1
    //0,2,1
    //2,1,1
    val links = Iterator(Link(0, 1, 1), Link(1, 0, 1), Link(0, 2, 1), Link(2, 1, 1))
    val graph = makeGraph(links)

    graph.nodes(0) should be (Node(0, Set(Link(1, 0, 1)), Set(Link(0, 1, 1), Link(0, 2, 1))))
    graph.nodes(2) should be (Node(2, Set(Link(0, 2, 1)), Set(Link(2, 1, 1))))
    graph.nodes(1) should be (Node(1, Set(Link(0, 1, 1), Link(2, 1, 1)), Set(Link(1, 0, 1))))
  }
}
