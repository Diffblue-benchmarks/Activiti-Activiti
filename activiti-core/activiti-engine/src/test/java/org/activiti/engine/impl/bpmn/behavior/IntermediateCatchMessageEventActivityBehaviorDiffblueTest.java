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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultMessageExecutionContext;
import org.activiti.engine.impl.bpmn.parser.factory.MessageExecutionContext;
import org.activiti.engine.impl.delegate.MessagePayloadMappingProvider;
import org.activiti.engine.impl.el.ExpressionManager;
import org.junit.Test;

public class IntermediateCatchMessageEventActivityBehaviorDiffblueTest {
  /**
   * Test
   * {@link IntermediateCatchMessageEventActivityBehavior#IntermediateCatchMessageEventActivityBehavior(MessageEventDefinition, MessageExecutionContext)}.
   * <p>
   * Method under test:
   * {@link IntermediateCatchMessageEventActivityBehavior#IntermediateCatchMessageEventActivityBehavior(MessageEventDefinition, MessageExecutionContext)}
   */
  @Test
  public void testNewIntermediateCatchMessageEventActivityBehavior() {
    // Arrange
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();
    DefaultMessageExecutionContext messageExecutionContext = new DefaultMessageExecutionContext(messageEventDefinition2,
        new ExpressionManager(), mock(MessagePayloadMappingProvider.class));

    // Act
    IntermediateCatchMessageEventActivityBehavior actualIntermediateCatchMessageEventActivityBehavior = new IntermediateCatchMessageEventActivityBehavior(
        messageEventDefinition, messageExecutionContext);

    // Assert
    assertNull(actualIntermediateCatchMessageEventActivityBehavior.getMultiInstanceActivityBehavior());
    assertFalse(actualIntermediateCatchMessageEventActivityBehavior.hasLoopCharacteristics());
    assertFalse(actualIntermediateCatchMessageEventActivityBehavior.hasMultiInstanceCharacteristics());
    assertSame(messageEventDefinition, actualIntermediateCatchMessageEventActivityBehavior.getMessageEventDefinition());
    assertSame(messageExecutionContext,
        actualIntermediateCatchMessageEventActivityBehavior.getMessageExecutionContext());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link IntermediateCatchMessageEventActivityBehavior#getMessageEventDefinition()}
   *   <li>
   * {@link IntermediateCatchMessageEventActivityBehavior#getMessageExecutionContext()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();
    IntermediateCatchMessageEventActivityBehavior intermediateCatchMessageEventActivityBehavior = new IntermediateCatchMessageEventActivityBehavior(
        messageEventDefinition, new DefaultMessageExecutionContext(messageEventDefinition2, new ExpressionManager(),
            mock(MessagePayloadMappingProvider.class)));

    // Act
    MessageEventDefinition actualMessageEventDefinition = intermediateCatchMessageEventActivityBehavior
        .getMessageEventDefinition();

    // Assert
    assertSame(intermediateCatchMessageEventActivityBehavior.messageEventDefinition, actualMessageEventDefinition);
    assertSame(intermediateCatchMessageEventActivityBehavior.messageExecutionContext,
        intermediateCatchMessageEventActivityBehavior.getMessageExecutionContext());
  }
}
