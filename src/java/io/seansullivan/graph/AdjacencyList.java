package io.seansullivan.graph;

import java.lang.Comparable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;


/**
 * Represents a graph by storing the adjacency information
 * connecting vertices within the graph.  Parallel edges are
 * not allowed. The two concrete subclasses from this
 * abstract class are UndirectedAdjacencyList and
 * DirectedAdjacencyList.
 *
 * @author Sean Sullivan (seans3@gmail.com)
 *
 * TODO:
 *   Breadth-First Search
 *   Depth-First Search
 *   Minimum Spanning Tree
 *   isConnected/Connected Components
 *
 * Copyright (C) 2016 Sean Sullivan
 */
public abstract class AdjacencyList {

  protected Set<Vertex> vertices;
  protected List<Edge> edges;
  protected SetMultimap<Vertex, Vertex> adjList;

  /**
   * Stores a set of vertices, a list of edges, and a
   * Map of Vertex to list of vertices for adjacency info.
   * Some of the stored information is somewhat redundant.
   * But we need a set of the vertices in case some vertices
   * are not connected. Additionally, we must store the 
   * edges in order to keep the edge weights. The adjacency
   * list should be able to be reconstructed from the 
   * Edge list.
   */
  public AdjacencyList() {
    this.vertices = new HashSet<Vertex>();
    this.edges = new ArrayList<Edge>();
    this.adjList = HashMultimap.create();
  }

  public boolean isEmpty() {
    return vertices.isEmpty() && edges.isEmpty();
  }

  public void addVertex(Vertex v) {
    vertices.add(v);
  }

  public int getNumVertices() {
    return adjList.keySet().size();
  }

  public Set<Vertex> getVertices() {
    return vertices;
  }

  public int getNumEdges() {
    return edges.size();
  }

  public List<Edge> getEdges() {
    return edges;
  }

  public float getTotalEdgeCost() {
    float totalEdgeCost = 0.0f;
    for (Edge edge : getEdges()) {
      totalEdgeCost += edge.getWeight();
    }
    return totalEdgeCost;
  }

  /**
   * @return a random vertex within the set of vertices.
   * @throws IllegalStateException if there are no vertices.
   */
  Vertex initialVertex() {
    Preconditions.checkState(getNumVertices() > 0);
    Iterator<Vertex> i = vertices.iterator();
    return i.next();
  }

  /**
   * @return the set of vertices explored by a breadth-first search.
   */
  public Set<Vertex> breadthFirstSearch() {
    return breadthFirstSearch(initialVertex());
  }

  /**
   * Search all reachable vertices, starting with the initial vertex.
   * Explores in a "cautious" breadth-first manner by probing all edges
   * of a current vertex before moving on to the next vertex.
   *
   * @return the set of vertices explored by a breadth-first search.
   */
  public Set<Vertex> breadthFirstSearch(Vertex initialVertex) {
    // Initialize the set of "explored" vertices with the initial vertex.
    Set<Vertex> explored = new HashSet<Vertex>();
    explored.add(initialVertex);
    // Create a queue, also initialized with the initial vertex.
    Queue<Vertex> working = new LinkedList<Vertex>();
    working.add(initialVertex);
    // While there are vertices in the queue.
    while (working.size() > 0) {
      // 1) Get vertex from the front of the queue.
      // 2) Check all adjacent vertices reachable from this current vertex.
      // 3) If an adjacent vertex has not been explored, then
      //  a) Add it to the "explored" vertices
      //  b) Add it to the end of the queue.
      Vertex current = working.remove();
      for (Vertex adjacent : adjList.get(current)) {
	if (!explored.contains(adjacent)) {
	  explored.add(adjacent);
	  working.add(adjacent);
	}
      }
    }
    // Return all the explored vertices.
    return explored;
  }

  /**
   * @return the number of edges incident to this vertex.
   */
  public int degree(Vertex v) {
    Preconditions.checkNotNull(v);
    Preconditions.checkArgument(adjList.containsKey(v));
    
    return adjList.get(v).size();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    } else if (!(o instanceof AdjacencyList)) {
      return false;
    }
    AdjacencyList other = (AdjacencyList) o;

    boolean same = false;
    if (this.vertices.equals(other.vertices) &&
	this.edges.equals(other.edges)) {
      same = true;
    }
    return same;
  }

  @Override
  public int hashCode() {
    return Objects.hash(vertices, edges);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Vertices: ").append(getNumVertices()).append("\n");
    for (Vertex v : getVertices()) {
      sb.append(v).append("\n");
    }
    sb.append("Edges: ").append(getNumEdges()).append("\n");
    for (Edge e : getEdges()) {
      sb.append(e).append("\n");
    }
    return sb.toString();
  }
}
