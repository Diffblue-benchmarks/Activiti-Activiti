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
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.api.process.model.BPMNActivity;
import org.activiti.api.process.model.events.BPMNActivityEvent;
import org.activiti.api.process.model.events.BPMNActivityEvent.ActivityEvents;
import org.activiti.api.runtime.model.impl.BPMNActivityImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class BPMNActivityCancelledEventImplDiffblueTest {
  /**
   * Test {@link BPMNActivityCancelledEventImpl#BPMNActivityCancelledEventImpl()}.
   * <p>
   * Method under test: {@link BPMNActivityCancelledEventImpl#BPMNActivityCancelledEventImpl()}
   */
  @Test
  @DisplayName("Test new BPMNActivityCancelledEventImpl()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BPMNActivityCancelledEventImpl.<init>()"})
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
    assertEquals(ActivityEvents.ACTIVITY_CANCELLED, actualBpmnActivityCancelledEventImpl.getEventType());
  }

  /**
   * Test {@link BPMNActivityCancelledEventImpl#BPMNActivityCancelledEventImpl(BPMNActivity)}.
   * <p>
   * Method under test: {@link BPMNActivityCancelledEventImpl#BPMNActivityCancelledEventImpl(BPMNActivity)}
   */
  @Test
  @DisplayName("Test new BPMNActivityCancelledEventImpl(BPMNActivity)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BPMNActivityCancelledEventImpl.<init>(BPMNActivity)"})
  void testNewBPMNActivityCancelledEventImpl2() {
    // Arrange
    BPMNActivityImpl entity = new BPMNActivityImpl("42", "Activity Name", "Activity Type");

    // Act
    BPMNActivityCancelledEventImpl actualBpmnActivityCancelledEventImpl = new BPMNActivityCancelledEventImpl(entity);

    // Assert
    BPMNActivity entity2 = actualBpmnActivityCancelledEventImpl.getEntity();
    assertTrue(entity2 instanceof BPMNActivityImpl);
    assertNull(actualBpmnActivityCancelledEventImpl.getProcessDefinitionVersion());
    assertNull(actualBpmnActivityCancelledEventImpl.getBusinessKey());
    assertNull(actualBpmnActivityCancelledEventImpl.getParentProcessInstanceId());
    assertNull(actualBpmnActivityCancelledEventImpl.getProcessDefinitionId());
    assertNull(actualBpmnActivityCancelledEventImpl.getProcessDefinitionKey());
    assertNull(actualBpmnActivityCancelledEventImpl.getProcessInstanceId());
    assertEquals(ActivityEvents.ACTIVITY_CANCELLED, actualBpmnActivityCancelledEventImpl.getEventType());
    assertSame(entity, entity2);
  }

  /**
   * Test {@link BPMNActivityCancelledEventImpl#getEventType()}.
   * <p>
   * Method under test: {@link BPMNActivityCancelledEventImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ActivityEvents BPMNActivityCancelledEventImpl.getEventType()"})
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(ActivityEvents.ACTIVITY_CANCELLED, (new BPMNActivityCancelledEventImpl()).getEventType());
  }
}
