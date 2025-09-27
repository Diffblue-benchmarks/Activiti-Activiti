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
package org.activiti.runtime.api.connector;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.bpmn.model.ServiceTask;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.bpmn.behavior.VariablesPropagator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DefaultServiceTaskBehavior.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class DefaultServiceTaskBehaviorDiffblueTest {
  @Autowired private ApplicationContext applicationContext;

  @Autowired private DefaultServiceTaskBehavior defaultServiceTaskBehavior;

  @MockBean private IntegrationContextBuilder integrationContextBuilder;

  @MockBean private VariablesPropagator variablesPropagator;

  /**
   * Test {@link DefaultServiceTaskBehavior#hasConnectorBean(DelegateExecution)}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultServiceTaskBehavior#hasConnectorBean(DelegateExecution)}
   */
  @Test
  @DisplayName("Test hasConnectorBean(DelegateExecution); then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DefaultServiceTaskBehavior.hasConnectorBean(DelegateExecution)"})
  void testHasConnectorBean_thenReturnFalse() {
    // Arrange
    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setImplementation("Implementation");

    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.getCurrentFlowElement()).thenReturn(serviceTask);

    // Act
    boolean actualHasConnectorBeanResult = defaultServiceTaskBehavior.hasConnectorBean(execution);

    // Assert
    verify(execution).getCurrentFlowElement();
    assertFalse(actualHasConnectorBeanResult);
  }
}
