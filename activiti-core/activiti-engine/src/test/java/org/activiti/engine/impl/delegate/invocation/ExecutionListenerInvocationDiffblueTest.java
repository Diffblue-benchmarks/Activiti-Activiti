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
package org.activiti.engine.impl.delegate.invocation;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.mockito.Mockito;

public class ExecutionListenerInvocationDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ExecutionListenerInvocation#ExecutionListenerInvocation(ExecutionListener, DelegateExecution)}
   *   <li>{@link ExecutionListenerInvocation#getTarget()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    ExecutionListener executionListenerInstance = mock(ExecutionListener.class);

    // Act
    ExecutionListenerInvocation actualExecutionListenerInvocation = new ExecutionListenerInvocation(
        executionListenerInstance, ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    Object actualTarget = actualExecutionListenerInvocation.getTarget();

    // Assert
    assertNull(actualExecutionListenerInvocation.getInvocationParameters());
    assertNull(actualExecutionListenerInvocation.getInvocationResult());
    assertSame(executionListenerInstance, actualTarget);
  }

  /**
   * Test {@link ExecutionListenerInvocation#invoke()}.
   * <p>
   * Method under test: {@link ExecutionListenerInvocation#invoke()}
   */
  @Test
  public void testInvoke() {
    // Arrange
    ExecutionListener executionListenerInstance = mock(ExecutionListener.class);
    doNothing().when(executionListenerInstance).notify(Mockito.<DelegateExecution>any());

    // Act
    (new ExecutionListenerInvocation(executionListenerInstance,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections())).invoke();

    // Assert
    verify(executionListenerInstance).notify(isA(DelegateExecution.class));
  }
}
