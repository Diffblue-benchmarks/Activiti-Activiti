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

public class DefaultDelegateInterceptorDiffblueTest {
  /**
   * Test {@link DefaultDelegateInterceptor#handleInvocation(DelegateInvocation)}.
   * <ul>
   *   <li>When {@link ActivityBehavior}
   * {@link ActivityBehavior#execute(DelegateExecution)} does nothing.</li>
   *   <li>Then calls {@link ActivityBehavior#execute(DelegateExecution)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultDelegateInterceptor#handleInvocation(DelegateInvocation)}
   */
  @Test
  public void testHandleInvocation_whenActivityBehaviorExecuteDoesNothing_thenCallsExecute() {
    // Arrange
    DefaultDelegateInterceptor defaultDelegateInterceptor = new DefaultDelegateInterceptor();
    ActivityBehavior behaviorInstance = mock(ActivityBehavior.class);
    doNothing().when(behaviorInstance).execute(Mockito.<DelegateExecution>any());

    // Act
    defaultDelegateInterceptor.handleInvocation(
        new ActivityBehaviorInvocation(behaviorInstance, ExecutionEntityImpl.createWithEmptyRelationshipCollections()));

    // Assert
    verify(behaviorInstance).execute(isA(DelegateExecution.class));
  }
}
