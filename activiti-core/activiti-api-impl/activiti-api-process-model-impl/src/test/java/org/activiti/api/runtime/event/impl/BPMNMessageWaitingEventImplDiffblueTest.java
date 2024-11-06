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

class BPMNMessageWaitingEventImplDiffblueTest {
  /**
   * Test {@link BPMNMessageWaitingEventImpl#BPMNMessageWaitingEventImpl()}.
   * <p>
   * Method under test:
   * {@link BPMNMessageWaitingEventImpl#BPMNMessageWaitingEventImpl()}
   */
  @Test
  @DisplayName("Test new BPMNMessageWaitingEventImpl()")
  void testNewBPMNMessageWaitingEventImpl() {
    // Arrange and Act
    BPMNMessageWaitingEventImpl actualBpmnMessageWaitingEventImpl = new BPMNMessageWaitingEventImpl();

    // Assert
    assertNull(actualBpmnMessageWaitingEventImpl.getProcessDefinitionVersion());
    assertNull(actualBpmnMessageWaitingEventImpl.getBusinessKey());
    assertNull(actualBpmnMessageWaitingEventImpl.getParentProcessInstanceId());
    assertNull(actualBpmnMessageWaitingEventImpl.getProcessDefinitionId());
    assertNull(actualBpmnMessageWaitingEventImpl.getProcessDefinitionKey());
    assertNull(actualBpmnMessageWaitingEventImpl.getProcessInstanceId());
    assertNull(actualBpmnMessageWaitingEventImpl.getEntity());
    assertEquals(BPMNMessageEvent.MessageEvents.MESSAGE_WAITING, actualBpmnMessageWaitingEventImpl.getEventType());
  }

  /**
   * Test
   * {@link BPMNMessageWaitingEventImpl#BPMNMessageWaitingEventImpl(BPMNMessage)}.
   * <p>
   * Method under test:
   * {@link BPMNMessageWaitingEventImpl#BPMNMessageWaitingEventImpl(BPMNMessage)}
   */
  @Test
  @DisplayName("Test new BPMNMessageWaitingEventImpl(BPMNMessage)")
  void testNewBPMNMessageWaitingEventImpl2() {
    // Arrange
    BPMNMessageImpl entity = new BPMNMessageImpl("42");

    // Act
    BPMNMessageWaitingEventImpl actualBpmnMessageWaitingEventImpl = new BPMNMessageWaitingEventImpl(entity);

    // Assert
    assertNull(actualBpmnMessageWaitingEventImpl.getProcessDefinitionVersion());
    assertNull(actualBpmnMessageWaitingEventImpl.getBusinessKey());
    assertNull(actualBpmnMessageWaitingEventImpl.getParentProcessInstanceId());
    assertNull(actualBpmnMessageWaitingEventImpl.getProcessDefinitionId());
    assertNull(actualBpmnMessageWaitingEventImpl.getProcessDefinitionKey());
    assertNull(actualBpmnMessageWaitingEventImpl.getProcessInstanceId());
    assertEquals(BPMNMessageEvent.MessageEvents.MESSAGE_WAITING, actualBpmnMessageWaitingEventImpl.getEventType());
    assertSame(entity, actualBpmnMessageWaitingEventImpl.getEntity());
  }

  /**
   * Test {@link BPMNMessageWaitingEventImpl#getEventType()}.
   * <p>
   * Method under test: {@link BPMNMessageWaitingEventImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType()")
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(BPMNMessageEvent.MessageEvents.MESSAGE_WAITING, (new BPMNMessageWaitingEventImpl()).getEventType());
  }
}
