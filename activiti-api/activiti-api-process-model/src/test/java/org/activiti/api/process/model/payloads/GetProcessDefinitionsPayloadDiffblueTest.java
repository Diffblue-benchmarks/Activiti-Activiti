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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GetProcessDefinitionsPayloadDiffblueTest {
  /**
   * Test {@link GetProcessDefinitionsPayload#GetProcessDefinitionsPayload()}.
   * <p>
   * Method under test:
   * {@link GetProcessDefinitionsPayload#GetProcessDefinitionsPayload()}
   */
  @Test
  @DisplayName("Test new GetProcessDefinitionsPayload()")
  void testNewGetProcessDefinitionsPayload() {
    // Arrange and Act
    GetProcessDefinitionsPayload actualGetProcessDefinitionsPayload = new GetProcessDefinitionsPayload();

    // Assert
    assertNull(actualGetProcessDefinitionsPayload.getProcessDefinitionId());
    assertNull(actualGetProcessDefinitionsPayload.getProcessDefinitionKeys());
    assertFalse(actualGetProcessDefinitionsPayload.hasDefinitionKeys());
  }

  /**
   * Test
   * {@link GetProcessDefinitionsPayload#GetProcessDefinitionsPayload(String, Set)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link HashSet#HashSet()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link GetProcessDefinitionsPayload#GetProcessDefinitionsPayload(String, Set)}
   */
  @Test
  @DisplayName("Test new GetProcessDefinitionsPayload(String, Set); given '42'; when HashSet() add '42'")
  void testNewGetProcessDefinitionsPayload_given42_whenHashSetAdd42() {
    // Arrange
    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("42");
    processDefinitionKeys.add("foo");

    // Act
    GetProcessDefinitionsPayload actualGetProcessDefinitionsPayload = new GetProcessDefinitionsPayload("42",
        processDefinitionKeys);

    // Assert
    assertEquals("42", actualGetProcessDefinitionsPayload.getProcessDefinitionId());
    assertTrue(actualGetProcessDefinitionsPayload.hasDefinitionKeys());
    assertSame(processDefinitionKeys, actualGetProcessDefinitionsPayload.getProcessDefinitionKeys());
  }

  /**
   * Test
   * {@link GetProcessDefinitionsPayload#GetProcessDefinitionsPayload(String, Set)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>Then return hasDefinitionKeys.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link GetProcessDefinitionsPayload#GetProcessDefinitionsPayload(String, Set)}
   */
  @Test
  @DisplayName("Test new GetProcessDefinitionsPayload(String, Set); given 'foo'; then return hasDefinitionKeys")
  void testNewGetProcessDefinitionsPayload_givenFoo_thenReturnHasDefinitionKeys() {
    // Arrange
    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("foo");

    // Act
    GetProcessDefinitionsPayload actualGetProcessDefinitionsPayload = new GetProcessDefinitionsPayload("42",
        processDefinitionKeys);

    // Assert
    assertEquals("42", actualGetProcessDefinitionsPayload.getProcessDefinitionId());
    assertTrue(actualGetProcessDefinitionsPayload.hasDefinitionKeys());
    assertSame(processDefinitionKeys, actualGetProcessDefinitionsPayload.getProcessDefinitionKeys());
  }

  /**
   * Test
   * {@link GetProcessDefinitionsPayload#GetProcessDefinitionsPayload(String, Set)}.
   * <ul>
   *   <li>When {@link HashSet#HashSet()}.</li>
   *   <li>Then return not hasDefinitionKeys.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link GetProcessDefinitionsPayload#GetProcessDefinitionsPayload(String, Set)}
   */
  @Test
  @DisplayName("Test new GetProcessDefinitionsPayload(String, Set); when HashSet(); then return not hasDefinitionKeys")
  void testNewGetProcessDefinitionsPayload_whenHashSet_thenReturnNotHasDefinitionKeys() {
    // Arrange and Act
    GetProcessDefinitionsPayload actualGetProcessDefinitionsPayload = new GetProcessDefinitionsPayload("42",
        new HashSet<>());

    // Assert
    assertEquals("42", actualGetProcessDefinitionsPayload.getProcessDefinitionId());
    assertFalse(actualGetProcessDefinitionsPayload.hasDefinitionKeys());
    assertTrue(actualGetProcessDefinitionsPayload.getProcessDefinitionKeys().isEmpty());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetProcessDefinitionsPayload#setProcessDefinitionKeys(Set)}
   *   <li>{@link GetProcessDefinitionsPayload#getId()}
   *   <li>{@link GetProcessDefinitionsPayload#getProcessDefinitionId()}
   *   <li>{@link GetProcessDefinitionsPayload#getProcessDefinitionKeys()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    GetProcessDefinitionsPayload getProcessDefinitionsPayload = new GetProcessDefinitionsPayload();
    HashSet<String> processDefinitionKeys = new HashSet<>();

    // Act
    getProcessDefinitionsPayload.setProcessDefinitionKeys(processDefinitionKeys);
    getProcessDefinitionsPayload.getId();
    getProcessDefinitionsPayload.getProcessDefinitionId();
    Set<String> actualProcessDefinitionKeys = getProcessDefinitionsPayload.getProcessDefinitionKeys();

    // Assert that nothing has changed
    assertTrue(actualProcessDefinitionKeys.isEmpty());
    assertSame(processDefinitionKeys, actualProcessDefinitionKeys);
  }

  /**
   * Test {@link GetProcessDefinitionsPayload#hasDefinitionKeys()}.
   * <ul>
   *   <li>Given
   * {@link GetProcessDefinitionsPayload#GetProcessDefinitionsPayload()}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetProcessDefinitionsPayload#hasDefinitionKeys()}
   */
  @Test
  @DisplayName("Test hasDefinitionKeys(); given GetProcessDefinitionsPayload(); then return 'false'")
  void testHasDefinitionKeys_givenGetProcessDefinitionsPayload_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new GetProcessDefinitionsPayload()).hasDefinitionKeys());
  }

  /**
   * Test {@link GetProcessDefinitionsPayload#hasDefinitionKeys()}.
   * <ul>
   *   <li>Given {@link HashSet#HashSet()} add {@code foo}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetProcessDefinitionsPayload#hasDefinitionKeys()}
   */
  @Test
  @DisplayName("Test hasDefinitionKeys(); given HashSet() add 'foo'; then return 'true'")
  void testHasDefinitionKeys_givenHashSetAddFoo_thenReturnTrue() {
    // Arrange
    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("foo");

    GetProcessDefinitionsPayload getProcessDefinitionsPayload = new GetProcessDefinitionsPayload();
    getProcessDefinitionsPayload.setProcessDefinitionKeys(processDefinitionKeys);

    // Act and Assert
    assertTrue(getProcessDefinitionsPayload.hasDefinitionKeys());
  }

  /**
   * Test {@link GetProcessDefinitionsPayload#hasDefinitionKeys()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetProcessDefinitionsPayload#hasDefinitionKeys()}
   */
  @Test
  @DisplayName("Test hasDefinitionKeys(); then return 'false'")
  void testHasDefinitionKeys_thenReturnFalse() {
    // Arrange
    GetProcessDefinitionsPayload getProcessDefinitionsPayload = new GetProcessDefinitionsPayload();
    getProcessDefinitionsPayload.setProcessDefinitionKeys(new HashSet<>());

    // Act and Assert
    assertFalse(getProcessDefinitionsPayload.hasDefinitionKeys());
  }
}
