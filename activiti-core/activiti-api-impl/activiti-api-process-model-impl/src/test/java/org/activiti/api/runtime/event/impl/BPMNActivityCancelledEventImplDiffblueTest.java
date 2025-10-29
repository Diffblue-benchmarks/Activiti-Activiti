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
import org.activiti.api.process.model.BPMNActivity;
import org.activiti.api.process.model.events.BPMNActivityEvent;
import org.activiti.api.runtime.model.impl.BPMNActivityImpl;
import org.junit.jupiter.api.Test;

class BPMNActivityCancelledEventImplDiffblueTest {
  /**
   * Method under test: {@link BPMNActivityCancelledEventImpl#getEventType()}
   */
  @Test
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(BPMNActivityEvent.ActivityEvents.ACTIVITY_CANCELLED,
        (new BPMNActivityCancelledEventImpl()).getEventType());
  }

  /**
   * Method under test:
   * {@link BPMNActivityCancelledEventImpl#BPMNActivityCancelledEventImpl()}
   */
  @Test
  void testNewBPMNActivityCancelledEventImpl() {
    // Arrange and Act
    BPMNActivityCancelledEventImpl actualBpmnActivityCancelledEventImpl = new BPMNActivityCancelledEventImpl();

    // Assert
    assertNull(actualBpmnActivityCancelledEventImpl.getProcessDefinitionVersion());
    assertNull(actualBpmnActivityCancelledEventImpl.getBusinessKey());
    assertNull(actualBpmnActivityCancelledEventImpl.getParentProcessInstanceId());
    assertNull(actualBpmnActivityCancelledEventImpl.getProcessDefinitionId());
    assertNull(actualBpmnActivityCancelledEventImpl.getProcessDefinitionKey());
    assertNull(actualBpmnActivityCancelledEventImpl.getProcessInstanceId());
    assertNull(actualBpmnActivityCancelledEventImpl.getEntity());
    assertEquals(BPMNActivityEvent.ActivityEvents.ACTIVITY_CANCELLED,
        actualBpmnActivityCancelledEventImpl.getEventType());
  }

  /**
   * Method under test:
   * {@link BPMNActivityCancelledEventImpl#BPMNActivityCancelledEventImpl(BPMNActivity)}
   */
  @Test
  void testNewBPMNActivityCancelledEventImpl2() {
    // Arrange
    BPMNActivityImpl entity = new BPMNActivityImpl("42", "Activity Name", "Activity Type");

    // Act
    BPMNActivityCancelledEventImpl actualBpmnActivityCancelledEventImpl = new BPMNActivityCancelledEventImpl(entity);

    // Assert
    assertNull(actualBpmnActivityCancelledEventImpl.getProcessDefinitionVersion());
    assertNull(actualBpmnActivityCancelledEventImpl.getBusinessKey());
    assertNull(actualBpmnActivityCancelledEventImpl.getParentProcessInstanceId());
    assertNull(actualBpmnActivityCancelledEventImpl.getProcessDefinitionId());
    assertNull(actualBpmnActivityCancelledEventImpl.getProcessDefinitionKey());
    assertNull(actualBpmnActivityCancelledEventImpl.getProcessInstanceId());
    assertEquals(BPMNActivityEvent.ActivityEvents.ACTIVITY_CANCELLED,
        actualBpmnActivityCancelledEventImpl.getEventType());
    assertSame(entity, actualBpmnActivityCancelledEventImpl.getEntity());
  }
}
