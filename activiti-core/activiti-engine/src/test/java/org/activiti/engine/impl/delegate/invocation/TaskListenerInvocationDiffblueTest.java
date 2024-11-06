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
import static org.mockito.Mockito.mock;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.persistence.entity.TaskEntityImpl;
import org.junit.Test;

public class TaskListenerInvocationDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link TaskListenerInvocation#TaskListenerInvocation(TaskListener, DelegateTask)}
   *   <li>{@link TaskListenerInvocation#getTarget()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    TaskListener executionListenerInstance = mock(TaskListener.class);

    // Act
    TaskListenerInvocation actualTaskListenerInvocation = new TaskListenerInvocation(executionListenerInstance,
        new TaskEntityImpl());
    Object actualTarget = actualTaskListenerInvocation.getTarget();

    // Assert
    assertNull(actualTaskListenerInvocation.getInvocationParameters());
    assertNull(actualTaskListenerInvocation.getInvocationResult());
    assertSame(executionListenerInstance, actualTarget);
  }
}
