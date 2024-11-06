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
package org.activiti.engine.impl.bpmn.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import org.junit.Test;

public class DataDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Data#Data(String, String, ItemDefinition)}
   *   <li>{@link Data#getDefinition()}
   *   <li>{@link Data#getId()}
   *   <li>{@link Data#getName()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    ItemDefinition definition = new ItemDefinition("42", new SimpleStructureDefinition("42"));

    // Act
    Data actualData = new Data("42", "Name", definition);
    ItemDefinition actualDefinition = actualData.getDefinition();
    String actualId = actualData.getId();

    // Assert
    assertEquals("42", actualId);
    assertEquals("Name", actualData.getName());
    assertSame(definition, actualDefinition);
  }
}
