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
package org.activiti.spring.process.variable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.util.List;
import java.util.Map;
import org.activiti.engine.ActivitiException;
import org.activiti.spring.process.model.VariableDefinition;
import org.activiti.spring.process.variable.types.VariableType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {VariableValidationService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class VariableValidationServiceDiffblueTest {
  @Autowired
  private Map<String, VariableType> map;

  @MockBean
  private VariableType variableType;

  @Autowired
  private VariableValidationService variableValidationService;

  /**
   * Test {@link VariableValidationService#validate(Object, VariableDefinition)}.
   * <ul>
   *   <li>When {@link VariableDefinition#VariableDefinition()}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableValidationService#validate(Object, VariableDefinition)}
   */
  @Test
  @DisplayName("Test validate(Object, VariableDefinition); when VariableDefinition(); then return 'false'")
  void testValidate_whenVariableDefinition_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(variableValidationService.validate("Var", new VariableDefinition()));
  }

  /**
   * Test
   * {@link VariableValidationService#validateWithErrors(Object, VariableDefinition)}.
   * <ul>
   *   <li>When {@link VariableDefinition#VariableDefinition()}.</li>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableValidationService#validateWithErrors(Object, VariableDefinition)}
   */
  @Test
  @DisplayName("Test validateWithErrors(Object, VariableDefinition); when VariableDefinition(); then return size is one")
  void testValidateWithErrors_whenVariableDefinition_thenReturnSizeIsOne() {
    // Arrange and Act
    List<ActivitiException> actualValidateWithErrorsResult = variableValidationService.validateWithErrors("Var",
        new VariableDefinition());

    // Assert
    assertEquals(1, actualValidateWithErrorsResult.size());
    ActivitiException getResult = actualValidateWithErrorsResult.get(0);
    assertEquals("null has no type", getResult.getLocalizedMessage());
    assertEquals("null has no type", getResult.getMessage());
    assertNull(getResult.getCause());
    assertEquals(0, getResult.getSuppressed().length);
  }
}
