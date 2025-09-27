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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class DiagramEdgeWaypointDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link DiagramEdgeWaypoint}
   *   <li>{@link DiagramEdgeWaypoint#setX(Double)}
   *   <li>{@link DiagramEdgeWaypoint#setY(Double)}
   *   <li>{@link DiagramEdgeWaypoint#getX()}
   *   <li>{@link DiagramEdgeWaypoint#getY()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DiagramEdgeWaypoint.<init>()",
    "Double DiagramEdgeWaypoint.getX()",
    "Double DiagramEdgeWaypoint.getY()",
    "void DiagramEdgeWaypoint.setX(Double)",
    "void DiagramEdgeWaypoint.setY(Double)"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    DiagramEdgeWaypoint actualDiagramEdgeWaypoint = new DiagramEdgeWaypoint();
    actualDiagramEdgeWaypoint.setX(2.0d);
    actualDiagramEdgeWaypoint.setY(3.0d);
    Double actualX = actualDiagramEdgeWaypoint.getX();
    Double actualY = actualDiagramEdgeWaypoint.getY();

    // Assert
    assertEquals(2.0d, actualX.doubleValue(), 0.0);
    assertEquals(3.0d, actualY.doubleValue(), 0.0);
  }
}
