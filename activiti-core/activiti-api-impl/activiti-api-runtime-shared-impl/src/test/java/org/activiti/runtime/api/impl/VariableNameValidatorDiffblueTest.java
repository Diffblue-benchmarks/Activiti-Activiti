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
   * Method under test: {@link VariableNameValidator#validate(String)}
   */
  @Test
  void testValidate() {
    // Arrange, Act and Assert
    assertTrue(variableNameValidator.validate("Name"));
    assertFalse(variableNameValidator.validate("(?i)[a-z][a-z0-9_]*"));
    assertFalse(variableNameValidator.validate(""));
  }

  /**
   * Method under test: {@link VariableNameValidator#validateVariables(Map)}
   */
  @Test
  void testValidateVariables() {
    // Arrange, Act and Assert
    assertTrue(variableNameValidator.validateVariables(new HashMap<>()).isEmpty());
  }

  /**
   * Method under test: {@link VariableNameValidator#validateVariables(Map)}
   */
  @Test
  void testValidateVariables2() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.put("foo", "42");

    // Act and Assert
    assertTrue(variableNameValidator.validateVariables(variables).isEmpty());
  }

  /**
   * Method under test: {@link VariableNameValidator#validateVariables(Map)}
   */
  @Test
  void testValidateVariables3() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("UU", mock(BiFunction.class));
    variables.put("foo", "42");

    // Act and Assert
    assertTrue(variableNameValidator.validateVariables(variables).isEmpty());
  }

  /**
   * Method under test: {@link VariableNameValidator#validateVariables(Map)}
   */
  @Test
  void testValidateVariables4() {
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
   * Method under test: {@link VariableNameValidator#validateVariables(Map)}
   */
  @Test
  void testValidateVariables5() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.put("42", "42");

    // Act
    Set<String> actualValidateVariablesResult = variableNameValidator.validateVariables(variables);

    // Assert
    assertEquals(1, actualValidateVariablesResult.size());
    assertTrue(actualValidateVariablesResult.contains("42"));
  }
}
