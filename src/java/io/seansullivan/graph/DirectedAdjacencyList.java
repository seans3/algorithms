package io.seansullivan.graph;


import com.google.common.base.Preconditions;


/**
 *
 *
 * @author Sean Sullivan (seans3@gmail.com)
 *
 * Copyright (C) 2016 Sean Sullivan
 */
public class DirectedAdjacencyList extends AdjacencyList {

  public void addDirectedEdge(DirectedEdge edge) {
    Preconditions.checkNotNull(edge);

    Vertex tail = edge.getTail();
    Vertex head = edge.getHead();
    vertices.add(tail);
    vertices.add(head);
    edges.add(edge);
    // Directed edge is NOT symmetric.
    adjList.put(tail, head);
  }
  
}
