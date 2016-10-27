package io.seansullivan.graph;

import java.lang.Comparable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.google.common.base.Preconditions;


/**
 * Encapsulates a directed edge (arc) within a Graph. The path
 * is only valid from the TAIL (origin) to the HEAD (destination),
 * not vice versa.
 *
 * @author Sean Sullivan (seans3@gmail.com)
 *
 * Copyright (C) 2016 Sean Sullivan
 */
public class DirectedEdge extends Edge {

  public DirectedEdge(Vertex tail, Vertex head) {
    super(tail, head);
  }

  public DirectedEdge(Vertex tail, Vertex head, float weight) {
    super(tail, head, weight);
  }

  /**
   * @return "from" or origin Vertex for directed path.
   */
  public Vertex getTail() {
    return getFirst();
  }

  /**
   * @return "to" or destination Vertex for directed path.
   */
  public Vertex getHead() {
    return getSecond();
  }

  /**
   * @return true if the edge is a "frontier" edge in the sense that
   *    the tail vertex of the edge lies within the set of vertices, and
   *    head vertex is outside the set of vertices.
   */
  public boolean crosses(Set<Vertex> vertices) {
    boolean isCrossingEdge = false;
    if (vertices.contains(getTail()) &&
	!vertices.contains(getHead())) {
	isCrossingEdge = true;
    }
    return isCrossingEdge;
  }
  
  /**
   * Ordering of edge vertices matters for equality. We don't
   * consider weights, since we don't allow parallel vertices.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    } else if (!(o instanceof DirectedEdge)) {
      return false;
    }
    DirectedEdge other = (DirectedEdge) o;

    boolean same = false;
    if (this.getTail().equals(other.getTail()) &&
	this.getHead().equals(other.getHead())) {
      same = true;
    }

    return same;
  }

  /**
   * Returns hash of tail and head vertices (but not weight).
   */
  @Override
  public int hashCode() {
    return Objects.hash(getTail(), getHead());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(getTail()).append("->").append(getHead());
    sb.append(" ").append(getWeight());
    return sb.toString();
  }
  
}
