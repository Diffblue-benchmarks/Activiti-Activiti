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

import static org.junit.jupiter.api.Assertions.assertNull;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.impl.bpmn.behavior.VariablesCalculator;
import org.activiti.engine.impl.bpmn.behavior.VariablesPropagator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {MappingAwareUserTaskBehavior.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class MappingAwareUserTaskBehaviorDiffblueTest {
  @Autowired
  private MappingAwareUserTaskBehavior mappingAwareUserTaskBehavior;

  @MockBean
  private UserTask userTask;

  @MockBean
  private VariablesCalculator variablesCalculator;

  @MockBean
  private VariablesPropagator variablesPropagator;

  /**
   * Test
   * {@link MappingAwareUserTaskBehavior#MappingAwareUserTaskBehavior(UserTask, VariablesCalculator, VariablesPropagator)}.
   * <p>
   * Method under test:
   * {@link MappingAwareUserTaskBehavior#MappingAwareUserTaskBehavior(UserTask, VariablesCalculator, VariablesPropagator)}
   */
  @Test
  @DisplayName("Test new MappingAwareUserTaskBehavior(UserTask, VariablesCalculator, VariablesPropagator)")
  void testNewMappingAwareUserTaskBehavior() {
    // Arrange, Act and Assert
    assertNull((new MappingAwareUserTaskBehavior(userTask, variablesCalculator, variablesPropagator))
        .getMultiInstanceActivityBehavior());
  }
}
