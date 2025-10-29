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
import org.junit.jupiter.api.Test;

class GetProcessDefinitionsPayloadDiffblueTest {
  /**
   * Method under test: {@link GetProcessDefinitionsPayload#hasDefinitionKeys()}
   */
  @Test
  void testHasDefinitionKeys() {
    // Arrange, Act and Assert
    assertFalse((new GetProcessDefinitionsPayload()).hasDefinitionKeys());
  }

  /**
   * Method under test: {@link GetProcessDefinitionsPayload#hasDefinitionKeys()}
   */
  @Test
  void testHasDefinitionKeys2() {
    // Arrange
    GetProcessDefinitionsPayload getProcessDefinitionsPayload = new GetProcessDefinitionsPayload();
    getProcessDefinitionsPayload.setProcessDefinitionKeys(new HashSet<>());

    // Act and Assert
    assertFalse(getProcessDefinitionsPayload.hasDefinitionKeys());
  }

  /**
   * Method under test: {@link GetProcessDefinitionsPayload#hasDefinitionKeys()}
   */
  @Test
  void testHasDefinitionKeys3() {
    // Arrange
    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("foo");

    GetProcessDefinitionsPayload getProcessDefinitionsPayload = new GetProcessDefinitionsPayload();
    getProcessDefinitionsPayload.setProcessDefinitionKeys(processDefinitionKeys);

    // Act and Assert
    assertTrue(getProcessDefinitionsPayload.hasDefinitionKeys());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetProcessDefinitionsPayload#setProcessDefinitionKeys(Set)}
   *   <li>{@link GetProcessDefinitionsPayload#getId()}
   *   <li>{@link GetProcessDefinitionsPayload#getProcessDefinitionId()}
   *   <li>{@link GetProcessDefinitionsPayload#getProcessDefinitionKeys()}
   * </ul>
   */
  @Test
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
   * Method under test:
   * {@link GetProcessDefinitionsPayload#GetProcessDefinitionsPayload()}
   */
  @Test
  void testNewGetProcessDefinitionsPayload() {
    // Arrange and Act
    GetProcessDefinitionsPayload actualGetProcessDefinitionsPayload = new GetProcessDefinitionsPayload();

    // Assert
    assertNull(actualGetProcessDefinitionsPayload.getProcessDefinitionId());
    assertNull(actualGetProcessDefinitionsPayload.getProcessDefinitionKeys());
    assertFalse(actualGetProcessDefinitionsPayload.hasDefinitionKeys());
  }

  /**
   * Method under test:
   * {@link GetProcessDefinitionsPayload#GetProcessDefinitionsPayload(String, Set)}
   */
  @Test
  void testNewGetProcessDefinitionsPayload2() {
    // Arrange
    HashSet<String> processDefinitionKeys = new HashSet<>();

    // Act
    GetProcessDefinitionsPayload actualGetProcessDefinitionsPayload = new GetProcessDefinitionsPayload("42",
        processDefinitionKeys);

    // Assert
    assertEquals("42", actualGetProcessDefinitionsPayload.getProcessDefinitionId());
    assertFalse(actualGetProcessDefinitionsPayload.hasDefinitionKeys());
    Set<String> processDefinitionKeys2 = actualGetProcessDefinitionsPayload.getProcessDefinitionKeys();
    assertTrue(processDefinitionKeys2.isEmpty());
    assertSame(processDefinitionKeys, processDefinitionKeys2);
  }

  /**
   * Method under test:
   * {@link GetProcessDefinitionsPayload#GetProcessDefinitionsPayload(String, Set)}
   */
  @Test
  void testNewGetProcessDefinitionsPayload3() {
    // Arrange
    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("foo");

    // Act
    GetProcessDefinitionsPayload actualGetProcessDefinitionsPayload = new GetProcessDefinitionsPayload("42",
        processDefinitionKeys);

    // Assert
    assertEquals("42", actualGetProcessDefinitionsPayload.getProcessDefinitionId());
    Set<String> processDefinitionKeys2 = actualGetProcessDefinitionsPayload.getProcessDefinitionKeys();
    assertEquals(1, processDefinitionKeys2.size());
    assertTrue(processDefinitionKeys2.contains("foo"));
    assertTrue(actualGetProcessDefinitionsPayload.hasDefinitionKeys());
    assertSame(processDefinitionKeys, processDefinitionKeys2);
  }

  /**
   * Method under test:
   * {@link GetProcessDefinitionsPayload#GetProcessDefinitionsPayload(String, Set)}
   */
  @Test
  void testNewGetProcessDefinitionsPayload4() {
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
}
