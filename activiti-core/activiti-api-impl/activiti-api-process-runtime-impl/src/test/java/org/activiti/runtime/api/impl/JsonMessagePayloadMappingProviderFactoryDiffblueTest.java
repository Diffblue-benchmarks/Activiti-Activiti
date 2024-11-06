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
package org.activiti.runtime.api.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.Event;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.engine.impl.bpmn.behavior.VariablesCalculator;
import org.activiti.engine.impl.delegate.MessagePayloadMappingProvider;
import org.activiti.engine.impl.el.ExpressionManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {JsonMessagePayloadMappingProviderFactory.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class JsonMessagePayloadMappingProviderFactoryDiffblueTest {
  @Autowired
  private JsonMessagePayloadMappingProviderFactory jsonMessagePayloadMappingProviderFactory;

  @MockBean
  private VariablesCalculator variablesCalculator;

  /**
   * Test
   * {@link JsonMessagePayloadMappingProviderFactory#create(Event, MessageEventDefinition, ExpressionManager)}.
   * <ul>
   *   <li>Then return MessageEventDefinition is {@link MessageEventDefinition}
   * (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonMessagePayloadMappingProviderFactory#create(Event, MessageEventDefinition, ExpressionManager)}
   */
  @Test
  @DisplayName("Test create(Event, MessageEventDefinition, ExpressionManager); then return MessageEventDefinition is MessageEventDefinition (default constructor)")
  void testCreate_thenReturnMessageEventDefinitionIsMessageEventDefinition() {
    // Arrange
    BoundaryEvent bpmnEvent = mock(BoundaryEvent.class);
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    ExpressionManager expressionManager = new ExpressionManager();

    // Act
    MessagePayloadMappingProvider actualCreateResult = jsonMessagePayloadMappingProviderFactory.create(bpmnEvent,
        messageEventDefinition, expressionManager);

    // Assert
    assertTrue(actualCreateResult instanceof JsonMessagePayloadMappingProvider);
    assertSame(messageEventDefinition,
        ((JsonMessagePayloadMappingProvider) actualCreateResult).getMessageEventDefinition());
    assertSame(expressionManager, ((JsonMessagePayloadMappingProvider) actualCreateResult).getExpressionManager());
    assertSame(bpmnEvent, ((JsonMessagePayloadMappingProvider) actualCreateResult).getBpmnEvent());
  }

  /**
   * Test
   * {@link JsonMessagePayloadMappingProviderFactory#create(Event, MessageEventDefinition, ExpressionManager)}.
   * <ul>
   *   <li>When {@link BoundaryEvent} (default constructor).</li>
   *   <li>Then return BpmnEvent is {@link BoundaryEvent} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonMessagePayloadMappingProviderFactory#create(Event, MessageEventDefinition, ExpressionManager)}
   */
  @Test
  @DisplayName("Test create(Event, MessageEventDefinition, ExpressionManager); when BoundaryEvent (default constructor); then return BpmnEvent is BoundaryEvent (default constructor)")
  void testCreate_whenBoundaryEvent_thenReturnBpmnEventIsBoundaryEvent() {
    // Arrange
    BoundaryEvent bpmnEvent = new BoundaryEvent();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();

    // Act
    MessagePayloadMappingProvider actualCreateResult = jsonMessagePayloadMappingProviderFactory.create(bpmnEvent,
        messageEventDefinition, new ExpressionManager());

    // Assert
    assertTrue(actualCreateResult instanceof JsonMessagePayloadMappingProvider);
    assertSame(bpmnEvent, ((JsonMessagePayloadMappingProvider) actualCreateResult).getBpmnEvent());
  }
}
