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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.delegate.ActivityBehavior;
import org.activiti.engine.impl.delegate.ActivityBehaviorInvocation;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.mockito.Mockito;

public class DelegateInvocationDiffblueTest {
  /**
   * Test {@link DelegateInvocation#proceed()}.
   * <p>
   * Method under test: {@link DelegateInvocation#proceed()}
   */
  @Test
  public void testProceed() {
    // Arrange
    ActivityBehavior behaviorInstance = mock(ActivityBehavior.class);
    doNothing().when(behaviorInstance).execute(Mockito.<DelegateExecution>any());

    // Act
    (new ActivityBehaviorInvocation(behaviorInstance, ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
        .proceed();

    // Assert
    verify(behaviorInstance).execute(isA(DelegateExecution.class));
  }

  /**
   * Test {@link DelegateInvocation#getInvocationResult()}.
   * <p>
   * Method under test: {@link DelegateInvocation#getInvocationResult()}
   */
  @Test
  public void testGetInvocationResult() {
    // Arrange
    ActivityBehavior behaviorInstance = mock(ActivityBehavior.class);

    // Act and Assert
    assertNull(
        (new ActivityBehaviorInvocation(behaviorInstance, ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
            .getInvocationResult());
  }

  /**
   * Test {@link DelegateInvocation#getInvocationParameters()}.
   * <p>
   * Method under test: {@link DelegateInvocation#getInvocationParameters()}
   */
  @Test
  public void testGetInvocationParameters() {
    // Arrange
    ActivityBehavior behaviorInstance = mock(ActivityBehavior.class);

    // Act and Assert
    assertNull(
        (new ActivityBehaviorInvocation(behaviorInstance, ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
            .getInvocationParameters());
  }
}
