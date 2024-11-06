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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.bpmn.model.ServiceTask;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.VariableScope;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.persistence.entity.integration.IntegrationContextEntity;
import org.activiti.engine.impl.persistence.entity.integration.IntegrationContextEntityImpl;
import org.activiti.runtime.api.impl.ExtensionsVariablesMappingProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {IntegrationContextBuilder.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class IntegrationContextBuilderDiffblueTest {
  @MockBean
  private ExpressionManager expressionManager;

  @MockBean
  private ExtensionsVariablesMappingProvider extensionsVariablesMappingProvider;

  @Autowired
  private IntegrationContextBuilder integrationContextBuilder;

  /**
   * Test {@link IntegrationContextBuilder#from(DelegateExecution)} with
   * {@code execution}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IntegrationContextBuilder#from(DelegateExecution)}
   */
  @Test
  @DisplayName("Test from(DelegateExecution) with 'execution'; then throw ActivitiException")
  void testFromWithExecution_thenThrowActivitiException() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getParentProcessInstanceId()).thenThrow(new ActivitiException("An error occurred"));
    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getId()).thenReturn("42");
    when(execution.getCurrentActivityId()).thenReturn("42");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getProcessInstanceBusinessKey()).thenReturn("Process Instance Business Key");
    when(execution.getProcessInstanceId()).thenReturn("42");
    when(execution.getRootProcessInstanceId()).thenReturn("42");
    when(execution.getProcessInstance()).thenReturn(executionEntityImpl);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> integrationContextBuilder.from(execution));
    verify(execution).getId();
    verify(execution).getCurrentActivityId();
    verify(executionEntityImpl).getParentProcessInstanceId();
    verify(execution).getProcessDefinitionId();
    verify(execution).getProcessInstance();
    verify(execution).getProcessInstanceBusinessKey();
    verify(execution).getProcessInstanceId();
    verify(execution).getRootProcessInstanceId();
  }

  /**
   * Test
   * {@link IntegrationContextBuilder#from(IntegrationContextEntity, DelegateExecution)}
   * with {@code integrationContextEntity}, {@code execution}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link IntegrationContextBuilder#from(IntegrationContextEntity, DelegateExecution)}
   */
  @Test
  @DisplayName("Test from(IntegrationContextEntity, DelegateExecution) with 'integrationContextEntity', 'execution'; then throw ActivitiException")
  void testFromWithIntegrationContextEntityExecution_thenThrowActivitiException() {
    // Arrange
    IntegrationContextEntityImpl integrationContextEntity = new IntegrationContextEntityImpl();
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getParentProcessInstanceId()).thenThrow(new ActivitiException("An error occurred"));
    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getId()).thenReturn("42");
    when(execution.getCurrentActivityId()).thenReturn("42");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getProcessInstanceBusinessKey()).thenReturn("Process Instance Business Key");
    when(execution.getProcessInstanceId()).thenReturn("42");
    when(execution.getRootProcessInstanceId()).thenReturn("42");
    when(execution.getProcessInstance()).thenReturn(executionEntityImpl);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> integrationContextBuilder.from(integrationContextEntity, execution));
    verify(execution).getId();
    verify(execution).getCurrentActivityId();
    verify(executionEntityImpl).getParentProcessInstanceId();
    verify(execution).getProcessDefinitionId();
    verify(execution).getProcessInstance();
    verify(execution).getProcessInstanceBusinessKey();
    verify(execution).getProcessInstanceId();
    verify(execution).getRootProcessInstanceId();
  }

  /**
   * Test
   * {@link IntegrationContextBuilder#resolveServiceTaskNameExpression(ServiceTask, DelegateExecution)}.
   * <p>
   * Method under test:
   * {@link IntegrationContextBuilder#resolveServiceTaskNameExpression(ServiceTask, DelegateExecution)}
   */
  @Test
  @DisplayName("Test resolveServiceTaskNameExpression(ServiceTask, DelegateExecution)")
  void testResolveServiceTaskNameExpression() {
    // Arrange
    when(expressionManager.createExpression(Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));
    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getName()).thenReturn("Name");

    // Act
    String actualResolveServiceTaskNameExpressionResult = integrationContextBuilder
        .resolveServiceTaskNameExpression(serviceTask, ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(serviceTask).getName();
    verify(expressionManager).createExpression(eq("Name"));
    assertEquals("Name", actualResolveServiceTaskNameExpressionResult);
  }

  /**
   * Test
   * {@link IntegrationContextBuilder#resolveServiceTaskNameExpression(ServiceTask, DelegateExecution)}.
   * <ul>
   *   <li>Given empty string.</li>
   *   <li>Then return empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link IntegrationContextBuilder#resolveServiceTaskNameExpression(ServiceTask, DelegateExecution)}
   */
  @Test
  @DisplayName("Test resolveServiceTaskNameExpression(ServiceTask, DelegateExecution); given empty string; then return empty string")
  void testResolveServiceTaskNameExpression_givenEmptyString_thenReturnEmptyString() {
    // Arrange
    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getName()).thenReturn("");

    // Act
    String actualResolveServiceTaskNameExpressionResult = integrationContextBuilder
        .resolveServiceTaskNameExpression(serviceTask, ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(serviceTask).getName();
    assertEquals("", actualResolveServiceTaskNameExpressionResult);
  }

  /**
   * Test
   * {@link IntegrationContextBuilder#resolveServiceTaskNameExpression(ServiceTask, DelegateExecution)}.
   * <ul>
   *   <li>Then calls {@link Expression#getValue(VariableScope)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link IntegrationContextBuilder#resolveServiceTaskNameExpression(ServiceTask, DelegateExecution)}
   */
  @Test
  @DisplayName("Test resolveServiceTaskNameExpression(ServiceTask, DelegateExecution); then calls getValue(VariableScope)")
  void testResolveServiceTaskNameExpression_thenCallsGetValue() {
    // Arrange
    Expression expression = mock(Expression.class);
    when(expression.getValue(Mockito.<VariableScope>any())).thenThrow(new ActivitiException("An error occurred"));
    when(expressionManager.createExpression(Mockito.<String>any())).thenReturn(expression);
    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getName()).thenReturn("Name");

    // Act
    String actualResolveServiceTaskNameExpressionResult = integrationContextBuilder
        .resolveServiceTaskNameExpression(serviceTask, ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(serviceTask).getName();
    verify(expression).getValue(isA(VariableScope.class));
    verify(expressionManager).createExpression(eq("Name"));
    assertEquals("Name", actualResolveServiceTaskNameExpressionResult);
  }

  /**
   * Test
   * {@link IntegrationContextBuilder#resolveServiceTaskNameExpression(ServiceTask, DelegateExecution)}.
   * <ul>
   *   <li>Then return {@code Value}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link IntegrationContextBuilder#resolveServiceTaskNameExpression(ServiceTask, DelegateExecution)}
   */
  @Test
  @DisplayName("Test resolveServiceTaskNameExpression(ServiceTask, DelegateExecution); then return 'Value'")
  void testResolveServiceTaskNameExpression_thenReturnValue() {
    // Arrange
    when(expressionManager.createExpression(Mockito.<String>any())).thenReturn(new FixedValue("Value"));
    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getName()).thenReturn("Name");

    // Act
    String actualResolveServiceTaskNameExpressionResult = integrationContextBuilder
        .resolveServiceTaskNameExpression(serviceTask, ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(serviceTask).getName();
    verify(expressionManager).createExpression(eq("Name"));
    assertEquals("Value", actualResolveServiceTaskNameExpressionResult);
  }

  /**
   * Test
   * {@link IntegrationContextBuilder#resolveServiceTaskNameExpression(ServiceTask, DelegateExecution)}.
   * <ul>
   *   <li>When {@link ServiceTask} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link IntegrationContextBuilder#resolveServiceTaskNameExpression(ServiceTask, DelegateExecution)}
   */
  @Test
  @DisplayName("Test resolveServiceTaskNameExpression(ServiceTask, DelegateExecution); when ServiceTask (default constructor); then return 'null'")
  void testResolveServiceTaskNameExpression_whenServiceTask_thenReturnNull() {
    // Arrange
    ServiceTask serviceTask = new ServiceTask();

    // Act and Assert
    assertNull(integrationContextBuilder.resolveServiceTaskNameExpression(serviceTask,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }
}
