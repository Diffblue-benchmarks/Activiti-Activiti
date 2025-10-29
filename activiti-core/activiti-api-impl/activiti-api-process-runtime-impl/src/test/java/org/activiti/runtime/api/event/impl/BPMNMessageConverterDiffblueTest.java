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
package org.activiti.runtime.api.event.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import java.util.Map;
import org.activiti.api.process.model.payloads.MessageEventPayload;
import org.activiti.api.runtime.model.impl.BPMNMessageImpl;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.ActivitiMessageEvent;
import org.activiti.engine.delegate.event.impl.ActivitiMessageEventImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {BPMNMessageConverter.class})
@ExtendWith(SpringExtension.class)
class BPMNMessageConverterDiffblueTest {
  @Autowired
  private BPMNMessageConverter bPMNMessageConverter;

  /**
   * Method under test:
   * {@link BPMNMessageConverter#convertToBPMNMessage(ActivitiMessageEvent)}
   */
  @Test
  void testConvertToBPMNMessage() {
    // Arrange and Act
    BPMNMessageImpl actualConvertToBPMNMessageResult = bPMNMessageConverter
        .convertToBPMNMessage(new ActivitiMessageEventImpl(ActivitiEventType.ENTITY_CREATED));

    // Assert
    MessageEventPayload messagePayload = actualConvertToBPMNMessageResult.getMessagePayload();
    assertNull(messagePayload.getBusinessKey());
    assertNull(messagePayload.getCorrelationKey());
    assertNull(messagePayload.getName());
    assertNull(actualConvertToBPMNMessageResult.getElementId());
    assertNull(actualConvertToBPMNMessageResult.getProcessDefinitionId());
    assertNull(actualConvertToBPMNMessageResult.getProcessInstanceId());
    assertNull(messagePayload.getVariables());
  }

  /**
   * Method under test:
   * {@link BPMNMessageConverter#convertToBPMNMessage(ActivitiMessageEvent)}
   */
  @Test
  void testConvertToBPMNMessage2() {
    // Arrange
    ActivitiMessageEvent internalEvent = mock(ActivitiMessageEvent.class);
    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    when(internalEvent.getMessageData()).thenReturn(objectObjectMap);
    when(internalEvent.getActivityId()).thenReturn("42");
    when(internalEvent.getProcessDefinitionId()).thenReturn("42");
    when(internalEvent.getProcessInstanceId()).thenReturn("42");
    when(internalEvent.getMessageBusinessKey()).thenReturn("Message Business Key");
    when(internalEvent.getMessageCorrelationKey()).thenReturn("Message Correlation Key");
    when(internalEvent.getMessageName()).thenReturn("Message Name");

    // Act
    BPMNMessageImpl actualConvertToBPMNMessageResult = bPMNMessageConverter.convertToBPMNMessage(internalEvent);

    // Assert
    verify(internalEvent).getActivityId();
    verify(internalEvent).getProcessDefinitionId();
    verify(internalEvent).getProcessInstanceId();
    verify(internalEvent).getMessageBusinessKey();
    verify(internalEvent).getMessageCorrelationKey();
    verify(internalEvent).getMessageData();
    verify(internalEvent).getMessageName();
    assertEquals("42", actualConvertToBPMNMessageResult.getElementId());
    assertEquals("42", actualConvertToBPMNMessageResult.getProcessDefinitionId());
    assertEquals("42", actualConvertToBPMNMessageResult.getProcessInstanceId());
    MessageEventPayload messagePayload = actualConvertToBPMNMessageResult.getMessagePayload();
    assertEquals("Message Business Key", messagePayload.getBusinessKey());
    assertEquals("Message Correlation Key", messagePayload.getCorrelationKey());
    assertEquals("Message Name", messagePayload.getName());
    Map<String, Object> variables = messagePayload.getVariables();
    assertTrue(variables.isEmpty());
    assertSame(objectObjectMap, variables);
  }
}
