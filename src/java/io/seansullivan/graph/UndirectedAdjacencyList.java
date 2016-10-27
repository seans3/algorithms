package io.seansullivan.graph;


import com.google.common.base.Preconditions;


/**
 *
 *
 * @author Sean Sullivan (seans3@gmail.com)
 *
 * Copyright (C) 2016 Sean Sullivan
 */
public class UndirectedAdjacencyList extends AdjacencyList {

  public void addEdge(UndirectedEdge edge) {
    Preconditions.checkNotNull(edge);

    Vertex first = edge.getFirst();
    Vertex second = edge.getSecond();
    addVertex(first);
    addVertex(second);
    edges.add(edge);
    // Undirected edge implies symmetric path between vertices.
    adjList.put(first, second);
    adjList.put(second, first);
  }

}
