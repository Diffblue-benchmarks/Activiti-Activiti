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
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.bpmn.model.ThrowEvent;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultMessageExecutionContext;
import org.activiti.engine.impl.bpmn.parser.factory.MessageExecutionContext;
import org.activiti.engine.impl.delegate.MessagePayloadMappingProvider;
import org.activiti.engine.impl.delegate.ThrowMessageDelegate;
import org.activiti.engine.impl.el.ExpressionManager;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class IntermediateThrowMessageEventActivityBehaviorDiffblueTest {
  /**
   * Test {@link
   * IntermediateThrowMessageEventActivityBehavior#IntermediateThrowMessageEventActivityBehavior(ThrowEvent,
   * MessageEventDefinition, ThrowMessageDelegate, MessageExecutionContext)}.
   *
   * <p>Method under test: {@link
   * IntermediateThrowMessageEventActivityBehavior#IntermediateThrowMessageEventActivityBehavior(ThrowEvent,
   * MessageEventDefinition, ThrowMessageDelegate, MessageExecutionContext)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void IntermediateThrowMessageEventActivityBehavior.<init>(ThrowEvent, MessageEventDefinition, ThrowMessageDelegate, MessageExecutionContext)"
  })
  public void testNewIntermediateThrowMessageEventActivityBehavior() {
    // Arrange
    ThrowEvent throwEvent = new ThrowEvent();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    ThrowMessageDelegate delegate = mock(ThrowMessageDelegate.class);
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();
    DefaultMessageExecutionContext messageExecutionContext =
        new DefaultMessageExecutionContext(
            messageEventDefinition2,
            new ExpressionManager(),
            mock(MessagePayloadMappingProvider.class));

    // Act
    IntermediateThrowMessageEventActivityBehavior
        actualIntermediateThrowMessageEventActivityBehavior =
            new IntermediateThrowMessageEventActivityBehavior(
                throwEvent, messageEventDefinition, delegate, messageExecutionContext);

    // Assert
    MessageExecutionContext messageExecutionContext2 =
        actualIntermediateThrowMessageEventActivityBehavior.getMessageExecutionContext();
    assertTrue(messageExecutionContext2 instanceof DefaultMessageExecutionContext);
    assertSame(
        messageEventDefinition,
        actualIntermediateThrowMessageEventActivityBehavior.getMessageEventDefinition());
    assertSame(throwEvent, actualIntermediateThrowMessageEventActivityBehavior.getThrowEvent());
    assertSame(messageExecutionContext, messageExecutionContext2);
    assertSame(delegate, actualIntermediateThrowMessageEventActivityBehavior.getDelegate());
  }

  /**
   * Test {@link IntermediateThrowMessageEventActivityBehavior#getThrowEvent()}.
   *
   * <p>Method under test: {@link IntermediateThrowMessageEventActivityBehavior#getThrowEvent()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ThrowEvent IntermediateThrowMessageEventActivityBehavior.getThrowEvent()"})
  public void testGetThrowEvent() {
    // Arrange
    ThrowEvent throwEvent = new ThrowEvent();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    ThrowMessageDelegate delegate = mock(ThrowMessageDelegate.class);
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();
    DefaultMessageExecutionContext messageExecutionContext =
        new DefaultMessageExecutionContext(
            messageEventDefinition2,
            new ExpressionManager(),
            mock(MessagePayloadMappingProvider.class));

    IntermediateThrowMessageEventActivityBehavior intermediateThrowMessageEventActivityBehavior =
        new IntermediateThrowMessageEventActivityBehavior(
            throwEvent, messageEventDefinition, delegate, messageExecutionContext);

    // Act and Assert
    assertSame(throwEvent, intermediateThrowMessageEventActivityBehavior.getThrowEvent());
  }
}
