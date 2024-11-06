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
package org.activiti.api.process.model.payloads;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RemoveProcessVariablesPayloadDiffblueTest {
  /**
   * Test {@link RemoveProcessVariablesPayload#RemoveProcessVariablesPayload()}.
   * <p>
   * Method under test:
   * {@link RemoveProcessVariablesPayload#RemoveProcessVariablesPayload()}
   */
  @Test
  @DisplayName("Test new RemoveProcessVariablesPayload()")
  void testNewRemoveProcessVariablesPayload() {
    // Arrange and Act
    RemoveProcessVariablesPayload actualRemoveProcessVariablesPayload = new RemoveProcessVariablesPayload();

    // Assert
    assertNull(actualRemoveProcessVariablesPayload.getProcessInstanceId());
    assertTrue(actualRemoveProcessVariablesPayload.getVariableNames().isEmpty());
  }

  /**
   * Test
   * {@link RemoveProcessVariablesPayload#RemoveProcessVariablesPayload(String, List)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RemoveProcessVariablesPayload#RemoveProcessVariablesPayload(String, List)}
   */
  @Test
  @DisplayName("Test new RemoveProcessVariablesPayload(String, List); given '42'; when ArrayList() add '42'")
  void testNewRemoveProcessVariablesPayload_given42_whenArrayListAdd42() {
    // Arrange
    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("42");
    variableNames.add("foo");

    // Act
    RemoveProcessVariablesPayload actualRemoveProcessVariablesPayload = new RemoveProcessVariablesPayload("42",
        variableNames);

    // Assert
    assertEquals("42", actualRemoveProcessVariablesPayload.getProcessInstanceId());
    assertSame(variableNames, actualRemoveProcessVariablesPayload.getVariableNames());
  }

  /**
   * Test
   * {@link RemoveProcessVariablesPayload#RemoveProcessVariablesPayload(String, List)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>Then return VariableNames is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RemoveProcessVariablesPayload#RemoveProcessVariablesPayload(String, List)}
   */
  @Test
  @DisplayName("Test new RemoveProcessVariablesPayload(String, List); given 'foo'; then return VariableNames is ArrayList()")
  void testNewRemoveProcessVariablesPayload_givenFoo_thenReturnVariableNamesIsArrayList() {
    // Arrange
    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("foo");

    // Act
    RemoveProcessVariablesPayload actualRemoveProcessVariablesPayload = new RemoveProcessVariablesPayload("42",
        variableNames);

    // Assert
    assertEquals("42", actualRemoveProcessVariablesPayload.getProcessInstanceId());
    assertSame(variableNames, actualRemoveProcessVariablesPayload.getVariableNames());
  }

  /**
   * Test
   * {@link RemoveProcessVariablesPayload#RemoveProcessVariablesPayload(String, List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return VariableNames Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RemoveProcessVariablesPayload#RemoveProcessVariablesPayload(String, List)}
   */
  @Test
  @DisplayName("Test new RemoveProcessVariablesPayload(String, List); when ArrayList(); then return VariableNames Empty")
  void testNewRemoveProcessVariablesPayload_whenArrayList_thenReturnVariableNamesEmpty() {
    // Arrange and Act
    RemoveProcessVariablesPayload actualRemoveProcessVariablesPayload = new RemoveProcessVariablesPayload("42",
        new ArrayList<>());

    // Assert
    assertEquals("42", actualRemoveProcessVariablesPayload.getProcessInstanceId());
    assertTrue(actualRemoveProcessVariablesPayload.getVariableNames().isEmpty());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link RemoveProcessVariablesPayload#setProcessInstanceId(String)}
   *   <li>{@link RemoveProcessVariablesPayload#setVariableNames(List)}
   *   <li>{@link RemoveProcessVariablesPayload#getId()}
   *   <li>{@link RemoveProcessVariablesPayload#getProcessInstanceId()}
   *   <li>{@link RemoveProcessVariablesPayload#getVariableNames()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    RemoveProcessVariablesPayload removeProcessVariablesPayload = new RemoveProcessVariablesPayload();

    // Act
    removeProcessVariablesPayload.setProcessInstanceId("42");
    ArrayList<String> variableNames = new ArrayList<>();
    removeProcessVariablesPayload.setVariableNames(variableNames);
    removeProcessVariablesPayload.getId();
    String actualProcessInstanceId = removeProcessVariablesPayload.getProcessInstanceId();
    List<String> actualVariableNames = removeProcessVariablesPayload.getVariableNames();

    // Assert that nothing has changed
    assertEquals("42", actualProcessInstanceId);
    assertTrue(actualVariableNames.isEmpty());
    assertSame(variableNames, actualVariableNames);
  }
}
