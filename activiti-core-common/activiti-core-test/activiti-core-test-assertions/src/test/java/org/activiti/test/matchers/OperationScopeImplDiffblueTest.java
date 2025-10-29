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
package org.activiti.test.matchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class OperationScopeImplDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link OperationScopeImpl#OperationScopeImpl(String, String)}
   *   <li>{@link OperationScopeImpl#setProcessInstanceId(String)}
   *   <li>{@link OperationScopeImpl#setTaskId(String)}
   *   <li>{@link OperationScopeImpl#getProcessInstanceId()}
   *   <li>{@link OperationScopeImpl#getTaskId()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    OperationScopeImpl actualOperationScopeImpl = new OperationScopeImpl("42", "42");
    actualOperationScopeImpl.setProcessInstanceId("42");
    actualOperationScopeImpl.setTaskId("42");
    String actualProcessInstanceId = actualOperationScopeImpl.getProcessInstanceId();

    // Assert that nothing has changed
    assertEquals("42", actualProcessInstanceId);
    assertEquals("42", actualOperationScopeImpl.getTaskId());
  }

  /**
   * Method under test: {@link OperationScopeImpl#processInstanceScope(String)}
   */
  @Test
  void testProcessInstanceScope() {
    // Arrange and Act
    OperationScope actualProcessInstanceScopeResult = OperationScopeImpl.processInstanceScope("42");

    // Assert
    assertTrue(actualProcessInstanceScopeResult instanceof OperationScopeImpl);
    assertEquals("42", actualProcessInstanceScopeResult.getProcessInstanceId());
    assertNull(actualProcessInstanceScopeResult.getTaskId());
  }

  /**
   * Method under test: {@link OperationScopeImpl#taskScope(String)}
   */
  @Test
  void testTaskScope() {
    // Arrange and Act
    OperationScope actualTaskScopeResult = OperationScopeImpl.taskScope("42");

    // Assert
    assertTrue(actualTaskScopeResult instanceof OperationScopeImpl);
    assertEquals("42", actualTaskScopeResult.getTaskId());
    assertNull(actualTaskScopeResult.getProcessInstanceId());
  }

  /**
   * Method under test: {@link OperationScopeImpl#scope(String, String)}
   */
  @Test
  void testScope() {
    // Arrange and Act
    OperationScope actualScopeResult = OperationScopeImpl.scope("42", "42");

    // Assert
    assertTrue(actualScopeResult instanceof OperationScopeImpl);
    assertEquals("42", actualScopeResult.getProcessInstanceId());
    assertEquals("42", actualScopeResult.getTaskId());
  }
}
