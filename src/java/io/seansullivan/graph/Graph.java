package io.seansullivan.graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.Comparable;
import java.lang.System;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.Set;

import com.google.common.base.Preconditions;

/**
 *
 *
 * @author Sean Sullivan (seans3@gmail.com)
 *
 * Copyright (C) 2016 Sean Sullivan
 */
public class Graph {

  private int numVertices;
  private List<Edge> edges;

  public Graph(int numVertices) {
    Preconditions.checkArgument(numVertices > 0);

    this.numVertices = numVertices;
    this.edges = new ArrayList<Edge>();
  }

  public void addEdge(Edge edge) {
    Preconditions.checkNotNull(edge);

    edges.add(edge);
  }

  public int getNumEdges() {
    return edges.size();
  }

  public int minSpanningTree() {    

    Set<Edge> mst = new HashSet<Edge>();  // Set of lowest cost edges.
    Set<Integer> treeVertices = new HashSet<Integer>();
    treeVertices.add(1);  // Add first vertex    
    while (treeVertices.size() < numVertices) {
      // Find lowest cost edge that connect tree vertices to non-tree vertices.
      List<Edge> candidates = new ArrayList<Edge>();
      for (Edge e : edges) {
	Integer v1 = e.getFirst().getId();
	Integer v2 = e.getSecond().getId();
	if (treeVertices.contains(v1)) {
	  if (!treeVertices.contains(v2)) {
	    candidates.add(e);
	  }
	} else if (treeVertices.contains(v2)) {
	  if (!treeVertices.contains(v1)) {
	    candidates.add(e);
	  }
	}
      }
      
      if (candidates.size() == 0) {
	throw new RuntimeException("No Candidates!");
      }

      Collections.sort(candidates);
      Edge lowestCost = candidates.get(0);
      mst.add(lowestCost);
      treeVertices.add(lowestCost.getFirst().getId());
      treeVertices.add(lowestCost.getSecond().getId());
    }

    int totalCost = 0;
    for (Edge e : mst) {
      totalCost += e.getWeight();
    }

    return totalCost;
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Vertices: ").append(numVertices).append("\n");
    for (Edge e : edges) {
      sb.append(e);
    }
    return sb.toString();
  }

  public static void main(String [] args) throws IOException {
    System.out.println("STARTING Prim's MST");
    System.out.println();

    File f = new File("edges.txt");
    BufferedReader r = new BufferedReader(new FileReader(f));
    String line = null;
    String firstLine = r.readLine();
    String [] parts = firstLine.split(" ");
    int numVertices = Integer.parseInt(parts[0]);
    int numEdges = Integer.parseInt(parts[1]);
    System.out.println("Num Vertices: " + numVertices);
    System.out.println("Num Edges: " + numEdges + "\n");    
    Graph g = new Graph(numVertices);
    while ((line = r.readLine()) != null) {
      parts = line.split(" ");
      int vertex1 = Integer.parseInt(parts[0]);
      int vertex2 = Integer.parseInt(parts[1]);
      int weight = Integer.parseInt(parts[2]);
      Edge edge = new UndirectedEdge(new Vertex(vertex1), new Vertex(vertex2), weight);
      g.addEdge(edge);
    }
    r.close();

    System.out.println(g);
    System.out.println("Num Edges Parsed: " + g.getNumEdges());

    System.out.println();
    System.out.println("MST: " + g.minSpanningTree());
    
    System.out.println("FINISHED");
  }
}
