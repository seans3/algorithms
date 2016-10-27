package io.seansullivan.graph;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

  
/**
 * Tests for UndirectedEdges.
 *
 * @author Sean Sullivan (seans3@gmail.com) 
 */
public class UndirectedEdgeTest {

  private static final int VERTEX_1_ID = 111;
  private static final int VERTEX_2_ID = 222;
  private static final float TEST_WEIGHT = 89.3f;
  
  @Rule
  public final ExpectedException thrown = ExpectedException.none();

  private Vertex vertex1;
  private Vertex vertex2;
  private UndirectedEdge edge;
  
  @Before
  public void setUp() throws Exception {
    vertex1 = new Vertex(VERTEX_1_ID);
    vertex2 = new Vertex(VERTEX_2_ID);
    edge = new UndirectedEdge(vertex1, vertex2, TEST_WEIGHT);
  }

  @Test
  public void badArgumentsToConstructorThrowsException() throws Exception {
    thrown.expect(NullPointerException.class);
    edge = new UndirectedEdge(null, vertex2);
    edge = new UndirectedEdge(vertex1, null);
  }

  @Test
  public void gettersReturnCorrectValues() throws Exception {
    assertEquals(vertex1, edge.getFirst());
    assertEquals(vertex2, edge.getSecond());
    assertEquals(TEST_WEIGHT, edge.getWeight(), 0.0f);
    edge = new UndirectedEdge(vertex1, vertex2);
    assertEquals(Edge.DEFAULT_EDGE_WEIGHT, edge.getWeight(), 0.0f);
  }

  @Test
  public void otherReturnsOppositeVertex() throws Exception {
    assertEquals(vertex2, edge.other(vertex1));
    assertEquals(vertex1, edge.other(vertex2));
    Vertex thirdVertex = new Vertex(333);
    thrown.expect(IllegalArgumentException.class);
    edge.other(thirdVertex);
  }

  @Test
  public void comparesWeights() throws Exception {
    assertEquals(0, edge.compareTo(edge));
    final float LARGER_WEIGHT = 90.0f;
    Vertex vertex3 = new Vertex(333);
    Edge secondEdge = new UndirectedEdge(vertex1, vertex3, LARGER_WEIGHT);
    assertEquals(0, secondEdge.compareTo(secondEdge));
    assertEquals(-1, edge.compareTo(secondEdge));
    assertEquals(1, secondEdge.compareTo(edge));
  }

  @Test
  public void undirectedEdgeEqualsWithSymmetricVertices() throws Exception {
    assertTrue(edge.equals(edge));
    Edge secondEdge = new UndirectedEdge(vertex2, vertex1);  // Symmetric to first edge.
    assertTrue(secondEdge.equals(secondEdge));
    assertTrue(edge.equals(secondEdge));
    Vertex vertex3 = new Vertex(333);
    Edge thirdEdge = new UndirectedEdge(vertex1, vertex3);
    assertFalse(edge.equals(thirdEdge));
    assertFalse(thirdEdge.equals(edge));
  }

  @Test
  public void undirectedEdgeWithSymmetricVerticesSameHashCode() throws Exception {
    Edge secondEdge = new UndirectedEdge(vertex2, vertex1);  // Symmetric to first edge.
    assertEquals(edge.hashCode(), secondEdge.hashCode());
    Vertex vertex3 = new Vertex(333);
    Edge thirdEdge = new UndirectedEdge(vertex1, vertex3);
    assertNotEquals(edge.hashCode(), thirdEdge.hashCode());
  }
}
