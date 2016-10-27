package io.seansullivan.graph;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

  
/**
 * Tests for UndirectedAdjacencyList representation of a Graph.
 *
 * @author Sean Sullivan (seans3@gmail.com) 
 */
public class UndirectedAdjacencyListTest {

  private static final int VERTEX_1_ID = 1;
  private static final int VERTEX_2_ID = 2;
  private static final int VERTEX_3_ID = 3;
  private static final int VERTEX_4_ID = 4;
  private static final int VERTEX_5_ID = 5;
  private static final int VERTEX_6_ID = 6;

  private static final int NUM_VERTICES = 6;
  private static final int NUM_EDGES = 8;

  @Rule
  public final ExpectedException thrown = ExpectedException.none();

  private Vertex vertex1;
  private Vertex vertex2;
  private Vertex vertex3;
  private Vertex vertex4;
  private Vertex vertex5;
  private Vertex vertex6;
  private Set<Vertex> allVertices;  
  private UndirectedEdge edge12;
  private UndirectedEdge edge13;
  private UndirectedEdge edge23;
  private UndirectedEdge edge24;
  private UndirectedEdge edge25;
  private UndirectedEdge edge35;
  private UndirectedEdge edge46;
  private UndirectedEdge edge56;
  private List<Edge> allEdges;
  private UndirectedAdjacencyList adjList;

  @Before
  public void setUp() throws Exception {
    adjList = new UndirectedAdjacencyList();

    vertex1 = new Vertex(VERTEX_1_ID);
    vertex2 = new Vertex(VERTEX_2_ID);
    vertex3 = new Vertex(VERTEX_3_ID);
    vertex4 = new Vertex(VERTEX_4_ID);
    vertex5 = new Vertex(VERTEX_5_ID);
    vertex6 = new Vertex(VERTEX_6_ID);
    allVertices = new HashSet<Vertex>();
    allVertices.add(vertex1);
    allVertices.add(vertex2);
    allVertices.add(vertex3);
    allVertices.add(vertex4);
    allVertices.add(vertex5);
    allVertices.add(vertex6);

    edge12 = new UndirectedEdge(vertex1, vertex2);
    edge13 = new UndirectedEdge(vertex1, vertex3);
    edge23 = new UndirectedEdge(vertex2, vertex3);
    edge24 = new UndirectedEdge(vertex2, vertex4);
    edge25 = new UndirectedEdge(vertex2, vertex5);
    edge35 = new UndirectedEdge(vertex3, vertex5);
    edge46 = new UndirectedEdge(vertex4, vertex6);
    edge56 = new UndirectedEdge(vertex5, vertex6);
    allEdges = new ArrayList<Edge>();
    allEdges.add(edge12);
    allEdges.add(edge13);
    allEdges.add(edge23);
    allEdges.add(edge24);
    allEdges.add(edge25);
    allEdges.add(edge35);
    allEdges.add(edge46);
    allEdges.add(edge56);    

    adjList.addEdge(edge12);
    adjList.addEdge(edge13);
    adjList.addEdge(edge23);
    adjList.addEdge(edge24);
    adjList.addEdge(edge25);
    adjList.addEdge(edge35);
    adjList.addEdge(edge46);
    adjList.addEdge(edge56);
  }

  @Test
  public void emptyAdjacencyList() throws Exception {
    assertFalse(adjList.isEmpty());
    adjList = new UndirectedAdjacencyList();
    assertTrue(adjList.isEmpty());
    adjList.addVertex(vertex1);
    assertFalse(adjList.isEmpty()); // Single vertex--not empty
    adjList = new UndirectedAdjacencyList();
    assertTrue(adjList.isEmpty());
    adjList.addEdge(edge12);
    assertFalse(adjList.isEmpty()); // Single edge-not empty
  }

  @Test
  public void numVerticesReturnsCorrectNumberOfVertices() throws Exception {
    assertEquals(allVertices.size(), adjList.getNumVertices());
    adjList = new UndirectedAdjacencyList();
    assertEquals(0, adjList.getNumVertices());
  }

