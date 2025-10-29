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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.delegate.ThrowMessage;
import org.activiti.engine.impl.delegate.ThrowMessageDelegate;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.mockito.Mockito;

public class ThrowMessageDelegateInvocationDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ThrowMessageDelegateInvocation#ThrowMessageDelegateInvocation(ThrowMessageDelegate, DelegateExecution, ThrowMessage)}
   *   <li>{@link ThrowMessageDelegateInvocation#getTarget()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    ThrowMessageDelegate delegateInstance = mock(ThrowMessageDelegate.class);
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    ThrowMessageDelegateInvocation actualThrowMessageDelegateInvocation = new ThrowMessageDelegateInvocation(
        delegateInstance, execution, new ThrowMessage("Name"));
    Object actualTarget = actualThrowMessageDelegateInvocation.getTarget();

    // Assert
    ThrowMessage throwMessage = actualThrowMessageDelegateInvocation.message;
    assertEquals("Name", throwMessage.getName());
    assertNull(actualThrowMessageDelegateInvocation.getInvocationParameters());
    assertNull(actualThrowMessageDelegateInvocation.getInvocationResult());
    assertFalse(throwMessage.getBusinessKey().isPresent());
    assertSame(delegateInstance, actualTarget);
  }

  /**
   * Method under test: {@link ThrowMessageDelegateInvocation#invoke()}
   */
  @Test
  public void testInvoke() {
    // Arrange
    ThrowMessageDelegate delegateInstance = mock(ThrowMessageDelegate.class);
    when(delegateInstance.send(Mockito.<DelegateExecution>any(), Mockito.<ThrowMessage>any())).thenReturn(true);
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    (new ThrowMessageDelegateInvocation(delegateInstance, execution, new ThrowMessage("Name"))).invoke();

    // Assert
    verify(delegateInstance).send(isA(DelegateExecution.class), isA(ThrowMessage.class));
  }
}
