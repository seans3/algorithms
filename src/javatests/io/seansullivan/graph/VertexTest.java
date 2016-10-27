package io.seansullivan.graph;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

  
/**
 * Tests for Vertex.
 *
 * @author Sean Sullivan (seans3@gmail.com) 
 */
public class VertexTest {

  private static final int TEST_ID = 898;
  private static final String TEST_LABEL = "OAK";

  @Rule
  public final ExpectedException thrown = ExpectedException.none();

  private Vertex vertex;
  
  @Before
  public void setUp() throws Exception {
    vertex = new Vertex(TEST_ID, TEST_LABEL);
  }

  @Test
  public void badArgumentsToConstructorThrowsException() throws Exception {
    thrown.expect(IllegalArgumentException.class);
    vertex = new Vertex(-1, TEST_LABEL);
    thrown.expect(NullPointerException.class);
    vertex = new Vertex(TEST_ID, null);
    thrown.expect(IllegalArgumentException.class);
    vertex = new Vertex(TEST_ID, "");
  }

  @Test
  public void gettersReturnCorrectValues() throws Exception {
    assertEquals(TEST_ID, vertex.getId());
    assertTrue(vertex.hasLabel());
    assertEquals(TEST_LABEL, vertex.getLabel());
    vertex = new Vertex(TEST_ID);
    assertFalse(vertex.hasLabel());
  }

  @Test
  public void testEquals() throws Exception {
    Vertex other = new Vertex(TEST_ID);
    assertTrue(vertex.equals(other));
    assertTrue(other.equals(vertex));
    other = new Vertex(111);
    assertFalse(vertex.equals(other));
    assertFalse(other.equals(vertex));
  }

  @Test
  public void testHashCode() throws Exception {
    Vertex other = new Vertex(TEST_ID);
    assertEquals(vertex.hashCode(), other.hashCode());
    other = new Vertex(111);
    assertNotEquals(vertex.hashCode(), other.hashCode());
  }

}
