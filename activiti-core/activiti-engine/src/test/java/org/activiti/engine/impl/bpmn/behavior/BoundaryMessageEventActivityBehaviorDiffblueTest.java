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
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultMessageExecutionContext;
import org.activiti.engine.impl.bpmn.parser.factory.MessageExecutionContext;
import org.activiti.engine.impl.delegate.MessagePayloadMappingProvider;
import org.activiti.engine.impl.el.ExpressionManager;
import org.junit.Test;

public class BoundaryMessageEventActivityBehaviorDiffblueTest {
  /**
   * Test
   * {@link BoundaryMessageEventActivityBehavior#BoundaryMessageEventActivityBehavior(MessageEventDefinition, boolean, MessageExecutionContext)}.
   * <p>
   * Method under test:
   * {@link BoundaryMessageEventActivityBehavior#BoundaryMessageEventActivityBehavior(MessageEventDefinition, boolean, MessageExecutionContext)}
   */
  @Test
  public void testNewBoundaryMessageEventActivityBehavior() {
    // Arrange
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();
    DefaultMessageExecutionContext messageExecutionContext = new DefaultMessageExecutionContext(messageEventDefinition2,
        new ExpressionManager(), mock(MessagePayloadMappingProvider.class));

    // Act
    BoundaryMessageEventActivityBehavior actualBoundaryMessageEventActivityBehavior = new BoundaryMessageEventActivityBehavior(
        messageEventDefinition, true, messageExecutionContext);

    // Assert
    assertTrue(actualBoundaryMessageEventActivityBehavior.isInterrupting());
    assertSame(messageEventDefinition, actualBoundaryMessageEventActivityBehavior.getMessageEventDefinition());
    assertSame(messageExecutionContext, actualBoundaryMessageEventActivityBehavior.getMessageExecutionContext());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link BoundaryMessageEventActivityBehavior#getMessageEventDefinition()}
   *   <li>{@link BoundaryMessageEventActivityBehavior#getMessageExecutionContext()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();
    DefaultMessageExecutionContext messageExecutionContext = new DefaultMessageExecutionContext(messageEventDefinition2,
        new ExpressionManager(), mock(MessagePayloadMappingProvider.class));

    BoundaryMessageEventActivityBehavior boundaryMessageEventActivityBehavior = new BoundaryMessageEventActivityBehavior(
        messageEventDefinition, true, messageExecutionContext);

    // Act
    MessageEventDefinition actualMessageEventDefinition = boundaryMessageEventActivityBehavior
        .getMessageEventDefinition();

    // Assert
    assertSame(messageEventDefinition, actualMessageEventDefinition);
    assertSame(messageExecutionContext, boundaryMessageEventActivityBehavior.getMessageExecutionContext());
  }
}
