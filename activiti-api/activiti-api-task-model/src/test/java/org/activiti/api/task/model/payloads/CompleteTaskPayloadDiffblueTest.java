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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CompleteTaskPayloadDiffblueTest {
  /**
   * Test {@link CompleteTaskPayload#CompleteTaskPayload()}.
   * <p>
   * Method under test: {@link CompleteTaskPayload#CompleteTaskPayload()}
   */
  @Test
  @DisplayName("Test new CompleteTaskPayload()")
  void testNewCompleteTaskPayload() {
    // Arrange and Act
    CompleteTaskPayload actualCompleteTaskPayload = new CompleteTaskPayload();

    // Assert
    assertNull(actualCompleteTaskPayload.getTaskId());
    assertNull(actualCompleteTaskPayload.getVariables());
  }

  /**
   * Test {@link CompleteTaskPayload#CompleteTaskPayload(String, Map)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CompleteTaskPayload#CompleteTaskPayload(String, Map)}
   */
  @Test
  @DisplayName("Test new CompleteTaskPayload(String, Map); given 'foo'; when HashMap() computeIfPresent 'foo' and BiFunction")
  void testNewCompleteTaskPayload_givenFoo_whenHashMapComputeIfPresentFooAndBiFunction() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("foo", mock(BiFunction.class));

    // Act
    CompleteTaskPayload actualCompleteTaskPayload = new CompleteTaskPayload("42", variables);

    // Assert
    assertEquals("42", actualCompleteTaskPayload.getTaskId());
    assertTrue(actualCompleteTaskPayload.getVariables().isEmpty());
  }

  /**
   * Test {@link CompleteTaskPayload#CompleteTaskPayload(String, Map)}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CompleteTaskPayload#CompleteTaskPayload(String, Map)}
   */
  @Test
  @DisplayName("Test new CompleteTaskPayload(String, Map); when HashMap()")
  void testNewCompleteTaskPayload_whenHashMap() {
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

    // Assert that nothing has changed
    assertEquals("42", actualTaskId);
    assertTrue(actualVariables.isEmpty());
    assertSame(variables, actualVariables);
  }
}
