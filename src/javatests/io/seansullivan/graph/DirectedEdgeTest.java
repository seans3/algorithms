package io.seansullivan.graph;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

  
/**
 * Tests for Edges.
 *
 * @author Sean Sullivan (seans3@gmail.com) 
 */
public class DirectedEdgeTest {

  private static final int TAIL_ID = 111;
  private static final int HEAD_ID = 222;
  private static final float TEST_WEIGHT = 89.3f;
  
  @Rule
  public final ExpectedException thrown = ExpectedException.none();

  private Vertex tail;
  private Vertex head;
  private DirectedEdge edge;
  
  @Before
  public void setUp() throws Exception {
    tail = new Vertex(TAIL_ID);
    head = new Vertex(HEAD_ID);
    edge = new DirectedEdge(tail, head, TEST_WEIGHT);
  }

  @Test
  public void badArgumentsToConstructorThrowsException() throws Exception {
    thrown.expect(NullPointerException.class);
    edge = new DirectedEdge(null, head);
    edge = new DirectedEdge(tail, null);
  }

  @Test
  public void gettersReturnCorrectValues() throws Exception {
    assertEquals(tail, edge.getTail());
    assertEquals(head, edge.getHead());
    assertEquals(TEST_WEIGHT, edge.getWeight(), 0.0f);
    edge = new DirectedEdge(tail, head);
    assertEquals(Edge.DEFAULT_EDGE_WEIGHT, edge.getWeight(), 0.0f);
  }

  @Test
  public void comparesWeights() throws Exception {
    assertEquals(0, edge.compareTo(edge));
    final float LARGER_WEIGHT = 90.0f;
    Vertex head2 = new Vertex(333);
    DirectedEdge secondEdge = new DirectedEdge(tail, head2, LARGER_WEIGHT);
    assertEquals(0, secondEdge.compareTo(secondEdge));
    assertEquals(-1, edge.compareTo(secondEdge));
    assertEquals(1, secondEdge.compareTo(edge));
  }

  @Test
  public void undirectedEdgeWithSymmetricVerticesNotEqual() throws Exception {
    // Reflexivity property.
    assertTrue(edge.equals(edge));
    // Same tail and head vertices are the same.
    Vertex head2 = new Vertex(HEAD_ID);
    DirectedEdge thirdEdge = new DirectedEdge(tail, head2);
    assertTrue(edge.equals(thirdEdge));
    assertTrue(thirdEdge.equals(edge));
    // Symmetric DirectedEdges are NOT equal.
    DirectedEdge symmetricEdge = new DirectedEdge(head, tail);
    assertTrue(symmetricEdge.equals(symmetricEdge));
    assertFalse(edge.equals(symmetricEdge));
    // Edges with different vertices are NOT equal.
    head2 = new Vertex(333);
    thirdEdge = new DirectedEdge(tail, head2);
    assertFalse(edge.equals(thirdEdge));
    assertFalse(thirdEdge.equals(edge));
  }

  @Test
  public void undirectedEdgeWithSymmetricVerticesSameHashCode() throws Exception {
    // Same tail and head vertices equals same hash code.
    Vertex head2 = new Vertex(HEAD_ID);
    DirectedEdge thirdEdge = new DirectedEdge(tail, head2);
    assertEquals(edge.hashCode(), thirdEdge.hashCode());
    // Symmetric DirectedEdges have different hash codes.
    DirectedEdge symmetricEdge = new DirectedEdge(head, tail);
    assertNotEquals(edge.hashCode(), symmetricEdge.hashCode());
    // Edges with different vertices have different hash codes.
    Vertex vertex3 = new Vertex(333);
    thirdEdge = new DirectedEdge(tail, vertex3);
    assertNotEquals(edge.hashCode(), thirdEdge.hashCode());
  }
}
