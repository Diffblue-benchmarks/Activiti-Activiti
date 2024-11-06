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
package org.activiti.engine.impl.bpmn.listener;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.util.HashMap;
import java.util.Map;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.FlowElement;
import org.junit.Test;

public class TransactionDependentExecutionListenerExecutionScopeDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link TransactionDependentExecutionListenerExecutionScope#TransactionDependentExecutionListenerExecutionScope(String, String, FlowElement, Map, Map)}
   *   <li>
   * {@link TransactionDependentExecutionListenerExecutionScope#getCustomPropertiesMap()}
   *   <li>
   * {@link TransactionDependentExecutionListenerExecutionScope#getExecutionId()}
   *   <li>
   * {@link TransactionDependentExecutionListenerExecutionScope#getExecutionVariables()}
   *   <li>
   * {@link TransactionDependentExecutionListenerExecutionScope#getFlowElement()}
   *   <li>
   * {@link TransactionDependentExecutionListenerExecutionScope#getProcessInstanceId()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    AdhocSubProcess flowElement = new AdhocSubProcess();
    HashMap<String, Object> executionVariables = new HashMap<>();
    HashMap<String, Object> customPropertiesMap = new HashMap<>();

    // Act
    TransactionDependentExecutionListenerExecutionScope actualTransactionDependentExecutionListenerExecutionScope = new TransactionDependentExecutionListenerExecutionScope(
        "42", "42", flowElement, executionVariables, customPropertiesMap);
    Map<String, Object> actualCustomPropertiesMap = actualTransactionDependentExecutionListenerExecutionScope
        .getCustomPropertiesMap();
    String actualExecutionId = actualTransactionDependentExecutionListenerExecutionScope.getExecutionId();
    Map<String, Object> actualExecutionVariables = actualTransactionDependentExecutionListenerExecutionScope
        .getExecutionVariables();
    FlowElement actualFlowElement = actualTransactionDependentExecutionListenerExecutionScope.getFlowElement();

    // Assert
    assertEquals("42", actualExecutionId);
    assertEquals("42", actualTransactionDependentExecutionListenerExecutionScope.getProcessInstanceId());
    assertTrue(actualCustomPropertiesMap.isEmpty());
    assertTrue(actualExecutionVariables.isEmpty());
    assertSame(customPropertiesMap, actualCustomPropertiesMap);
    assertSame(executionVariables, actualExecutionVariables);
    assertSame(flowElement, actualFlowElement);
  }
}
