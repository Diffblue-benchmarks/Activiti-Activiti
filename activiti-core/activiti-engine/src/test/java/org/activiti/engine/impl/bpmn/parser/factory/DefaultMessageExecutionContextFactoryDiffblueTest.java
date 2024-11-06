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
package org.activiti.engine.impl.bpmn.parser.factory;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.engine.impl.delegate.MessagePayloadMappingProvider;
import org.activiti.engine.impl.el.ExpressionManager;
import org.junit.Test;

public class DefaultMessageExecutionContextFactoryDiffblueTest {
  /**
   * Test
   * {@link DefaultMessageExecutionContextFactory#create(MessageEventDefinition, MessagePayloadMappingProvider, ExpressionManager)}.
   * <p>
   * Method under test:
   * {@link DefaultMessageExecutionContextFactory#create(MessageEventDefinition, MessagePayloadMappingProvider, ExpressionManager)}
   */
  @Test
  public void testCreate() {
    // Arrange
    DefaultMessageExecutionContextFactory defaultMessageExecutionContextFactory = new DefaultMessageExecutionContextFactory();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    MessagePayloadMappingProvider messagePayloadMappingProvider = mock(MessagePayloadMappingProvider.class);
    ExpressionManager expressionManager = new ExpressionManager();

    // Act
    MessageExecutionContext actualCreateResult = defaultMessageExecutionContextFactory.create(messageEventDefinition,
        messagePayloadMappingProvider, expressionManager);

    // Assert
    assertTrue(actualCreateResult instanceof DefaultMessageExecutionContext);
    assertSame(expressionManager, ((DefaultMessageExecutionContext) actualCreateResult).getExpressionManager());
    assertSame(messagePayloadMappingProvider,
        ((DefaultMessageExecutionContext) actualCreateResult).getMessagePayloadMappingProvider());
  }
}
