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
package org.activiti.api.runtime.model.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VariableDefinitionImplDiffblueTest {
  /**
   * Test {@link VariableDefinitionImpl#equals(Object)}, and
   * {@link VariableDefinitionImpl#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link VariableDefinitionImpl#equals(Object)}
   *   <li>{@link VariableDefinitionImpl#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    VariableDefinitionImpl variableDefinitionImpl = new VariableDefinitionImpl();
    variableDefinitionImpl.setAnalytics(true);
    variableDefinitionImpl.setDescription("The characteristics of someone or something");
    variableDefinitionImpl.setDisplay(true);
    variableDefinitionImpl.setDisplayName("Display Name");
    variableDefinitionImpl.setId("42");
    variableDefinitionImpl.setName("Name");
    variableDefinitionImpl.setRequired(true);
    variableDefinitionImpl.setType("Type");

    VariableDefinitionImpl variableDefinitionImpl2 = new VariableDefinitionImpl();
    variableDefinitionImpl2.setAnalytics(true);
    variableDefinitionImpl2.setDescription("The characteristics of someone or something");
    variableDefinitionImpl2.setDisplay(true);
    variableDefinitionImpl2.setDisplayName("Display Name");
    variableDefinitionImpl2.setId("42");
    variableDefinitionImpl2.setName("Name");
    variableDefinitionImpl2.setRequired(true);
    variableDefinitionImpl2.setType("Type");

    // Act and Assert
    assertEquals(variableDefinitionImpl, variableDefinitionImpl2);
    int expectedHashCodeResult = variableDefinitionImpl.hashCode();
    assertEquals(expectedHashCodeResult, variableDefinitionImpl2.hashCode());
  }

  /**
   * Test {@link VariableDefinitionImpl#equals(Object)}, and
   * {@link VariableDefinitionImpl#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link VariableDefinitionImpl#equals(Object)}
   *   <li>{@link VariableDefinitionImpl#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    VariableDefinitionImpl variableDefinitionImpl = new VariableDefinitionImpl();
    variableDefinitionImpl.setAnalytics(true);
    variableDefinitionImpl.setDescription("The characteristics of someone or something");
    variableDefinitionImpl.setDisplay(true);
    variableDefinitionImpl.setDisplayName("Display Name");
    variableDefinitionImpl.setId("42");
    variableDefinitionImpl.setName("Name");
    variableDefinitionImpl.setRequired(true);
    variableDefinitionImpl.setType("Type");

    // Act and Assert
    assertEquals(variableDefinitionImpl, variableDefinitionImpl);
    int expectedHashCodeResult = variableDefinitionImpl.hashCode();
    assertEquals(expectedHashCodeResult, variableDefinitionImpl.hashCode());
  }

  /**
   * Test {@link VariableDefinitionImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableDefinitionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    VariableDefinitionImpl variableDefinitionImpl = new VariableDefinitionImpl();
    variableDefinitionImpl.setAnalytics(false);
    variableDefinitionImpl.setDescription("The characteristics of someone or something");
    variableDefinitionImpl.setDisplay(true);
    variableDefinitionImpl.setDisplayName("Display Name");
    variableDefinitionImpl.setId("42");
    variableDefinitionImpl.setName("Name");
    variableDefinitionImpl.setRequired(true);
    variableDefinitionImpl.setType("Type");

    VariableDefinitionImpl variableDefinitionImpl2 = new VariableDefinitionImpl();
    variableDefinitionImpl2.setAnalytics(true);
    variableDefinitionImpl2.setDescription("The characteristics of someone or something");
    variableDefinitionImpl2.setDisplay(true);
    variableDefinitionImpl2.setDisplayName("Display Name");
    variableDefinitionImpl2.setId("42");
    variableDefinitionImpl2.setName("Name");
    variableDefinitionImpl2.setRequired(true);
    variableDefinitionImpl2.setType("Type");

    // Act and Assert
    assertNotEquals(variableDefinitionImpl, variableDefinitionImpl2);
  }

  /**
   * Test {@link VariableDefinitionImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableDefinitionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    VariableDefinitionImpl variableDefinitionImpl = new VariableDefinitionImpl();
    variableDefinitionImpl.setAnalytics(true);
    variableDefinitionImpl.setDescription("Description");
    variableDefinitionImpl.setDisplay(true);
    variableDefinitionImpl.setDisplayName("Display Name");
    variableDefinitionImpl.setId("42");
    variableDefinitionImpl.setName("Name");
    variableDefinitionImpl.setRequired(true);
    variableDefinitionImpl.setType("Type");

    VariableDefinitionImpl variableDefinitionImpl2 = new VariableDefinitionImpl();
    variableDefinitionImpl2.setAnalytics(true);
    variableDefinitionImpl2.setDescription("The characteristics of someone or something");
    variableDefinitionImpl2.setDisplay(true);
    variableDefinitionImpl2.setDisplayName("Display Name");
    variableDefinitionImpl2.setId("42");
    variableDefinitionImpl2.setName("Name");
    variableDefinitionImpl2.setRequired(true);
    variableDefinitionImpl2.setType("Type");

    // Act and Assert
    assertNotEquals(variableDefinitionImpl, variableDefinitionImpl2);
  }

  /**
   * Test {@link VariableDefinitionImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableDefinitionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    VariableDefinitionImpl variableDefinitionImpl = new VariableDefinitionImpl();
    variableDefinitionImpl.setAnalytics(true);
    variableDefinitionImpl.setDescription("The characteristics of someone or something");
    variableDefinitionImpl.setDisplay(false);
    variableDefinitionImpl.setDisplayName("Display Name");
    variableDefinitionImpl.setId("42");
    variableDefinitionImpl.setName("Name");
    variableDefinitionImpl.setRequired(true);
    variableDefinitionImpl.setType("Type");

    VariableDefinitionImpl variableDefinitionImpl2 = new VariableDefinitionImpl();
    variableDefinitionImpl2.setAnalytics(true);
    variableDefinitionImpl2.setDescription("The characteristics of someone or something");
    variableDefinitionImpl2.setDisplay(true);
    variableDefinitionImpl2.setDisplayName("Display Name");
    variableDefinitionImpl2.setId("42");
    variableDefinitionImpl2.setName("Name");
    variableDefinitionImpl2.setRequired(true);
    variableDefinitionImpl2.setType("Type");

    // Act and Assert
    assertNotEquals(variableDefinitionImpl, variableDefinitionImpl2);
  }

  /**
   * Test {@link VariableDefinitionImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableDefinitionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    VariableDefinitionImpl variableDefinitionImpl = new VariableDefinitionImpl();
    variableDefinitionImpl.setAnalytics(true);
    variableDefinitionImpl.setDescription("The characteristics of someone or something");
    variableDefinitionImpl.setDisplay(true);
    variableDefinitionImpl.setDisplayName(null);
    variableDefinitionImpl.setId("42");
    variableDefinitionImpl.setName("Name");
    variableDefinitionImpl.setRequired(true);
    variableDefinitionImpl.setType("Type");

    VariableDefinitionImpl variableDefinitionImpl2 = new VariableDefinitionImpl();
    variableDefinitionImpl2.setAnalytics(true);
    variableDefinitionImpl2.setDescription("The characteristics of someone or something");
    variableDefinitionImpl2.setDisplay(true);
    variableDefinitionImpl2.setDisplayName("Display Name");
    variableDefinitionImpl2.setId("42");
    variableDefinitionImpl2.setName("Name");
    variableDefinitionImpl2.setRequired(true);
    variableDefinitionImpl2.setType("Type");

    // Act and Assert
    assertNotEquals(variableDefinitionImpl, variableDefinitionImpl2);
  }

  /**
   * Test {@link VariableDefinitionImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableDefinitionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    VariableDefinitionImpl variableDefinitionImpl = new VariableDefinitionImpl();
    variableDefinitionImpl.setAnalytics(true);
    variableDefinitionImpl.setDescription("The characteristics of someone or something");
    variableDefinitionImpl.setDisplay(true);
    variableDefinitionImpl.setDisplayName("Display Name");
    variableDefinitionImpl.setId("Id");
    variableDefinitionImpl.setName("Name");
    variableDefinitionImpl.setRequired(true);
    variableDefinitionImpl.setType("Type");

    VariableDefinitionImpl variableDefinitionImpl2 = new VariableDefinitionImpl();
    variableDefinitionImpl2.setAnalytics(true);
    variableDefinitionImpl2.setDescription("The characteristics of someone or something");
    variableDefinitionImpl2.setDisplay(true);
    variableDefinitionImpl2.setDisplayName("Display Name");
    variableDefinitionImpl2.setId("42");
    variableDefinitionImpl2.setName("Name");
    variableDefinitionImpl2.setRequired(true);
    variableDefinitionImpl2.setType("Type");

    // Act and Assert
    assertNotEquals(variableDefinitionImpl, variableDefinitionImpl2);
  }

  /**
   * Test {@link VariableDefinitionImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableDefinitionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    VariableDefinitionImpl variableDefinitionImpl = new VariableDefinitionImpl();
    variableDefinitionImpl.setAnalytics(true);
    variableDefinitionImpl.setDescription("The characteristics of someone or something");
    variableDefinitionImpl.setDisplay(true);
    variableDefinitionImpl.setDisplayName("Display Name");
    variableDefinitionImpl.setId("42");
    variableDefinitionImpl.setName(null);
    variableDefinitionImpl.setRequired(true);
    variableDefinitionImpl.setType("Type");

    VariableDefinitionImpl variableDefinitionImpl2 = new VariableDefinitionImpl();
    variableDefinitionImpl2.setAnalytics(true);
    variableDefinitionImpl2.setDescription("The characteristics of someone or something");
    variableDefinitionImpl2.setDisplay(true);
    variableDefinitionImpl2.setDisplayName("Display Name");
    variableDefinitionImpl2.setId("42");
    variableDefinitionImpl2.setName("Name");
    variableDefinitionImpl2.setRequired(true);
    variableDefinitionImpl2.setType("Type");

    // Act and Assert
    assertNotEquals(variableDefinitionImpl, variableDefinitionImpl2);
  }

  /**
   * Test {@link VariableDefinitionImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableDefinitionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    VariableDefinitionImpl variableDefinitionImpl = new VariableDefinitionImpl();
    variableDefinitionImpl.setAnalytics(true);
    variableDefinitionImpl.setDescription("The characteristics of someone or something");
    variableDefinitionImpl.setDisplay(true);
    variableDefinitionImpl.setDisplayName("Display Name");
    variableDefinitionImpl.setId("42");
    variableDefinitionImpl.setName("Name");
    variableDefinitionImpl.setRequired(false);
    variableDefinitionImpl.setType("Type");

    VariableDefinitionImpl variableDefinitionImpl2 = new VariableDefinitionImpl();
    variableDefinitionImpl2.setAnalytics(true);
    variableDefinitionImpl2.setDescription("The characteristics of someone or something");
    variableDefinitionImpl2.setDisplay(true);
    variableDefinitionImpl2.setDisplayName("Display Name");
    variableDefinitionImpl2.setId("42");
    variableDefinitionImpl2.setName("Name");
    variableDefinitionImpl2.setRequired(true);
    variableDefinitionImpl2.setType("Type");

    // Act and Assert
    assertNotEquals(variableDefinitionImpl, variableDefinitionImpl2);
  }

  /**
   * Test {@link VariableDefinitionImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableDefinitionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    VariableDefinitionImpl variableDefinitionImpl = new VariableDefinitionImpl();
    variableDefinitionImpl.setAnalytics(true);
    variableDefinitionImpl.setDescription("The characteristics of someone or something");
    variableDefinitionImpl.setDisplay(true);
    variableDefinitionImpl.setDisplayName("Display Name");
    variableDefinitionImpl.setId("42");
    variableDefinitionImpl.setName("Name");
    variableDefinitionImpl.setRequired(true);
    variableDefinitionImpl.setType(null);

    VariableDefinitionImpl variableDefinitionImpl2 = new VariableDefinitionImpl();
    variableDefinitionImpl2.setAnalytics(true);
    variableDefinitionImpl2.setDescription("The characteristics of someone or something");
    variableDefinitionImpl2.setDisplay(true);
    variableDefinitionImpl2.setDisplayName("Display Name");
    variableDefinitionImpl2.setId("42");
    variableDefinitionImpl2.setName("Name");
    variableDefinitionImpl2.setRequired(true);
    variableDefinitionImpl2.setType("Type");

    // Act and Assert
    assertNotEquals(variableDefinitionImpl, variableDefinitionImpl2);
  }

  /**
   * Test {@link VariableDefinitionImpl#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableDefinitionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    VariableDefinitionImpl variableDefinitionImpl = new VariableDefinitionImpl();
    variableDefinitionImpl.setAnalytics(true);
    variableDefinitionImpl.setDescription("The characteristics of someone or something");
    variableDefinitionImpl.setDisplay(true);
    variableDefinitionImpl.setDisplayName("Display Name");
    variableDefinitionImpl.setId("42");
    variableDefinitionImpl.setName("Name");
    variableDefinitionImpl.setRequired(true);
    variableDefinitionImpl.setType("Type");

    // Act and Assert
    assertNotEquals(variableDefinitionImpl, null);
  }

  /**
   * Test {@link VariableDefinitionImpl#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableDefinitionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    VariableDefinitionImpl variableDefinitionImpl = new VariableDefinitionImpl();
    variableDefinitionImpl.setAnalytics(true);
    variableDefinitionImpl.setDescription("The characteristics of someone or something");
    variableDefinitionImpl.setDisplay(true);
    variableDefinitionImpl.setDisplayName("Display Name");
    variableDefinitionImpl.setId("42");
    variableDefinitionImpl.setName("Name");
    variableDefinitionImpl.setRequired(true);
    variableDefinitionImpl.setType("Type");

    // Act and Assert
    assertNotEquals(variableDefinitionImpl, "Different type to VariableDefinitionImpl");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link VariableDefinitionImpl}
   *   <li>{@link VariableDefinitionImpl#setAnalytics(boolean)}
   *   <li>{@link VariableDefinitionImpl#setDescription(String)}
   *   <li>{@link VariableDefinitionImpl#setDisplay(Boolean)}
   *   <li>{@link VariableDefinitionImpl#setDisplayName(String)}
   *   <li>{@link VariableDefinitionImpl#setId(String)}
   *   <li>{@link VariableDefinitionImpl#setName(String)}
   *   <li>{@link VariableDefinitionImpl#setRequired(boolean)}
   *   <li>{@link VariableDefinitionImpl#setType(String)}
   *   <li>{@link VariableDefinitionImpl#toString()}
   *   <li>{@link VariableDefinitionImpl#getDescription()}
   *   <li>{@link VariableDefinitionImpl#getDisplay()}
   *   <li>{@link VariableDefinitionImpl#getDisplayName()}
   *   <li>{@link VariableDefinitionImpl#getId()}
   *   <li>{@link VariableDefinitionImpl#getName()}
   *   <li>{@link VariableDefinitionImpl#getType()}
   *   <li>{@link VariableDefinitionImpl#isAnalytics()}
   *   <li>{@link VariableDefinitionImpl#isRequired()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    VariableDefinitionImpl actualVariableDefinitionImpl = new VariableDefinitionImpl();
    actualVariableDefinitionImpl.setAnalytics(true);
    actualVariableDefinitionImpl.setDescription("The characteristics of someone or something");
    actualVariableDefinitionImpl.setDisplay(true);
    actualVariableDefinitionImpl.setDisplayName("Display Name");
    actualVariableDefinitionImpl.setId("42");
    actualVariableDefinitionImpl.setName("Name");
    actualVariableDefinitionImpl.setRequired(true);
    actualVariableDefinitionImpl.setType("Type");
    String actualToStringResult = actualVariableDefinitionImpl.toString();
    String actualDescription = actualVariableDefinitionImpl.getDescription();
    Boolean actualDisplay = actualVariableDefinitionImpl.getDisplay();
    String actualDisplayName = actualVariableDefinitionImpl.getDisplayName();
    String actualId = actualVariableDefinitionImpl.getId();
    String actualName = actualVariableDefinitionImpl.getName();
    String actualType = actualVariableDefinitionImpl.getType();
    boolean actualIsAnalyticsResult = actualVariableDefinitionImpl.isAnalytics();

    // Assert that nothing has changed
    assertEquals("42", actualId);
    assertEquals("Display Name", actualDisplayName);
    assertEquals("Name", actualName);
    assertEquals("The characteristics of someone or something", actualDescription);
    assertEquals("Type", actualType);
    assertEquals(
        "VariableDefinitionImpl{id='42', name='Name', description='The characteristics of someone or something',"
            + " type='Type', required=true, display=true, displayName='Display Name', analytics='true'}",
        actualToStringResult);
    assertTrue(actualDisplay);
    assertTrue(actualIsAnalyticsResult);
    assertTrue(actualVariableDefinitionImpl.isRequired());
  }
}
