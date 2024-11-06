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
package org.activiti.engine.impl.bpmn.behavior;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import java.util.Map;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.persistence.entity.VariableScopeImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.mockito.Mockito;

public class VariablesPropagatorDiffblueTest {
  /**
   * Test {@link VariablesPropagator#propagate(DelegateExecution, Map)}.
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#getProcessInstanceId()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariablesPropagator#propagate(DelegateExecution, Map)}
   */
  @Test
  public void testPropagate_thenCallsGetProcessInstanceId() {
    // Arrange
    VariablesPropagator variablesPropagator = new VariablesPropagator(new CopyVariablesCalculator());
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.isMultiInstanceRoot()).thenReturn(false);
    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getProcessInstanceId()).thenReturn(null);
    when(execution.getParent()).thenReturn(executionEntityImpl);

    HashMap<String, Object> availableVariables = new HashMap<>();
    availableVariables.put("foo", JSONObject.NULL);

    // Act
    variablesPropagator.propagate(execution, availableVariables);

    // Assert that nothing has changed
    verify(execution).getParent();
    verify(execution).getProcessInstanceId();
    verify(executionEntityImpl).isMultiInstanceRoot();
  }

  /**
   * Test {@link VariablesPropagator#propagate(DelegateExecution, Map)}.
   * <ul>
   *   <li>Then calls {@link VariableScopeImpl#setVariablesLocal(Map)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariablesPropagator#propagate(DelegateExecution, Map)}
   */
  @Test
  public void testPropagate_thenCallsSetVariablesLocal() {
    // Arrange
    VariablesPropagator variablesPropagator = new VariablesPropagator(new CopyVariablesCalculator());
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.isMultiInstanceRoot()).thenReturn(true);
    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    doNothing().when(execution).setVariablesLocal(Mockito.<Map<String, Object>>any());
    when(execution.getParent()).thenReturn(executionEntityImpl);

    HashMap<String, Object> availableVariables = new HashMap<>();
    availableVariables.put("foo", JSONObject.NULL);

    // Act
    variablesPropagator.propagate(execution, availableVariables);

    // Assert that nothing has changed
    verify(execution).getParent();
    verify(executionEntityImpl).isMultiInstanceRoot();
    verify(execution).setVariablesLocal(isA(Map.class));
  }
}