  @Test
  public void getVerticesReturnsAllVertices() throws Exception {
    assertEquals(allVertices, adjList.getVertices());
    adjList = new UndirectedAdjacencyList();
    assertEquals(new HashSet<Vertex>(), adjList.getVertices());
  }

  @Test
  public void numEdgesReturnsCorrectNumberOfEdges() throws Exception {
    assertEquals(allEdges.size(), adjList.getNumEdges());
    adjList = new UndirectedAdjacencyList();
    assertEquals(0, adjList.getNumEdges());
  }

  @Test
  public void getEdgesReturnsAllEdges() throws Exception {
    assertEquals(allEdges, adjList.getEdges());
    adjList = new UndirectedAdjacencyList();
    assertEquals(new ArrayList<Edge>(), adjList.getEdges());
  }

  @Test
  public void getTotalEdgeCostReturnsCorrectSum() throws Exception {    
    assertEquals(NUM_EDGES * Edge.DEFAULT_EDGE_WEIGHT, adjList.getTotalEdgeCost(), 0.0f);
    final float EDGE_COST_1_2 = 2.3f;
    final float EDGE_COST_1_3 = 3.677f;
    final float EDGE_COST_1_4 = 7.213f;
    final float TOTAL_EDGE_COST = EDGE_COST_1_2 + EDGE_COST_1_3 + EDGE_COST_1_4;
    edge12 = new UndirectedEdge(vertex1, vertex2, EDGE_COST_1_2);
    edge13 = new UndirectedEdge(vertex1, vertex3, EDGE_COST_1_3);
    UndirectedEdge edge14 = new UndirectedEdge(vertex1, vertex4, EDGE_COST_1_4);
    adjList = new UndirectedAdjacencyList();
    adjList.addEdge(edge12);
    adjList.addEdge(edge13);
    adjList.addEdge(edge14);
    assertEquals(TOTAL_EDGE_COST, adjList.getTotalEdgeCost(), 0.0f);
  }

  @Test
  public void vertexDegreeMeasuresEdgesIncident() throws Exception {
    assertEquals(2, adjList.degree(vertex1));
    assertEquals(4, adjList.degree(vertex2));
    assertEquals(2, adjList.degree(vertex6));
  }

  @Test
  public void equalsDependsOnVerticesAndEdges() throws Exception {
    assertTrue(adjList.equals(adjList));  // Reflexivity
    UndirectedAdjacencyList adjList2 = new UndirectedAdjacencyList();
    assertFalse(adjList.equals(adjList2));
    UndirectedAdjacencyList adjList3 = new UndirectedAdjacencyList();
    assertTrue(adjList2.equals(adjList3)); // Empty adjLists are the same
    adjList2.addVertex(vertex5);
    adjList3.addVertex(vertex5);
    assertTrue(adjList2.equals(adjList3));
    adjList2.addEdge(edge35);
    adjList3.addEdge(edge35);
    assertTrue(adjList2.equals(adjList3));    
    adjList2.addEdge(edge56);
    assertFalse(adjList2.equals(adjList3));
  }

  @Test
  public void sameAdjListHaveSameHashCode() throws Exception {
    UndirectedAdjacencyList adjList2 = new UndirectedAdjacencyList();
    UndirectedAdjacencyList adjList3 = new UndirectedAdjacencyList();
    assertEquals(adjList2.hashCode(), adjList3.hashCode());
    adjList2.addVertex(vertex5);
    adjList3.addVertex(vertex5);
    assertEquals(adjList2.hashCode(), adjList3.hashCode());
    adjList2.addEdge(edge35);
    adjList3.addEdge(edge35);
    assertEquals(adjList2.hashCode(), adjList3.hashCode());
    adjList2.addEdge(edge56);
    assertNotEquals(adjList2.hashCode(), adjList3.hashCode());
  }

}
