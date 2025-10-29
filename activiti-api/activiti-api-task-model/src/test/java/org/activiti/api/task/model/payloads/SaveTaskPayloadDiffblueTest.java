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
package org.activiti.api.task.model.payloads;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.jupiter.api.Test;

class SaveTaskPayloadDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SaveTaskPayload#setTaskId(String)}
   *   <li>{@link SaveTaskPayload#setVariables(Map)}
   *   <li>{@link SaveTaskPayload#getId()}
   *   <li>{@link SaveTaskPayload#getTaskId()}
   *   <li>{@link SaveTaskPayload#getVariables()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    SaveTaskPayload saveTaskPayload = new SaveTaskPayload();

    // Act
    saveTaskPayload.setTaskId("42");
    HashMap<String, Object> variables = new HashMap<>();
    saveTaskPayload.setVariables(variables);
    saveTaskPayload.getId();
    String actualTaskId = saveTaskPayload.getTaskId();
    Map<String, Object> actualVariables = saveTaskPayload.getVariables();

    // Assert that nothing has changed
    assertEquals("42", actualTaskId);
    assertTrue(actualVariables.isEmpty());
    assertSame(variables, actualVariables);
  }

  /**
   * Method under test: {@link SaveTaskPayload#SaveTaskPayload()}
   */
  @Test
  void testNewSaveTaskPayload() {
    // Arrange and Act
    SaveTaskPayload actualSaveTaskPayload = new SaveTaskPayload();

    // Assert
    assertNull(actualSaveTaskPayload.getTaskId());
    assertNull(actualSaveTaskPayload.getVariables());
  }

  /**
   * Method under test: {@link SaveTaskPayload#SaveTaskPayload(String, Map)}
   */
  @Test
  void testNewSaveTaskPayload2() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();

    // Act
    SaveTaskPayload actualSaveTaskPayload = new SaveTaskPayload("42", variables);

    // Assert
    assertEquals("42", actualSaveTaskPayload.getTaskId());
    Map<String, Object> variables2 = actualSaveTaskPayload.getVariables();
    assertTrue(variables2.isEmpty());
    assertSame(variables, variables2);
  }

  /**
   * Method under test: {@link SaveTaskPayload#SaveTaskPayload(String, Map)}
   */
  @Test
  void testNewSaveTaskPayload3() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("foo", mock(BiFunction.class));

    // Act
    SaveTaskPayload actualSaveTaskPayload = new SaveTaskPayload("42", variables);

    // Assert
    assertEquals("42", actualSaveTaskPayload.getTaskId());
    Map<String, Object> variables2 = actualSaveTaskPayload.getVariables();
    assertTrue(variables2.isEmpty());
    assertSame(variables, variables2);
  }
}
