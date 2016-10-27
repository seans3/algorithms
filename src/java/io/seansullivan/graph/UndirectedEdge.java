package io.seansullivan.graph;

import java.lang.Comparable;
import java.util.HashSet;
import java.util.Set;

import com.google.common.base.Preconditions;


/**
 * Encapsulates an undirected edge (arc) within a Graph. An undirected
 * edge means that a path from vertex1 to vertex2 implies a path
 * from vertex2 to vertex1. Ordering of the vertices doesn't matter.
 *
 * @author Sean Sullivan (seans3@gmail.com)
 *
 * Copyright (C) 2016 Sean Sullivan
 */
public class UndirectedEdge extends Edge {

  public UndirectedEdge(Vertex v1, Vertex v2) {
    super(v1, v2);
  }

  public UndirectedEdge(Vertex v1, Vertex v2, float weight) {
    super(v1, v2, weight);
  }

  /**
   * Given the passed vertex, return the "other" vertex for this edge.
   *
   * @param first one of the vertices for this edge.
   * @return the opposite vertex from the passed vertex for this edge.
   */
  public Vertex other(Vertex v) {
    Preconditions.checkNotNull(v);
    Preconditions.checkArgument(v.equals(getFirst()) || v.equals(getSecond()));

    Vertex otherVertex = getFirst();
    if (v.equals(otherVertex)) {
      otherVertex = getSecond();
    }
    return otherVertex;
  }

  /**
   * Returns a set of both vertices connected by this edge. This can be
   * used for equality measurements for an undirected edge.
   * 
   * @return Set of both vertices connected by this Edge.
   */
  Set<Vertex> both() {
    Set<Vertex> bothVertices = new HashSet<Vertex>();
    bothVertices.add(getFirst());
    bothVertices.add(getSecond());
    return bothVertices;
  }

  /**
   * Two edges are equal if the set of their vertices is the same. In other
   * words, symmetric edges are equal. UndirectedEdge(v1, v2) is the same
   * as UndirectedEdge(v2, v1). The ordering of the vertices is irrelevant.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    } else if (!(o instanceof UndirectedEdge)) {
      return false;
    }
    UndirectedEdge other = (UndirectedEdge) o;

    boolean same = false;
    if (this.both().equals(other.both())) {
      same = true;
    }

    return same;
  }

  @Override
  public int hashCode() {
    return both().hashCode();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(getFirst()).append("-").append(getSecond());
    sb.append(" ").append(getWeight());
    return sb.toString();
  }
  
}
