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
package org.activiti.engine.impl.delegate;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.mockito.Mockito;

public class ActivityBehaviorInvocationDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ActivityBehaviorInvocation#ActivityBehaviorInvocation(ActivityBehavior, DelegateExecution)}
   *   <li>{@link ActivityBehaviorInvocation#getTarget()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    ActivityBehavior behaviorInstance = mock(ActivityBehavior.class);

    // Act
    ActivityBehaviorInvocation actualActivityBehaviorInvocation = new ActivityBehaviorInvocation(behaviorInstance,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    Object actualTarget = actualActivityBehaviorInvocation.getTarget();

    // Assert
    assertNull(actualActivityBehaviorInvocation.getInvocationParameters());
    assertNull(actualActivityBehaviorInvocation.getInvocationResult());
    assertSame(behaviorInstance, actualTarget);
  }

  /**
   * Test {@link ActivityBehaviorInvocation#invoke()}.
   * <p>
   * Method under test: {@link ActivityBehaviorInvocation#invoke()}
   */
  @Test
  public void testInvoke() {
    // Arrange
    ActivityBehavior behaviorInstance = mock(ActivityBehavior.class);
    doNothing().when(behaviorInstance).execute(Mockito.<DelegateExecution>any());

    // Act
    (new ActivityBehaviorInvocation(behaviorInstance, ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
        .invoke();

    // Assert
    verify(behaviorInstance).execute(isA(DelegateExecution.class));
  }
}
