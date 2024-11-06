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
package org.activiti.runtime.api.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {VariableNameValidator.class})
@ExtendWith(SpringExtension.class)
class VariableNameValidatorDiffblueTest {
  @Autowired
  private VariableNameValidator variableNameValidator;

  /**
   * Test {@link VariableNameValidator#validate(String)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableNameValidator#validate(String)}
   */
  @Test
  @DisplayName("Test validate(String); when empty string; then return 'false'")
  void testValidate_whenEmptyString_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(variableNameValidator.validate(""));
  }

  /**
   * Test {@link VariableNameValidator#validate(String)}.
   * <ul>
   *   <li>When {@code (?i)[a-z][a-z0-9_]*}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableNameValidator#validate(String)}
   */
  @Test
  @DisplayName("Test validate(String); when '(?i)[a-z][a-z0-9_]*'; then return 'false'")
  void testValidate_whenIAZAZ09_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(variableNameValidator.validate("(?i)[a-z][a-z0-9_]*"));
  }

  /**
   * Test {@link VariableNameValidator#validate(String)}.
   * <ul>
   *   <li>When {@code Name}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableNameValidator#validate(String)}
   */
  @Test
  @DisplayName("Test validate(String); when 'Name'; then return 'true'")
  void testValidate_whenName_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(variableNameValidator.validate("Name"));
  }

  /**
   * Test {@link VariableNameValidator#validateVariables(Map)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link HashMap#HashMap()} {@code 42} is {@code 42}.</li>
   *   <li>Then return contains {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableNameValidator#validateVariables(Map)}
   */
  @Test
  @DisplayName("Test validateVariables(Map); given '42'; when HashMap() '42' is '42'; then return contains '42'")
  void testValidateVariables_given42_whenHashMap42Is42_thenReturnContains42() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.put("42", "42");

    // Act
    Set<String> actualValidateVariablesResult = variableNameValidator.validateVariables(variables);

    // Assert
    assertEquals(1, actualValidateVariablesResult.size());
    assertTrue(actualValidateVariablesResult.contains("42"));
  }

  /**
   * Test {@link VariableNameValidator#validateVariables(Map)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link HashMap#HashMap()} {@code foo} is {@code 42}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableNameValidator#validateVariables(Map)}
   */
  @Test
  @DisplayName("Test validateVariables(Map); given 'foo'; when HashMap() 'foo' is '42'; then return Empty")
  void testValidateVariables_givenFoo_whenHashMapFooIs42_thenReturnEmpty() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.put("foo", "42");

    // Act and Assert
    assertTrue(variableNameValidator.validateVariables(variables).isEmpty());
  }

  /**
   * Test {@link VariableNameValidator#validateVariables(Map)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link HashMap#HashMap()} {@code null} is {@code 42}.</li>
   *   <li>Then return contains {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableNameValidator#validateVariables(Map)}
   */
  @Test
  @DisplayName("Test validateVariables(Map); given 'null'; when HashMap() 'null' is '42'; then return contains 'null'")
  void testValidateVariables_givenNull_whenHashMapNullIs42_thenReturnContainsNull() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.put(null, "42");

    // Act
    Set<String> actualValidateVariablesResult = variableNameValidator.validateVariables(variables);

    // Assert
    assertEquals(1, actualValidateVariablesResult.size());
    assertTrue(actualValidateVariablesResult.contains(null));
  }

  /**
   * Test {@link VariableNameValidator#validateVariables(Map)}.
   * <ul>
   *   <li>Given {@code UU}.</li>
   *   <li>When {@link HashMap#HashMap()} computeIfPresent {@code UU} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableNameValidator#validateVariables(Map)}
   */
  @Test
  @DisplayName("Test validateVariables(Map); given 'UU'; when HashMap() computeIfPresent 'UU' and BiFunction")
  void testValidateVariables_givenUu_whenHashMapComputeIfPresentUuAndBiFunction() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("UU", mock(BiFunction.class));
    variables.put("foo", "42");

    // Act and Assert
    assertTrue(variableNameValidator.validateVariables(variables).isEmpty());
  }

  /**
   * Test {@link VariableNameValidator#validateVariables(Map)}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableNameValidator#validateVariables(Map)}
   */
  @Test
  @DisplayName("Test validateVariables(Map); when HashMap(); then return Empty")
  void testValidateVariables_whenHashMap_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(variableNameValidator.validateVariables(new HashMap<>()).isEmpty());
  }
}
