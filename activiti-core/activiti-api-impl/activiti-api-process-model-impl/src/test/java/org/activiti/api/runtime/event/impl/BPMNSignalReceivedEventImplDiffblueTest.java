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
import org.activiti.api.process.model.BPMNSignal;
import org.activiti.api.process.model.events.BPMNSignalEvent;
import org.activiti.api.runtime.model.impl.BPMNSignalImpl;
import org.junit.jupiter.api.Test;

class BPMNSignalReceivedEventImplDiffblueTest {
  /**
   * Method under test: {@link BPMNSignalReceivedEventImpl#getEventType()}
   */
  @Test
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(BPMNSignalEvent.SignalEvents.SIGNAL_RECEIVED, (new BPMNSignalReceivedEventImpl()).getEventType());
  }

  /**
   * Method under test:
   * {@link BPMNSignalReceivedEventImpl#BPMNSignalReceivedEventImpl()}
   */
  @Test
  void testNewBPMNSignalReceivedEventImpl() {
    // Arrange and Act
    BPMNSignalReceivedEventImpl actualBpmnSignalReceivedEventImpl = new BPMNSignalReceivedEventImpl();

    // Assert
    assertNull(actualBpmnSignalReceivedEventImpl.getProcessDefinitionVersion());
    assertNull(actualBpmnSignalReceivedEventImpl.getBusinessKey());
    assertNull(actualBpmnSignalReceivedEventImpl.getParentProcessInstanceId());
    assertNull(actualBpmnSignalReceivedEventImpl.getProcessDefinitionId());
    assertNull(actualBpmnSignalReceivedEventImpl.getProcessDefinitionKey());
    assertNull(actualBpmnSignalReceivedEventImpl.getProcessInstanceId());
    assertNull(actualBpmnSignalReceivedEventImpl.getEntity());
    assertEquals(BPMNSignalEvent.SignalEvents.SIGNAL_RECEIVED, actualBpmnSignalReceivedEventImpl.getEventType());
  }

  /**
   * Method under test:
   * {@link BPMNSignalReceivedEventImpl#BPMNSignalReceivedEventImpl(BPMNSignal)}
   */
  @Test
  void testNewBPMNSignalReceivedEventImpl2() {
    // Arrange
    BPMNSignalImpl entity = new BPMNSignalImpl("42");

    // Act
    BPMNSignalReceivedEventImpl actualBpmnSignalReceivedEventImpl = new BPMNSignalReceivedEventImpl(entity);

    // Assert
    assertNull(actualBpmnSignalReceivedEventImpl.getProcessDefinitionVersion());
    assertNull(actualBpmnSignalReceivedEventImpl.getBusinessKey());
    assertNull(actualBpmnSignalReceivedEventImpl.getParentProcessInstanceId());
    assertNull(actualBpmnSignalReceivedEventImpl.getProcessDefinitionId());
    assertNull(actualBpmnSignalReceivedEventImpl.getProcessDefinitionKey());
    assertNull(actualBpmnSignalReceivedEventImpl.getProcessInstanceId());
    assertEquals(BPMNSignalEvent.SignalEvents.SIGNAL_RECEIVED, actualBpmnSignalReceivedEventImpl.getEventType());
    assertSame(entity, actualBpmnSignalReceivedEventImpl.getEntity());
  }
}
