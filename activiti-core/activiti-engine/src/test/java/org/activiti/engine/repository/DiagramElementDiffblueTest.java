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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DiagramElementDiffblueTest {
  @InjectMocks
  private DiagramEdge diagramEdge;

  /**
   * Test {@link DiagramElement#getId()}.
   * <p>
   * Method under test: {@link DiagramElement#getId()}
   */
  @Test
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
  public void testSetId() {
    // Arrange and Act
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
  public void testToString() {
    // Arrange, Act and Assert
    assertEquals("id=null", (new DiagramEdge()).toString());
  }
}
