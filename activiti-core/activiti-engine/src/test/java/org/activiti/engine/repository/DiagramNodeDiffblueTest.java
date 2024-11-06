/*
 * Copyright 2010-2020 Alfresco Software, Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.activiti.engine.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class DiagramNodeDiffblueTest {
  /**
   * Test getters and setters.
   * <ul>
   *   <li>Then return toString is
   * {@code id=null, x=2.0, y=3.0, width=10.0, height=10.0}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link DiagramNode#DiagramNode()}
   *   <li>{@link DiagramNode#setHeight(Double)}
   *   <li>{@link DiagramNode#setWidth(Double)}
   *   <li>{@link DiagramNode#setX(Double)}
   *   <li>{@link DiagramNode#setY(Double)}
   *   <li>{@link DiagramNode#toString()}
   *   <li>{@link DiagramNode#getHeight()}
   *   <li>{@link DiagramNode#getWidth()}
   *   <li>{@link DiagramNode#getX()}
   *   <li>{@link DiagramNode#getY()}
   *   <li>{@link DiagramNode#isEdge()}
   *   <li>{@link DiagramNode#isNode()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters_thenReturnToStringIsIdNullX20Y30Width100Height100() {
    // Arrange and Act
    DiagramNode actualDiagramNode = new DiagramNode();
    actualDiagramNode.setHeight(10.0d);
    actualDiagramNode.setWidth(10.0d);
    actualDiagramNode.setX(2.0d);
    actualDiagramNode.setY(3.0d);
    String actualToStringResult = actualDiagramNode.toString();
    Double actualHeight = actualDiagramNode.getHeight();
    Double actualWidth = actualDiagramNode.getWidth();
    Double actualX = actualDiagramNode.getX();
    Double actualY = actualDiagramNode.getY();
    boolean actualIsEdgeResult = actualDiagramNode.isEdge();
    boolean actualIsNodeResult = actualDiagramNode.isNode();

    // Assert that nothing has changed
    assertEquals("id=null, x=2.0, y=3.0, width=10.0, height=10.0", actualToStringResult);
    assertEquals(10.0d, actualHeight.doubleValue(), 0.0);
    assertEquals(10.0d, actualWidth.doubleValue(), 0.0);
    assertEquals(2.0d, actualX.doubleValue(), 0.0);
    assertEquals(3.0d, actualY.doubleValue(), 0.0);
    assertFalse(actualIsEdgeResult);
    assertTrue(actualIsNodeResult);
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then return Id is {@code 42}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link DiagramNode#DiagramNode(String)}
   *   <li>{@link DiagramNode#setHeight(Double)}
   *   <li>{@link DiagramNode#setWidth(Double)}
   *   <li>{@link DiagramNode#setX(Double)}
   *   <li>{@link DiagramNode#setY(Double)}
   *   <li>{@link DiagramNode#toString()}
   *   <li>{@link DiagramNode#getHeight()}
   *   <li>{@link DiagramNode#getWidth()}
   *   <li>{@link DiagramNode#getX()}
   *   <li>{@link DiagramNode#getY()}
   *   <li>{@link DiagramNode#isEdge()}
   *   <li>{@link DiagramNode#isNode()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters_when42_thenReturnIdIs42() {
    // Arrange and Act
    DiagramNode actualDiagramNode = new DiagramNode("42");
    actualDiagramNode.setHeight(10.0d);
    actualDiagramNode.setWidth(10.0d);
    actualDiagramNode.setX(2.0d);
    actualDiagramNode.setY(3.0d);
    String actualToStringResult = actualDiagramNode.toString();
    Double actualHeight = actualDiagramNode.getHeight();
    Double actualWidth = actualDiagramNode.getWidth();
    Double actualX = actualDiagramNode.getX();
    Double actualY = actualDiagramNode.getY();
    boolean actualIsEdgeResult = actualDiagramNode.isEdge();
    boolean actualIsNodeResult = actualDiagramNode.isNode();

    // Assert that nothing has changed
    assertEquals("42", actualDiagramNode.getId());
    assertEquals("id=42, x=2.0, y=3.0, width=10.0, height=10.0", actualToStringResult);
    assertEquals(10.0d, actualHeight.doubleValue(), 0.0);
    assertEquals(10.0d, actualWidth.doubleValue(), 0.0);
    assertEquals(2.0d, actualX.doubleValue(), 0.0);
    assertEquals(3.0d, actualY.doubleValue(), 0.0);
    assertFalse(actualIsEdgeResult);
    assertTrue(actualIsNodeResult);
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When two.</li>
   *   <li>Then return Id is {@code 42}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link DiagramNode#DiagramNode(String, Double, Double, Double, Double)}
   *   <li>{@link DiagramNode#setHeight(Double)}
   *   <li>{@link DiagramNode#setWidth(Double)}
   *   <li>{@link DiagramNode#setX(Double)}
   *   <li>{@link DiagramNode#setY(Double)}
   *   <li>{@link DiagramNode#toString()}
   *   <li>{@link DiagramNode#getHeight()}
   *   <li>{@link DiagramNode#getWidth()}
   *   <li>{@link DiagramNode#getX()}
   *   <li>{@link DiagramNode#getY()}
   *   <li>{@link DiagramNode#isEdge()}
   *   <li>{@link DiagramNode#isNode()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters_whenTwo_thenReturnIdIs42() {
    // Arrange and Act
    DiagramNode actualDiagramNode = new DiagramNode("42", 2.0d, 3.0d, 10.0d, 10.0d);
    actualDiagramNode.setHeight(10.0d);
    actualDiagramNode.setWidth(10.0d);
    actualDiagramNode.setX(2.0d);
    actualDiagramNode.setY(3.0d);
    String actualToStringResult = actualDiagramNode.toString();
    Double actualHeight = actualDiagramNode.getHeight();
    Double actualWidth = actualDiagramNode.getWidth();
    Double actualX = actualDiagramNode.getX();
    Double actualY = actualDiagramNode.getY();
    boolean actualIsEdgeResult = actualDiagramNode.isEdge();
    boolean actualIsNodeResult = actualDiagramNode.isNode();

    // Assert that nothing has changed
    assertEquals("42", actualDiagramNode.getId());
    assertEquals("id=42, x=2.0, y=3.0, width=10.0, height=10.0", actualToStringResult);
    assertEquals(10.0d, actualHeight.doubleValue(), 0.0);
    assertEquals(10.0d, actualWidth.doubleValue(), 0.0);
    assertEquals(2.0d, actualX.doubleValue(), 0.0);
    assertEquals(3.0d, actualY.doubleValue(), 0.0);
    assertFalse(actualIsEdgeResult);
    assertTrue(actualIsNodeResult);
  }
}
