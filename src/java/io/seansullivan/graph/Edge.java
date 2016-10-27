package io.seansullivan.graph;

import java.lang.Comparable;
import java.util.HashSet;
import java.util.Set;

import com.google.common.base.Preconditions;


/**
 * Abstract base class for both undirected and directed edges.
 * If not weight is specified, then the default weight is 1.
 *
 * @author Sean Sullivan (seans3@gmail.com)
 *
 * Copyright (C) 2016 Sean Sullivan
 */
public abstract class Edge implements Comparable<Edge> {

  public static final float DEFAULT_EDGE_WEIGHT = 1.0f;
  
  private final Vertex first;
  private final Vertex second;
  private float weight;

  /**
   * 
   *
   * @param first
   * @param second
   */
  public Edge(Vertex first, Vertex second) {
    Preconditions.checkNotNull(first);
    Preconditions.checkNotNull(second);

    this.first = first;
    this.second = second;
    this.weight = DEFAULT_EDGE_WEIGHT;
  }

  /**
   * 
   *
   * @param first
   * @param second
   * @param weight
   */
  public Edge(Vertex first, Vertex second, float weight) {
    this(first, second);
    this.weight = weight;
  }

  public Vertex getFirst() {
    return first;
  }

  public Vertex getSecond() {
    return second;
  }

  public float getWeight() {
    return weight;
  }

  public abstract boolean crosses(Set<Vertex> vertices);
  
  /**
   * Edges are compared based on weights in ascending order.
   */
  public int compareTo(Edge other) {
    if (this.weight < other.weight) {
      return -1;
    } else if (this.weight > other.weight) {
      return 1;
    } else {
      return 0;
    }
  }

}
