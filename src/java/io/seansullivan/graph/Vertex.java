package io.seansullivan.graph;

import java.util.Objects;

import com.google.common.base.Preconditions;


/**
 * Encapsulates a Vertex (Node) within a Graph.
 *
 * @author Sean Sullivan (seans3@gmail.com)
 *
 * Copyright (C) 2016 Sean Sullivan
 */
public class Vertex {

  private final int id;
  private String label;
  
  public Vertex(int id) {
    Preconditions.checkArgument(id >= 0);

    this.id = id;
    this.label = null;
  }

  public Vertex(int id, String label) {
    this(id);
    Preconditions.checkNotNull(label);
    Preconditions.checkArgument(!label.isEmpty());
    this.label = label;
  }
  
  public int getId() {
    return id;
  }

  public boolean hasLabel() {
    return (label != null);
  }
  
  public String getLabel() {
    return label;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    } else if (!(o instanceof Vertex)) {
      return false;
    }
    Vertex other = (Vertex) o;

    return (this.getId() == other.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId());
  }

  @Override
  public String toString() {
    StringBuilder buffer = new StringBuilder();
    if (hasLabel()) {
      buffer.append(getLabel()).append(" ");
    }
    buffer.append("[").append(getId()).append("]");
    return buffer.toString();
  }

}
