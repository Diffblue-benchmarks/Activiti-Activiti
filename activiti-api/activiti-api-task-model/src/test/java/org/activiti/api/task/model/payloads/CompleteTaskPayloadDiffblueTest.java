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
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class CompleteTaskPayloadDiffblueTest {
  /**
   * Test {@link CompleteTaskPayload#CompleteTaskPayload()}.
   * <p>
   * Method under test: {@link CompleteTaskPayload#CompleteTaskPayload()}
   */
  @Test
  @DisplayName("Test new CompleteTaskPayload()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void CompleteTaskPayload.<init>()"})
  void testNewCompleteTaskPayload() {
    // Arrange and Act
    CompleteTaskPayload actualCompleteTaskPayload = new CompleteTaskPayload();

    // Assert
    assertNull(actualCompleteTaskPayload.getTaskId());
    assertNull(actualCompleteTaskPayload.getVariables());
  }

  /**
   * Test {@link CompleteTaskPayload#CompleteTaskPayload(String, Map)}.
   * <p>
   * Method under test: {@link CompleteTaskPayload#CompleteTaskPayload(String, Map)}
   */
  @Test
  @DisplayName("Test new CompleteTaskPayload(String, Map)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void CompleteTaskPayload.<init>(String, Map)"})
  void testNewCompleteTaskPayload2() {
    // Arrange and Act
    CompleteTaskPayload actualCompleteTaskPayload = new CompleteTaskPayload("42", new HashMap<>());

    // Assert
    assertEquals("42", actualCompleteTaskPayload.getTaskId());
    assertTrue(actualCompleteTaskPayload.getVariables().isEmpty());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link CompleteTaskPayload#setTaskId(String)}
   *   <li>{@link CompleteTaskPayload#setVariables(Map)}
   *   <li>{@link CompleteTaskPayload#getId()}
   *   <li>{@link CompleteTaskPayload#getTaskId()}
   *   <li>{@link CompleteTaskPayload#getVariables()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String CompleteTaskPayload.getId()", "String CompleteTaskPayload.getTaskId()",
      "Map CompleteTaskPayload.getVariables()", "void CompleteTaskPayload.setTaskId(String)",
      "void CompleteTaskPayload.setVariables(Map)"})
  void testGettersAndSetters() {
    // Arrange
    CompleteTaskPayload completeTaskPayload = new CompleteTaskPayload();

    // Act
    completeTaskPayload.setTaskId("42");
    HashMap<String, Object> variables = new HashMap<>();
    completeTaskPayload.setVariables(variables);
    completeTaskPayload.getId();
    String actualTaskId = completeTaskPayload.getTaskId();
    Map<String, Object> actualVariables = completeTaskPayload.getVariables();

    // Assert
    assertEquals("42", actualTaskId);
    assertTrue(actualVariables.isEmpty());
    assertSame(variables, actualVariables);
  }
}
