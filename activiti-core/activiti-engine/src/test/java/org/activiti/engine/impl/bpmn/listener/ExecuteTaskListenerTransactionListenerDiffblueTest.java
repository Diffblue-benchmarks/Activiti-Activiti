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
import org.activiti.bpmn.model.Task;
import org.activiti.engine.delegate.TransactionDependentTaskListener;
import org.junit.Test;

public class ExecuteTaskListenerTransactionListenerDiffblueTest {
  /**
   * Test
   * {@link ExecuteTaskListenerTransactionListener#ExecuteTaskListenerTransactionListener(TransactionDependentTaskListener, TransactionDependentTaskListenerExecutionScope)}.
   * <p>
   * Method under test:
   * {@link ExecuteTaskListenerTransactionListener#ExecuteTaskListenerTransactionListener(TransactionDependentTaskListener, TransactionDependentTaskListenerExecutionScope)}
   */
  @Test
  public void testNewExecuteTaskListenerTransactionListener() {
    // Arrange
    TransactionDependentTaskListener listener = mock(TransactionDependentTaskListener.class);
    Task task = new Task();
    HashMap<String, Object> executionVariables = new HashMap<>();
    HashMap<String, Object> customPropertiesMap = new HashMap<>();

    // Act and Assert
    TransactionDependentTaskListenerExecutionScope transactionDependentTaskListenerExecutionScope = (new ExecuteTaskListenerTransactionListener(
        listener, new TransactionDependentTaskListenerExecutionScope("42", "42", task, executionVariables,
            customPropertiesMap))).scope;
    assertEquals("42", transactionDependentTaskListenerExecutionScope.getExecutionId());
    assertEquals("42", transactionDependentTaskListenerExecutionScope.getProcessInstanceId());
    Map<String, Object> customPropertiesMap2 = transactionDependentTaskListenerExecutionScope.getCustomPropertiesMap();
    assertTrue(customPropertiesMap2.isEmpty());
    Map<String, Object> executionVariables2 = transactionDependentTaskListenerExecutionScope.getExecutionVariables();
    assertTrue(executionVariables2.isEmpty());
    assertSame(customPropertiesMap, customPropertiesMap2);
    assertSame(executionVariables, executionVariables2);
    assertSame(task, transactionDependentTaskListenerExecutionScope.getTask());
  }
}
