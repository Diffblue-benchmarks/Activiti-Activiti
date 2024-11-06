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
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Optional;
import org.activiti.api.process.model.BPMNMessage;
import org.activiti.api.process.model.events.BPMNMessageEvent;
import org.activiti.api.process.model.events.BPMNMessageReceivedEvent;
import org.activiti.api.process.model.payloads.MessageEventPayload;
import org.activiti.api.runtime.event.impl.BPMNMessageReceivedEventImpl;
import org.activiti.api.runtime.model.impl.BPMNMessageImpl;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.ActivitiMessageEvent;
import org.activiti.engine.delegate.event.impl.ActivitiMessageEventImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ToMessageReceivedConverterDiffblueTest {
  /**
   * Test {@link ToMessageReceivedConverter#from(ActivitiMessageEvent)} with
   * {@code ActivitiMessageEvent}.
   * <ul>
   *   <li>Then {@link Optional#get()} return
   * {@link BPMNMessageReceivedEventImpl}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ToMessageReceivedConverter#from(ActivitiMessageEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiMessageEvent) with 'ActivitiMessageEvent'; then get() return BPMNMessageReceivedEventImpl")
  void testFromWithActivitiMessageEvent_thenGetReturnBPMNMessageReceivedEventImpl() {
    // Arrange
    ToMessageReceivedConverter toMessageReceivedConverter = new ToMessageReceivedConverter(new BPMNMessageConverter());

    // Act
    Optional<BPMNMessageReceivedEvent> actualFromResult = toMessageReceivedConverter
        .from(new ActivitiMessageEventImpl(ActivitiEventType.ENTITY_CREATED));

    // Assert
    BPMNMessageReceivedEvent getResult = actualFromResult.get();
    assertTrue(getResult instanceof BPMNMessageReceivedEventImpl);
    BPMNMessage entity = getResult.getEntity();
    assertTrue(entity instanceof BPMNMessageImpl);
    assertNull(getResult.getProcessDefinitionVersion());
    assertNull(getResult.getBusinessKey());
    assertNull(getResult.getParentProcessInstanceId());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionKey());
    assertNull(getResult.getProcessInstanceId());
    assertNull(entity.getElementId());
    assertNull(entity.getProcessDefinitionId());
    assertNull(entity.getProcessInstanceId());
    MessageEventPayload messagePayload = entity.getMessagePayload();
    assertNull(messagePayload.getBusinessKey());
    assertNull(messagePayload.getCorrelationKey());
    assertNull(messagePayload.getName());
    assertNull(messagePayload.getVariables());
    assertEquals(BPMNMessageEvent.MessageEvents.MESSAGE_RECEIVED, getResult.getEventType());
    assertTrue(actualFromResult.isPresent());
  }
}
