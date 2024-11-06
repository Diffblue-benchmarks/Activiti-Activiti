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

class BPMNActivityCompletedEventImplDiffblueTest {
  /**
   * Test {@link BPMNActivityCompletedEventImpl#BPMNActivityCompletedEventImpl()}.
   * <p>
   * Method under test:
   * {@link BPMNActivityCompletedEventImpl#BPMNActivityCompletedEventImpl()}
   */
  @Test
  @DisplayName("Test new BPMNActivityCompletedEventImpl()")
  void testNewBPMNActivityCompletedEventImpl() {
    // Arrange and Act
    BPMNActivityCompletedEventImpl actualBpmnActivityCompletedEventImpl = new BPMNActivityCompletedEventImpl();

    // Assert
    assertNull(actualBpmnActivityCompletedEventImpl.getProcessDefinitionVersion());
    assertNull(actualBpmnActivityCompletedEventImpl.getBusinessKey());
    assertNull(actualBpmnActivityCompletedEventImpl.getParentProcessInstanceId());
    assertNull(actualBpmnActivityCompletedEventImpl.getProcessDefinitionId());
    assertNull(actualBpmnActivityCompletedEventImpl.getProcessDefinitionKey());
    assertNull(actualBpmnActivityCompletedEventImpl.getProcessInstanceId());
    assertNull(actualBpmnActivityCompletedEventImpl.getEntity());
    assertEquals(BPMNActivityEvent.ActivityEvents.ACTIVITY_COMPLETED,
        actualBpmnActivityCompletedEventImpl.getEventType());
  }

  /**
   * Test
   * {@link BPMNActivityCompletedEventImpl#BPMNActivityCompletedEventImpl(BPMNActivity)}.
   * <p>
   * Method under test:
   * {@link BPMNActivityCompletedEventImpl#BPMNActivityCompletedEventImpl(BPMNActivity)}
   */
  @Test
  @DisplayName("Test new BPMNActivityCompletedEventImpl(BPMNActivity)")
  void testNewBPMNActivityCompletedEventImpl2() {
    // Arrange
    BPMNActivityImpl entity = new BPMNActivityImpl("42", "Activity Name", "Activity Type");

    // Act
    BPMNActivityCompletedEventImpl actualBpmnActivityCompletedEventImpl = new BPMNActivityCompletedEventImpl(entity);

    // Assert
    assertNull(actualBpmnActivityCompletedEventImpl.getProcessDefinitionVersion());
    assertNull(actualBpmnActivityCompletedEventImpl.getBusinessKey());
    assertNull(actualBpmnActivityCompletedEventImpl.getParentProcessInstanceId());
    assertNull(actualBpmnActivityCompletedEventImpl.getProcessDefinitionId());
    assertNull(actualBpmnActivityCompletedEventImpl.getProcessDefinitionKey());
    assertNull(actualBpmnActivityCompletedEventImpl.getProcessInstanceId());
    assertEquals(BPMNActivityEvent.ActivityEvents.ACTIVITY_COMPLETED,
        actualBpmnActivityCompletedEventImpl.getEventType());
    assertSame(entity, actualBpmnActivityCompletedEventImpl.getEntity());
  }

  /**
   * Test {@link BPMNActivityCompletedEventImpl#getEventType()}.
   * <p>
   * Method under test: {@link BPMNActivityCompletedEventImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType()")
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(BPMNActivityEvent.ActivityEvents.ACTIVITY_COMPLETED,
        (new BPMNActivityCompletedEventImpl()).getEventType());
  }
}
