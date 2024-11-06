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
package org.activiti.api.runtime.event.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.activiti.api.process.model.BPMNMessage;
import org.activiti.api.process.model.events.BPMNMessageEvent;
import org.activiti.api.runtime.model.impl.BPMNMessageImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BPMNMessageSentEventImplDiffblueTest {
  /**
   * Test {@link BPMNMessageSentEventImpl#BPMNMessageSentEventImpl()}.
   * <p>
   * Method under test:
   * {@link BPMNMessageSentEventImpl#BPMNMessageSentEventImpl()}
   */
  @Test
  @DisplayName("Test new BPMNMessageSentEventImpl()")
  void testNewBPMNMessageSentEventImpl() {
    // Arrange and Act
    BPMNMessageSentEventImpl actualBpmnMessageSentEventImpl = new BPMNMessageSentEventImpl();

    // Assert
    assertNull(actualBpmnMessageSentEventImpl.getProcessDefinitionVersion());
    assertNull(actualBpmnMessageSentEventImpl.getBusinessKey());
    assertNull(actualBpmnMessageSentEventImpl.getParentProcessInstanceId());
    assertNull(actualBpmnMessageSentEventImpl.getProcessDefinitionId());
    assertNull(actualBpmnMessageSentEventImpl.getProcessDefinitionKey());
    assertNull(actualBpmnMessageSentEventImpl.getProcessInstanceId());
    assertNull(actualBpmnMessageSentEventImpl.getEntity());
    assertEquals(BPMNMessageEvent.MessageEvents.MESSAGE_SENT, actualBpmnMessageSentEventImpl.getEventType());
  }

  /**
   * Test {@link BPMNMessageSentEventImpl#BPMNMessageSentEventImpl(BPMNMessage)}.
   * <p>
   * Method under test:
   * {@link BPMNMessageSentEventImpl#BPMNMessageSentEventImpl(BPMNMessage)}
   */
  @Test
  @DisplayName("Test new BPMNMessageSentEventImpl(BPMNMessage)")
  void testNewBPMNMessageSentEventImpl2() {
    // Arrange
    BPMNMessageImpl entity = new BPMNMessageImpl("42");

    // Act
    BPMNMessageSentEventImpl actualBpmnMessageSentEventImpl = new BPMNMessageSentEventImpl(entity);

    // Assert
    assertNull(actualBpmnMessageSentEventImpl.getProcessDefinitionVersion());
    assertNull(actualBpmnMessageSentEventImpl.getBusinessKey());
    assertNull(actualBpmnMessageSentEventImpl.getParentProcessInstanceId());
    assertNull(actualBpmnMessageSentEventImpl.getProcessDefinitionId());
    assertNull(actualBpmnMessageSentEventImpl.getProcessDefinitionKey());
    assertNull(actualBpmnMessageSentEventImpl.getProcessInstanceId());
    assertEquals(BPMNMessageEvent.MessageEvents.MESSAGE_SENT, actualBpmnMessageSentEventImpl.getEventType());
    assertSame(entity, actualBpmnMessageSentEventImpl.getEntity());
  }

  /**
   * Test {@link BPMNMessageSentEventImpl#getEventType()}.
   * <p>
   * Method under test: {@link BPMNMessageSentEventImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType()")
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(BPMNMessageEvent.MessageEvents.MESSAGE_SENT, (new BPMNMessageSentEventImpl()).getEventType());
  }
}
