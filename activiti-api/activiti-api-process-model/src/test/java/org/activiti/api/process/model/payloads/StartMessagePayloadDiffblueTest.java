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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.jupiter.api.Test;

class StartMessagePayloadDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link StartMessagePayload#equals(Object)}
   *   <li>{@link StartMessagePayload#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    StartMessagePayload startMessagePayload = new StartMessagePayload();

    // Act and Assert
    assertEquals(startMessagePayload, startMessagePayload);
    int expectedHashCodeResult = startMessagePayload.hashCode();
    assertEquals(expectedHashCodeResult, startMessagePayload.hashCode());
  }

  /**
   * Method under test: {@link StartMessagePayload#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    StartMessagePayload startMessagePayload = new StartMessagePayload();

    // Act and Assert
    assertNotEquals(startMessagePayload, new StartMessagePayload());
  }

  /**
   * Method under test: {@link StartMessagePayload#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    StartMessagePayload startMessagePayload = new StartMessagePayload("Name", "Business Key", new HashMap<>());

    // Act and Assert
    assertNotEquals(startMessagePayload, new StartMessagePayload());
  }

  /**
   * Method under test: {@link StartMessagePayload#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("foo", mock(BiFunction.class));
    StartMessagePayload startMessagePayload = new StartMessagePayload("Name", "Business Key", variables);

    // Act and Assert
    assertNotEquals(startMessagePayload, new StartMessagePayload());
  }

  /**
   * Method under test: {@link StartMessagePayload#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new StartMessagePayload(), null);
  }

  /**
   * Method under test: {@link StartMessagePayload#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new StartMessagePayload(), "Different type to StartMessagePayload");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link StartMessagePayload#toString()}
   *   <li>{@link StartMessagePayload#getBusinessKey()}
   *   <li>{@link StartMessagePayload#getId()}
   *   <li>{@link StartMessagePayload#getName()}
   *   <li>{@link StartMessagePayload#getVariables()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    StartMessagePayload startMessagePayload = new StartMessagePayload();

    // Act
    startMessagePayload.toString();
    String actualBusinessKey = startMessagePayload.getBusinessKey();
    startMessagePayload.getId();
    String actualName = startMessagePayload.getName();

    // Assert
    assertNull(actualBusinessKey);
    assertNull(actualName);
    assertTrue(startMessagePayload.getVariables().isEmpty());
  }

  /**
   * Method under test: {@link StartMessagePayload#StartMessagePayload()}
   */
  @Test
  void testNewStartMessagePayload() {
    // Arrange and Act
    StartMessagePayload actualStartMessagePayload = new StartMessagePayload();

    // Assert
    assertNull(actualStartMessagePayload.getBusinessKey());
    assertNull(actualStartMessagePayload.getName());
    assertTrue(actualStartMessagePayload.getVariables().isEmpty());
  }

  /**
   * Method under test:
   * {@link StartMessagePayload#StartMessagePayload(String, String, Map)}
   */
  @Test
  void testNewStartMessagePayload2() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();

    // Act
    StartMessagePayload actualStartMessagePayload = new StartMessagePayload("Name", "Business Key", variables);

    // Assert
    assertEquals("Business Key", actualStartMessagePayload.getBusinessKey());
    assertEquals("Name", actualStartMessagePayload.getName());
    Map<String, Object> variables2 = actualStartMessagePayload.getVariables();
    assertTrue(variables2.isEmpty());
    assertSame(variables, variables2);
  }

  /**
   * Method under test:
   * {@link StartMessagePayload#StartMessagePayload(String, String, Map)}
   */
  @Test
  void testNewStartMessagePayload3() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("name must not be null", mock(BiFunction.class));

    // Act
    StartMessagePayload actualStartMessagePayload = new StartMessagePayload("Name", "Business Key", variables);

    // Assert
    assertEquals("Business Key", actualStartMessagePayload.getBusinessKey());
    assertEquals("Name", actualStartMessagePayload.getName());
    Map<String, Object> variables2 = actualStartMessagePayload.getVariables();
    assertTrue(variables2.isEmpty());
    assertSame(variables, variables2);
  }
}
