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
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.Map;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.engine.delegate.TransactionDependentExecutionListener;
import org.junit.Test;

public class ExecuteExecutionListenerTransactionListenerDiffblueTest {
  /**
   * Test
   * {@link ExecuteExecutionListenerTransactionListener#ExecuteExecutionListenerTransactionListener(TransactionDependentExecutionListener, TransactionDependentExecutionListenerExecutionScope)}.
   * <p>
   * Method under test:
   * {@link ExecuteExecutionListenerTransactionListener#ExecuteExecutionListenerTransactionListener(TransactionDependentExecutionListener, TransactionDependentExecutionListenerExecutionScope)}
   */
  @Test
  public void testNewExecuteExecutionListenerTransactionListener() {
    // Arrange
    TransactionDependentExecutionListener listener = mock(TransactionDependentExecutionListener.class);
    AdhocSubProcess flowElement = new AdhocSubProcess();
    HashMap<String, Object> executionVariables = new HashMap<>();
    HashMap<String, Object> customPropertiesMap = new HashMap<>();

    // Act and Assert
    TransactionDependentExecutionListenerExecutionScope transactionDependentExecutionListenerExecutionScope = (new ExecuteExecutionListenerTransactionListener(
        listener, new TransactionDependentExecutionListenerExecutionScope("42", "42", flowElement, executionVariables,
            customPropertiesMap))).scope;
    assertEquals("42", transactionDependentExecutionListenerExecutionScope.getExecutionId());
    assertEquals("42", transactionDependentExecutionListenerExecutionScope.getProcessInstanceId());
    Map<String, Object> customPropertiesMap2 = transactionDependentExecutionListenerExecutionScope
        .getCustomPropertiesMap();
    assertTrue(customPropertiesMap2.isEmpty());
    Map<String, Object> executionVariables2 = transactionDependentExecutionListenerExecutionScope
        .getExecutionVariables();
    assertTrue(executionVariables2.isEmpty());
    assertSame(customPropertiesMap, customPropertiesMap2);
    assertSame(executionVariables, executionVariables2);
    assertSame(flowElement, transactionDependentExecutionListenerExecutionScope.getFlowElement());
  }
}
