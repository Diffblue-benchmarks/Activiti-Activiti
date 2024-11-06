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
package org.activiti.engine.impl.bpmn.behavior;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.bpmn.model.ThrowEvent;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultMessageExecutionContext;
import org.activiti.engine.impl.delegate.MessagePayloadMappingProvider;
import org.activiti.engine.impl.delegate.ThrowMessage;
import org.activiti.engine.impl.delegate.ThrowMessageDelegate;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.mockito.Mockito;

public class AbstractThrowMessageEventActivityBehaviorDiffblueTest {
  /**
   * Test
   * {@link AbstractThrowMessageEventActivityBehavior#getMessageEventDefinition()}.
   * <p>
   * Method under test:
   * {@link AbstractThrowMessageEventActivityBehavior#getMessageEventDefinition()}
   */
  @Test
  public void testGetMessageEventDefinition() {
    // Arrange
    ThrowEvent throwEvent = new ThrowEvent();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    ThrowMessageDelegate delegate = mock(ThrowMessageDelegate.class);
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();

    // Act and Assert
    assertSame(messageEventDefinition,
        (new IntermediateThrowMessageEventActivityBehavior(throwEvent, messageEventDefinition, delegate,
            new DefaultMessageExecutionContext(messageEventDefinition2, new ExpressionManager(),
                mock(MessagePayloadMappingProvider.class))))
            .getMessageEventDefinition());
  }

  /**
   * Test {@link AbstractThrowMessageEventActivityBehavior#getDelegate()}.
   * <p>
   * Method under test:
   * {@link AbstractThrowMessageEventActivityBehavior#getDelegate()}
   */
  @Test
  public void testGetDelegate() {
    // Arrange
    ThrowMessageDelegate delegate = mock(ThrowMessageDelegate.class);
    when(delegate.send(Mockito.<DelegateExecution>any(), Mockito.<ThrowMessage>any())).thenReturn(true);
    ThrowEvent throwEvent = new ThrowEvent();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();

    // Act
    ThrowMessageDelegate actualDelegate = (new IntermediateThrowMessageEventActivityBehavior(throwEvent,
        messageEventDefinition, delegate, new DefaultMessageExecutionContext(messageEventDefinition2,
            new ExpressionManager(), mock(MessagePayloadMappingProvider.class))))
        .getDelegate();
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    boolean actualSendResult = actualDelegate.send(execution, new ThrowMessage("Name"));

    // Assert
    verify(delegate).send(isA(DelegateExecution.class), isA(ThrowMessage.class));
    assertTrue(actualSendResult);
  }

  /**
   * Test {@link AbstractThrowMessageEventActivityBehavior#getDelegate()}.
   * <p>
   * Method under test:
   * {@link AbstractThrowMessageEventActivityBehavior#getDelegate()}
   */
  @Test
  public void testGetDelegate2() {
    // Arrange
    ThrowMessageDelegate delegate = mock(ThrowMessageDelegate.class);
    when(delegate.send(Mockito.<DelegateExecution>any(), Mockito.<ThrowMessage>any())).thenReturn(false);
    ThrowEvent throwEvent = new ThrowEvent();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();

    // Act
    ThrowMessageDelegate actualDelegate = (new IntermediateThrowMessageEventActivityBehavior(throwEvent,
        messageEventDefinition, delegate, new DefaultMessageExecutionContext(messageEventDefinition2,
            new ExpressionManager(), mock(MessagePayloadMappingProvider.class))))
        .getDelegate();
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    boolean actualSendResult = actualDelegate.send(execution, new ThrowMessage("Name"));

    // Assert
    verify(delegate).send(isA(DelegateExecution.class), isA(ThrowMessage.class));
    assertFalse(actualSendResult);
  }

  /**
   * Test
   * {@link AbstractThrowMessageEventActivityBehavior#getMessageExecutionContext()}.
   * <p>
   * Method under test:
   * {@link AbstractThrowMessageEventActivityBehavior#getMessageExecutionContext()}
   */
  @Test
  public void testGetMessageExecutionContext() {
    // Arrange
    ThrowEvent throwEvent = new ThrowEvent();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    ThrowMessageDelegate delegate = mock(ThrowMessageDelegate.class);
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();
    DefaultMessageExecutionContext messageExecutionContext = new DefaultMessageExecutionContext(messageEventDefinition2,
        new ExpressionManager(), mock(MessagePayloadMappingProvider.class));

    // Act and Assert
    assertSame(messageExecutionContext, (new IntermediateThrowMessageEventActivityBehavior(throwEvent,
        messageEventDefinition, delegate, messageExecutionContext)).getMessageExecutionContext());
  }
}
