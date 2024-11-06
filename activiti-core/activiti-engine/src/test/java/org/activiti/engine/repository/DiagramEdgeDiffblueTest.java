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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class DiagramEdgeDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link DiagramEdge#DiagramEdge()}
   *   <li>{@link DiagramEdge#setWaypoints(List)}
   *   <li>{@link DiagramEdge#getWaypoints()}
   *   <li>{@link DiagramEdge#isEdge()}
   *   <li>{@link DiagramEdge#isNode()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    DiagramEdge actualDiagramEdge = new DiagramEdge();
    ArrayList<DiagramEdgeWaypoint> waypoints = new ArrayList<>();
    actualDiagramEdge.setWaypoints(waypoints);
    List<DiagramEdgeWaypoint> actualWaypoints = actualDiagramEdge.getWaypoints();
    boolean actualIsEdgeResult = actualDiagramEdge.isEdge();

    // Assert that nothing has changed
    assertFalse(actualDiagramEdge.isNode());
    assertTrue(actualWaypoints.isEmpty());
    assertTrue(actualIsEdgeResult);
    assertSame(waypoints, actualWaypoints);
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
   *   <li>{@link DiagramEdge#DiagramEdge(String, List)}
   *   <li>{@link DiagramEdge#setWaypoints(List)}
   *   <li>{@link DiagramEdge#getWaypoints()}
   *   <li>{@link DiagramEdge#isEdge()}
   *   <li>{@link DiagramEdge#isNode()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters_when42_thenReturnIdIs42() {
    // Arrange and Act
    DiagramEdge actualDiagramEdge = new DiagramEdge("42", new ArrayList<>());
    ArrayList<DiagramEdgeWaypoint> waypoints = new ArrayList<>();
    actualDiagramEdge.setWaypoints(waypoints);
    List<DiagramEdgeWaypoint> actualWaypoints = actualDiagramEdge.getWaypoints();
    boolean actualIsEdgeResult = actualDiagramEdge.isEdge();
    boolean actualIsNodeResult = actualDiagramEdge.isNode();

    // Assert that nothing has changed
    assertEquals("42", actualDiagramEdge.getId());
    assertFalse(actualIsNodeResult);
    assertTrue(actualWaypoints.isEmpty());
    assertTrue(actualIsEdgeResult);
    assertSame(waypoints, actualWaypoints);
  }
}
