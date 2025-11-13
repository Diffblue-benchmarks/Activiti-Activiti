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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.Map;
import org.activiti.api.process.model.IntegrationContext;
import org.activiti.api.process.runtime.connector.Connector;
import org.activiti.api.runtime.model.impl.IntegrationContextImpl;
import org.activiti.bpmn.model.ServiceTask;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.bpmn.behavior.DelegateExecutionOutcome;
import org.activiti.engine.impl.bpmn.behavior.VariablesPropagator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

@ExtendWith(MockitoExtension.class)
class DefaultServiceTaskBehaviorDiffblueTest {
  @Mock private ApplicationContext applicationContext;

  @InjectMocks private DefaultServiceTaskBehavior defaultServiceTaskBehavior;

  @Mock private IntegrationContextBuilder integrationContextBuilder;

  @Mock private VariablesPropagator variablesPropagator;

  /**
   * Test {@link DefaultServiceTaskBehavior#apply(DelegateExecution)} with {@code
   * DelegateExecution}.
   *
   * <ul>
   *   <li>Given {@link Connector} {@link Connector#apply(Object)} return {@link
   *       IntegrationContextImpl} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link DefaultServiceTaskBehavior#apply(DelegateExecution)}
   */
  @Test
  @DisplayName(
      "Test apply(DelegateExecution) with 'DelegateExecution'; given Connector apply(Object) return IntegrationContextImpl (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "DelegateExecutionOutcome DefaultServiceTaskBehavior.apply(DelegateExecution)"
  })
  void testApplyWithDelegateExecution_givenConnectorApplyReturnIntegrationContextImpl()
      throws BeansException {
    // Arrange
    Connector connector = mock(Connector.class);
    when(connector.apply(Mockito.<IntegrationContext>any()))
        .thenReturn(new IntegrationContextImpl());
    when(applicationContext.getBean(Mockito.<String>any(), eq(Connector.class)))
        .thenReturn(connector);
    when(integrationContextBuilder.from(Mockito.<DelegateExecution>any()))
        .thenReturn(new IntegrationContextImpl());
    doNothing()
        .when(variablesPropagator)
        .propagate(Mockito.<DelegateExecution>any(), Mockito.<Map<String, Object>>any());

    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.getCurrentFlowElement()).thenReturn(new ServiceTask());

    // Act
    DelegateExecutionOutcome actualApplyResult = defaultServiceTaskBehavior.apply(execution);

    // Assert
    verify(connector).apply(isA(IntegrationContext.class));
    verify(execution).getCurrentFlowElement();
    verify(variablesPropagator).propagate(isA(DelegateExecution.class), isA(Map.class));
    verify(integrationContextBuilder).from(isA(DelegateExecution.class));
    verify(applicationContext).getBean(isNull(), isA(Class.class));
    assertEquals(DelegateExecutionOutcome.LEAVE_EXECUTION, actualApplyResult);
  }

  /**
   * Test {@link DefaultServiceTaskBehavior#apply(DelegateExecution)} with {@code
   * DelegateExecution}.
   *
   * <ul>
   *   <li>Then calls {@link IntegrationContextImpl#getOutBoundVariables()}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultServiceTaskBehavior#apply(DelegateExecution)}
   */
  @Test
  @DisplayName(
      "Test apply(DelegateExecution) with 'DelegateExecution'; then calls getOutBoundVariables()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "DelegateExecutionOutcome DefaultServiceTaskBehavior.apply(DelegateExecution)"
  })
  void testApplyWithDelegateExecution_thenCallsGetOutBoundVariables() throws BeansException {
    // Arrange
    IntegrationContextImpl integrationContextImpl = mock(IntegrationContextImpl.class);
    when(integrationContextImpl.getOutBoundVariables()).thenReturn(new HashMap<>());

    Connector connector = mock(Connector.class);
    when(connector.apply(Mockito.<IntegrationContext>any())).thenReturn(integrationContextImpl);
    when(applicationContext.getBean(Mockito.<String>any(), eq(Connector.class)))
        .thenReturn(connector);
    when(integrationContextBuilder.from(Mockito.<DelegateExecution>any()))
        .thenReturn(new IntegrationContextImpl());
    doNothing()
        .when(variablesPropagator)
        .propagate(Mockito.<DelegateExecution>any(), Mockito.<Map<String, Object>>any());

    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.getCurrentFlowElement()).thenReturn(new ServiceTask());

    // Act
    DelegateExecutionOutcome actualApplyResult = defaultServiceTaskBehavior.apply(execution);

    // Assert
    verify(connector).apply(isA(IntegrationContext.class));
    verify(integrationContextImpl).getOutBoundVariables();
    verify(execution).getCurrentFlowElement();
    verify(variablesPropagator).propagate(isA(DelegateExecution.class), isA(Map.class));
    verify(integrationContextBuilder).from(isA(DelegateExecution.class));
    verify(applicationContext).getBean(isNull(), isA(Class.class));
    assertEquals(DelegateExecutionOutcome.LEAVE_EXECUTION, actualApplyResult);
  }

  /**
   * Test {@link DefaultServiceTaskBehavior#hasConnectorBean(DelegateExecution)}.
   *
   * <ul>
   *   <li>Given {@link ApplicationContext} {@link ApplicationContext#containsBean(String)} return
   *       {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultServiceTaskBehavior#hasConnectorBean(DelegateExecution)}
   */
  @Test
  @DisplayName(
      "Test hasConnectorBean(DelegateExecution); given ApplicationContext containsBean(String) return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DefaultServiceTaskBehavior.hasConnectorBean(DelegateExecution)"})
  void testHasConnectorBean_givenApplicationContextContainsBeanReturnFalse() {
    // Arrange
    when(applicationContext.containsBean(Mockito.<String>any())).thenReturn(false);

    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.getCurrentFlowElement()).thenReturn(new ServiceTask());

    // Act
    boolean actualHasConnectorBeanResult = defaultServiceTaskBehavior.hasConnectorBean(execution);

    // Assert
    verify(execution).getCurrentFlowElement();
    verify(applicationContext).containsBean(null);
    assertFalse(actualHasConnectorBeanResult);
  }
}
