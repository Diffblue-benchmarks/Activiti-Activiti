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
package org.activiti.engine.impl.bpmn.parser.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Message;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.junit.Test;
import org.mockito.Mockito;

public class MessageEventDefinitionParseHandlerDiffblueTest {
  /**
   * Test
   * {@link MessageEventDefinitionParseHandler#executeParse(BpmnParse, MessageEventDefinition)}
   * with {@code BpmnParse}, {@code MessageEventDefinition}.
   * <p>
   * Method under test:
   * {@link MessageEventDefinitionParseHandler#executeParse(BpmnParse, MessageEventDefinition)}
   */
  @Test
  public void testExecuteParseWithBpmnParseMessageEventDefinition() {
    // Arrange
    MessageEventDefinitionParseHandler messageEventDefinitionParseHandler = new MessageEventDefinitionParseHandler();
    BpmnParse bpmnParse = mock(BpmnParse.class);
    when(bpmnParse.getActivityBehaviorFactory()).thenReturn(new DefaultActivityBehaviorFactory());
    when(bpmnParse.getCurrentFlowElement()).thenReturn(new BoundaryEvent());
    when(bpmnParse.getBpmnModel()).thenReturn(new BpmnModel());
    MessageEventDefinition messageDefinition = new MessageEventDefinition();

    // Act
    messageEventDefinitionParseHandler.executeParse(bpmnParse, messageDefinition);

    // Assert
    verify(bpmnParse).getActivityBehaviorFactory();
    verify(bpmnParse).getBpmnModel();
    verify(bpmnParse, atLeast(1)).getCurrentFlowElement();
    assertNull(messageDefinition.getMessageRef());
  }

  /**
   * Test
   * {@link MessageEventDefinitionParseHandler#executeParse(BpmnParse, MessageEventDefinition)}
   * with {@code BpmnParse}, {@code MessageEventDefinition}.
   * <p>
   * Method under test:
   * {@link MessageEventDefinitionParseHandler#executeParse(BpmnParse, MessageEventDefinition)}
   */
  @Test
  public void testExecuteParseWithBpmnParseMessageEventDefinition2() {
    // Arrange
    MessageEventDefinitionParseHandler messageEventDefinitionParseHandler = new MessageEventDefinitionParseHandler();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    Message.Builder builderResult = Message.builder();
    Message.Builder attributesResult = builderResult.attributes(new HashMap<>());
    Message buildResult = attributesResult.extensionElements(new HashMap<>())
        .id("42")
        .itemRef("Item Ref")
        .name("Name")
        .xmlColumnNumber(10)
        .xmlRowNumber(10)
        .build();
    when(bpmnModel.getMessage(Mockito.<String>any())).thenReturn(buildResult);
    when(bpmnModel.containsMessageId(Mockito.<String>any())).thenReturn(true);
    BpmnParse bpmnParse = mock(BpmnParse.class);
    when(bpmnParse.getActivityBehaviorFactory()).thenReturn(new DefaultActivityBehaviorFactory());
    when(bpmnParse.getCurrentFlowElement()).thenReturn(new BoundaryEvent());
    when(bpmnParse.getBpmnModel()).thenReturn(bpmnModel);
    MessageEventDefinition messageDefinition = new MessageEventDefinition();

    // Act
    messageEventDefinitionParseHandler.executeParse(bpmnParse, messageDefinition);

    // Assert
    verify(bpmnModel).containsMessageId(isNull());
    verify(bpmnModel).getMessage(isNull());
    verify(bpmnParse).getActivityBehaviorFactory();
    verify(bpmnParse).getBpmnModel();
    verify(bpmnParse, atLeast(1)).getCurrentFlowElement();
    assertEquals("Name", messageDefinition.getMessageRef());
  }

  /**
   * Test
   * {@link MessageEventDefinitionParseHandler#executeParse(BpmnParse, MessageEventDefinition)}
   * with {@code BpmnParse}, {@code MessageEventDefinition}.
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MessageEventDefinitionParseHandler#executeParse(BpmnParse, MessageEventDefinition)}
   */
  @Test
  public void testExecuteParseWithBpmnParseMessageEventDefinition_givenAdhocSubProcess() {
    // Arrange
    MessageEventDefinitionParseHandler messageEventDefinitionParseHandler = new MessageEventDefinitionParseHandler();
    BpmnParse bpmnParse = mock(BpmnParse.class);
    when(bpmnParse.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());
    when(bpmnParse.getBpmnModel()).thenReturn(new BpmnModel());

    // Act
    messageEventDefinitionParseHandler.executeParse(bpmnParse, new MessageEventDefinition());

    // Assert that nothing has changed
    verify(bpmnParse).getBpmnModel();
    verify(bpmnParse, atLeast(1)).getCurrentFlowElement();
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of
   * {@link MessageEventDefinitionParseHandler}
   *   <li>{@link MessageEventDefinitionParseHandler#getHandledType()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    Class<? extends BaseElement> actualHandledType = (new MessageEventDefinitionParseHandler()).getHandledType();

    // Assert
    Class<MessageEventDefinition> expectedHandledType = MessageEventDefinition.class;
    assertEquals(expectedHandledType, actualHandledType);
  }
}
