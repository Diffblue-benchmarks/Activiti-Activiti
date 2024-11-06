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

class BPMNMessageReceivedEventImplDiffblueTest {
  /**
   * Test {@link BPMNMessageReceivedEventImpl#BPMNMessageReceivedEventImpl()}.
   * <p>
   * Method under test:
   * {@link BPMNMessageReceivedEventImpl#BPMNMessageReceivedEventImpl()}
   */
  @Test
  @DisplayName("Test new BPMNMessageReceivedEventImpl()")
  void testNewBPMNMessageReceivedEventImpl() {
    // Arrange and Act
    BPMNMessageReceivedEventImpl actualBpmnMessageReceivedEventImpl = new BPMNMessageReceivedEventImpl();

    // Assert
    assertNull(actualBpmnMessageReceivedEventImpl.getProcessDefinitionVersion());
    assertNull(actualBpmnMessageReceivedEventImpl.getBusinessKey());
    assertNull(actualBpmnMessageReceivedEventImpl.getParentProcessInstanceId());
    assertNull(actualBpmnMessageReceivedEventImpl.getProcessDefinitionId());
    assertNull(actualBpmnMessageReceivedEventImpl.getProcessDefinitionKey());
    assertNull(actualBpmnMessageReceivedEventImpl.getProcessInstanceId());
    assertNull(actualBpmnMessageReceivedEventImpl.getEntity());
    assertEquals(BPMNMessageEvent.MessageEvents.MESSAGE_RECEIVED, actualBpmnMessageReceivedEventImpl.getEventType());
  }

  /**
   * Test
   * {@link BPMNMessageReceivedEventImpl#BPMNMessageReceivedEventImpl(BPMNMessage)}.
   * <p>
   * Method under test:
   * {@link BPMNMessageReceivedEventImpl#BPMNMessageReceivedEventImpl(BPMNMessage)}
   */
  @Test
  @DisplayName("Test new BPMNMessageReceivedEventImpl(BPMNMessage)")
  void testNewBPMNMessageReceivedEventImpl2() {
    // Arrange
    BPMNMessageImpl entity = new BPMNMessageImpl("42");

    // Act
    BPMNMessageReceivedEventImpl actualBpmnMessageReceivedEventImpl = new BPMNMessageReceivedEventImpl(entity);

    // Assert
    assertNull(actualBpmnMessageReceivedEventImpl.getProcessDefinitionVersion());
    assertNull(actualBpmnMessageReceivedEventImpl.getBusinessKey());
    assertNull(actualBpmnMessageReceivedEventImpl.getParentProcessInstanceId());
    assertNull(actualBpmnMessageReceivedEventImpl.getProcessDefinitionId());
    assertNull(actualBpmnMessageReceivedEventImpl.getProcessDefinitionKey());
    assertNull(actualBpmnMessageReceivedEventImpl.getProcessInstanceId());
    assertEquals(BPMNMessageEvent.MessageEvents.MESSAGE_RECEIVED, actualBpmnMessageReceivedEventImpl.getEventType());
    assertSame(entity, actualBpmnMessageReceivedEventImpl.getEntity());
  }

  /**
   * Test {@link BPMNMessageReceivedEventImpl#getEventType()}.
   * <p>
   * Method under test: {@link BPMNMessageReceivedEventImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType()")
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(BPMNMessageEvent.MessageEvents.MESSAGE_RECEIVED, (new BPMNMessageReceivedEventImpl()).getEventType());
  }
}
