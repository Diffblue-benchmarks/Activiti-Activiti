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

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultMessageExecutionContext;
import org.activiti.engine.impl.bpmn.parser.factory.MessageExecutionContext;
import org.activiti.engine.impl.delegate.MessagePayloadMappingProvider;
import org.activiti.engine.impl.delegate.ThrowMessageDelegate;
import org.activiti.engine.impl.el.ExpressionManager;
import org.junit.Test;

public class ThrowMessageEndEventActivityBehaviorDiffblueTest {
  /**
   * Test
   * {@link ThrowMessageEndEventActivityBehavior#ThrowMessageEndEventActivityBehavior(EndEvent, MessageEventDefinition, ThrowMessageDelegate, MessageExecutionContext)}.
   * <p>
   * Method under test:
   * {@link ThrowMessageEndEventActivityBehavior#ThrowMessageEndEventActivityBehavior(EndEvent, MessageEventDefinition, ThrowMessageDelegate, MessageExecutionContext)}
   */
  @Test
  public void testNewThrowMessageEndEventActivityBehavior() {
    // Arrange
    EndEvent endEvent = new EndEvent();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    ThrowMessageDelegate delegate = mock(ThrowMessageDelegate.class);
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();
    DefaultMessageExecutionContext messageExecutionContext = new DefaultMessageExecutionContext(messageEventDefinition2,
        new ExpressionManager(), mock(MessagePayloadMappingProvider.class));

    // Act
    ThrowMessageEndEventActivityBehavior actualThrowMessageEndEventActivityBehavior = new ThrowMessageEndEventActivityBehavior(
        endEvent, messageEventDefinition, delegate, messageExecutionContext);

    // Assert
    assertSame(endEvent, actualThrowMessageEndEventActivityBehavior.getEndEvent());
    assertSame(messageEventDefinition, actualThrowMessageEndEventActivityBehavior.getMessageEventDefinition());
    assertSame(messageExecutionContext, actualThrowMessageEndEventActivityBehavior.getMessageExecutionContext());
    assertSame(delegate, actualThrowMessageEndEventActivityBehavior.getDelegate());
  }

  /**
   * Test {@link ThrowMessageEndEventActivityBehavior#getEndEvent()}.
   * <p>
   * Method under test: {@link ThrowMessageEndEventActivityBehavior#getEndEvent()}
   */
  @Test
  public void testGetEndEvent() {
    // Arrange
    EndEvent endEvent = new EndEvent();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    ThrowMessageDelegate delegate = mock(ThrowMessageDelegate.class);
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();

    // Act and Assert
    assertSame(endEvent,
        (new ThrowMessageEndEventActivityBehavior(endEvent, messageEventDefinition, delegate,
            new DefaultMessageExecutionContext(messageEventDefinition2, new ExpressionManager(),
                mock(MessagePayloadMappingProvider.class))))
            .getEndEvent());
  }
}
