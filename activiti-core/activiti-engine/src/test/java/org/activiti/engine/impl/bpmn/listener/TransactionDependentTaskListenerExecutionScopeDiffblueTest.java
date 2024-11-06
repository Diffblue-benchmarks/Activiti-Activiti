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
import org.activiti.bpmn.model.Task;
import org.junit.Test;

public class TransactionDependentTaskListenerExecutionScopeDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link TransactionDependentTaskListenerExecutionScope#TransactionDependentTaskListenerExecutionScope(String, String, Task, Map, Map)}
   *   <li>
   * {@link TransactionDependentTaskListenerExecutionScope#getCustomPropertiesMap()}
   *   <li>{@link TransactionDependentTaskListenerExecutionScope#getExecutionId()}
   *   <li>
   * {@link TransactionDependentTaskListenerExecutionScope#getExecutionVariables()}
   *   <li>
   * {@link TransactionDependentTaskListenerExecutionScope#getProcessInstanceId()}
   *   <li>{@link TransactionDependentTaskListenerExecutionScope#getTask()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    Task task = new Task();
    HashMap<String, Object> executionVariables = new HashMap<>();
    HashMap<String, Object> customPropertiesMap = new HashMap<>();

    // Act
    TransactionDependentTaskListenerExecutionScope actualTransactionDependentTaskListenerExecutionScope = new TransactionDependentTaskListenerExecutionScope(
        "42", "42", task, executionVariables, customPropertiesMap);
    Map<String, Object> actualCustomPropertiesMap = actualTransactionDependentTaskListenerExecutionScope
        .getCustomPropertiesMap();
    String actualExecutionId = actualTransactionDependentTaskListenerExecutionScope.getExecutionId();
    Map<String, Object> actualExecutionVariables = actualTransactionDependentTaskListenerExecutionScope
        .getExecutionVariables();
    String actualProcessInstanceId = actualTransactionDependentTaskListenerExecutionScope.getProcessInstanceId();
    Task actualTask = actualTransactionDependentTaskListenerExecutionScope.getTask();

    // Assert
    assertEquals("42", actualExecutionId);
    assertEquals("42", actualProcessInstanceId);
    assertTrue(actualCustomPropertiesMap.isEmpty());
    assertTrue(actualExecutionVariables.isEmpty());
    assertSame(customPropertiesMap, actualCustomPropertiesMap);
    assertSame(executionVariables, actualExecutionVariables);
    assertSame(task, actualTask);
  }
}
