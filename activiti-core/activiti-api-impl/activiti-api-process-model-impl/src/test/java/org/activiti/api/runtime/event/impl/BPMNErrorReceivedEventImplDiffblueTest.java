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
import org.activiti.api.process.model.BPMNError;
import org.activiti.api.process.model.events.BPMNErrorReceivedEvent;
import org.activiti.api.runtime.model.impl.BPMNErrorImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BPMNErrorReceivedEventImplDiffblueTest {
  /**
   * Test {@link BPMNErrorReceivedEventImpl#BPMNErrorReceivedEventImpl()}.
   * <p>
   * Method under test:
   * {@link BPMNErrorReceivedEventImpl#BPMNErrorReceivedEventImpl()}
   */
  @Test
  @DisplayName("Test new BPMNErrorReceivedEventImpl()")
  void testNewBPMNErrorReceivedEventImpl() {
    // Arrange and Act
    BPMNErrorReceivedEventImpl actualBpmnErrorReceivedEventImpl = new BPMNErrorReceivedEventImpl();

    // Assert
    assertNull(actualBpmnErrorReceivedEventImpl.getProcessDefinitionVersion());
    assertNull(actualBpmnErrorReceivedEventImpl.getBusinessKey());
    assertNull(actualBpmnErrorReceivedEventImpl.getParentProcessInstanceId());
    assertNull(actualBpmnErrorReceivedEventImpl.getProcessDefinitionId());
    assertNull(actualBpmnErrorReceivedEventImpl.getProcessDefinitionKey());
    assertNull(actualBpmnErrorReceivedEventImpl.getProcessInstanceId());
    assertNull(actualBpmnErrorReceivedEventImpl.getEntity());
    assertEquals(BPMNErrorReceivedEvent.ErrorEvents.ERROR_RECEIVED, actualBpmnErrorReceivedEventImpl.getEventType());
  }

  /**
   * Test
   * {@link BPMNErrorReceivedEventImpl#BPMNErrorReceivedEventImpl(BPMNError)}.
   * <p>
   * Method under test:
   * {@link BPMNErrorReceivedEventImpl#BPMNErrorReceivedEventImpl(BPMNError)}
   */
  @Test
  @DisplayName("Test new BPMNErrorReceivedEventImpl(BPMNError)")
  void testNewBPMNErrorReceivedEventImpl2() {
    // Arrange
    BPMNErrorImpl entity = new BPMNErrorImpl("42");

    // Act
    BPMNErrorReceivedEventImpl actualBpmnErrorReceivedEventImpl = new BPMNErrorReceivedEventImpl(entity);

    // Assert
    assertNull(actualBpmnErrorReceivedEventImpl.getProcessDefinitionVersion());
    assertNull(actualBpmnErrorReceivedEventImpl.getBusinessKey());
    assertNull(actualBpmnErrorReceivedEventImpl.getParentProcessInstanceId());
    assertNull(actualBpmnErrorReceivedEventImpl.getProcessDefinitionId());
    assertNull(actualBpmnErrorReceivedEventImpl.getProcessDefinitionKey());
    assertNull(actualBpmnErrorReceivedEventImpl.getProcessInstanceId());
    assertEquals(BPMNErrorReceivedEvent.ErrorEvents.ERROR_RECEIVED, actualBpmnErrorReceivedEventImpl.getEventType());
    assertSame(entity, actualBpmnErrorReceivedEventImpl.getEntity());
  }

  /**
   * Test {@link BPMNErrorReceivedEventImpl#getEventType()}.
   * <p>
   * Method under test: {@link BPMNErrorReceivedEventImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType()")
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(BPMNErrorReceivedEvent.ErrorEvents.ERROR_RECEIVED, (new BPMNErrorReceivedEventImpl()).getEventType());
  }
}
