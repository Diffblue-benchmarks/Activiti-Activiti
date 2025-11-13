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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {VariableNameValidator.class})
@ExtendWith(SpringExtension.class)
class VariableNameValidatorDiffblueTest {
  @Autowired private VariableNameValidator variableNameValidator;

  /**
   * Test {@link VariableNameValidator#validate(String)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link VariableNameValidator#validate(String)}
   */
  @Test
  @DisplayName("Test validate(String); when empty string; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean VariableNameValidator.validate(String)"})
  void testValidate_whenEmptyString_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(variableNameValidator.validate(""));
  }

  /**
   * Test {@link VariableNameValidator#validate(String)}.
   *
   * <ul>
   *   <li>When {@code Name}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link VariableNameValidator#validate(String)}
   */
  @Test
  @DisplayName("Test validate(String); when 'Name'; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean VariableNameValidator.validate(String)"})
  void testValidate_whenName_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(variableNameValidator.validate("Name"));
  }

  /**
   * Test {@link VariableNameValidator#validate(String)}.
   *
   * <ul>
   *   <li>When {@code not empty}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link VariableNameValidator#validate(String)}
   */
  @Test
  @DisplayName("Test validate(String); when 'not empty'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean VariableNameValidator.validate(String)"})
  void testValidate_whenNotEmpty_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(variableNameValidator.validate("not empty"));
  }

  /**
   * Test {@link VariableNameValidator#validateVariables(Map)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link HashMap#HashMap()} {@code 42} is {@code Value}.
   *   <li>Then return contains {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link VariableNameValidator#validateVariables(Map)}
   */
  @Test
  @DisplayName(
      "Test validateVariables(Map); given '42'; when HashMap() '42' is 'Value'; then return contains '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Set VariableNameValidator.validateVariables(Map)"})
  void testValidateVariables_given42_whenHashMap42IsValue_thenReturnContains42() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.put("42", "Value");

    // Act
    Set<String> actualValidateVariablesResult = variableNameValidator.validateVariables(variables);

    // Assert
    assertEquals(1, actualValidateVariablesResult.size());
    assertTrue(actualValidateVariablesResult.contains("42"));
  }

  /**
   * Test {@link VariableNameValidator#validateVariables(Map)}.
   *
   * <ul>
   *   <li>Given {@code Key}.
   *   <li>When {@link HashMap#HashMap()} {@code Key} is {@code Value}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link VariableNameValidator#validateVariables(Map)}
   */
  @Test
  @DisplayName(
      "Test validateVariables(Map); given 'Key'; when HashMap() 'Key' is 'Value'; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Set VariableNameValidator.validateVariables(Map)"})
  void testValidateVariables_givenKey_whenHashMapKeyIsValue_thenReturnEmpty() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.put("Key", "Value");

    // Act and Assert
    assertTrue(variableNameValidator.validateVariables(variables).isEmpty());
  }

  /**
   * Test {@link VariableNameValidator#validateVariables(Map)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link HashMap#HashMap()} {@code null} is {@code Value}.
   *   <li>Then return contains {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link VariableNameValidator#validateVariables(Map)}
   */
  @Test
  @DisplayName(
      "Test validateVariables(Map); given 'null'; when HashMap() 'null' is 'Value'; then return contains 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Set VariableNameValidator.validateVariables(Map)"})
  void testValidateVariables_givenNull_whenHashMapNullIsValue_thenReturnContainsNull() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.put(null, "Value");

    // Act
    Set<String> actualValidateVariablesResult = variableNameValidator.validateVariables(variables);

    // Assert
    assertEquals(1, actualValidateVariablesResult.size());
    assertTrue(actualValidateVariablesResult.contains(null));
  }

  /**
   * Test {@link VariableNameValidator#validateVariables(Map)}.
   *
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link VariableNameValidator#validateVariables(Map)}
   */
  @Test
  @DisplayName("Test validateVariables(Map); when HashMap(); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Set VariableNameValidator.validateVariables(Map)"})
  void testValidateVariables_whenHashMap_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(variableNameValidator.validateVariables(new HashMap<>()).isEmpty());
  }

  /**
   * Test {@link VariableNameValidator#validateVariables(Map)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link VariableNameValidator#validateVariables(Map)}
   */
  @Test
  @DisplayName("Test validateVariables(Map); when 'null'; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Set VariableNameValidator.validateVariables(Map)"})
  void testValidateVariables_whenNull_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(variableNameValidator.validateVariables(null).isEmpty());
  }
}
