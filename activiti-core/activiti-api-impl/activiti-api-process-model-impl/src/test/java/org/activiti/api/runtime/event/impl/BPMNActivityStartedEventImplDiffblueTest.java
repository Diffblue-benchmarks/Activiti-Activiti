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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BPMNActivityStartedEventImplDiffblueTest {
  /**
   * Test {@link BPMNActivityStartedEventImpl#BPMNActivityStartedEventImpl()}.
   * <p>
   * Method under test:
   * {@link BPMNActivityStartedEventImpl#BPMNActivityStartedEventImpl()}
   */
  @Test
  @DisplayName("Test new BPMNActivityStartedEventImpl()")
  void testNewBPMNActivityStartedEventImpl() {
    // Arrange and Act
    BPMNActivityStartedEventImpl actualBpmnActivityStartedEventImpl = new BPMNActivityStartedEventImpl();

    // Assert
    assertNull(actualBpmnActivityStartedEventImpl.getProcessDefinitionVersion());
    assertNull(actualBpmnActivityStartedEventImpl.getBusinessKey());
    assertNull(actualBpmnActivityStartedEventImpl.getParentProcessInstanceId());
    assertNull(actualBpmnActivityStartedEventImpl.getProcessDefinitionId());
    assertNull(actualBpmnActivityStartedEventImpl.getProcessDefinitionKey());
    assertNull(actualBpmnActivityStartedEventImpl.getProcessInstanceId());
    assertNull(actualBpmnActivityStartedEventImpl.getEntity());
    assertEquals(BPMNActivityEvent.ActivityEvents.ACTIVITY_STARTED, actualBpmnActivityStartedEventImpl.getEventType());
  }

  /**
   * Test
   * {@link BPMNActivityStartedEventImpl#BPMNActivityStartedEventImpl(BPMNActivity)}.
   * <p>
   * Method under test:
   * {@link BPMNActivityStartedEventImpl#BPMNActivityStartedEventImpl(BPMNActivity)}
   */
  @Test
  @DisplayName("Test new BPMNActivityStartedEventImpl(BPMNActivity)")
  void testNewBPMNActivityStartedEventImpl2() {
    // Arrange
    BPMNActivityImpl entity = new BPMNActivityImpl("42", "Activity Name", "Activity Type");

    // Act
    BPMNActivityStartedEventImpl actualBpmnActivityStartedEventImpl = new BPMNActivityStartedEventImpl(entity);

    // Assert
    assertNull(actualBpmnActivityStartedEventImpl.getProcessDefinitionVersion());
    assertNull(actualBpmnActivityStartedEventImpl.getBusinessKey());
    assertNull(actualBpmnActivityStartedEventImpl.getParentProcessInstanceId());
    assertNull(actualBpmnActivityStartedEventImpl.getProcessDefinitionId());
    assertNull(actualBpmnActivityStartedEventImpl.getProcessDefinitionKey());
    assertNull(actualBpmnActivityStartedEventImpl.getProcessInstanceId());
    assertEquals(BPMNActivityEvent.ActivityEvents.ACTIVITY_STARTED, actualBpmnActivityStartedEventImpl.getEventType());
    assertSame(entity, actualBpmnActivityStartedEventImpl.getEntity());
  }

  /**
   * Test {@link BPMNActivityStartedEventImpl#getEventType()}.
   * <p>
   * Method under test: {@link BPMNActivityStartedEventImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType()")
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(BPMNActivityEvent.ActivityEvents.ACTIVITY_STARTED,
        (new BPMNActivityStartedEventImpl()).getEventType());
  }
}
