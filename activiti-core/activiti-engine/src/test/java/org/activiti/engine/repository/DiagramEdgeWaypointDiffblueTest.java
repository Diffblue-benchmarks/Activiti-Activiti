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
import org.junit.Test;

public class DiagramEdgeWaypointDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link DiagramEdgeWaypoint}
   *   <li>{@link DiagramEdgeWaypoint#setX(Double)}
   *   <li>{@link DiagramEdgeWaypoint#setY(Double)}
   *   <li>{@link DiagramEdgeWaypoint#getX()}
   *   <li>{@link DiagramEdgeWaypoint#getY()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    DiagramEdgeWaypoint actualDiagramEdgeWaypoint = new DiagramEdgeWaypoint();
    actualDiagramEdgeWaypoint.setX(2.0d);
    actualDiagramEdgeWaypoint.setY(3.0d);
    Double actualX = actualDiagramEdgeWaypoint.getX();
    Double actualY = actualDiagramEdgeWaypoint.getY();

    // Assert that nothing has changed
    assertEquals(2.0d, actualX.doubleValue(), 0.0);
    assertEquals(3.0d, actualY.doubleValue(), 0.0);
  }
}
