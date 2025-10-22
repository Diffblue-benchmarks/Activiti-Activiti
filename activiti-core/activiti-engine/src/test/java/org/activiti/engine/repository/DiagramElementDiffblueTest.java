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
import static org.junit.Assert.assertNull;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class DiagramElementDiffblueTest {
  /**
   * Test {@link DiagramElement#getId()}.
   * <p>
   * Method under test: {@link DiagramElement#getId()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String DiagramElement.getId()"})
  public void testGetId() {
    // Arrange, Act and Assert
    assertNull((new DiagramEdge()).getId());
  }

  /**
   * Test {@link DiagramElement#setId(String)}.
   * <p>
   * Method under test: {@link DiagramElement#setId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DiagramElement.setId(String)"})
  public void testSetId() {
    // Arrange
    DiagramEdge diagramEdge = new DiagramEdge();

    // Act
    diagramEdge.setId("42");

    // Assert
    assertEquals("42", diagramEdge.getId());
  }

  /**
   * Test {@link DiagramElement#toString()}.
   * <p>
   * Method under test: {@link DiagramElement#toString()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String DiagramElement.toString()"})
  public void testToString() {
    // Arrange, Act and Assert
    assertEquals("id=null", (new DiagramEdge()).toString());
  }
}
