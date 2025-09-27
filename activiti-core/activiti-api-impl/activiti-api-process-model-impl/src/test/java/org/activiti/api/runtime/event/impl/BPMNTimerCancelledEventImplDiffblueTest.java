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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.api.process.model.BPMNTimer;
import org.activiti.api.process.model.events.BPMNTimerEvent;
import org.activiti.api.process.model.events.BPMNTimerEvent.TimerEvents;
import org.activiti.api.runtime.model.impl.BPMNTimerImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class BPMNTimerCancelledEventImplDiffblueTest {
  /**
   * Test {@link BPMNTimerCancelledEventImpl#BPMNTimerCancelledEventImpl()}.
   *
   * <p>Method under test: {@link BPMNTimerCancelledEventImpl#BPMNTimerCancelledEventImpl()}
   */
  @Test
  @DisplayName("Test new BPMNTimerCancelledEventImpl()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BPMNTimerCancelledEventImpl.<init>()"})
  void testNewBPMNTimerCancelledEventImpl() {
    // Arrange and Act
    BPMNTimerCancelledEventImpl actualBpmnTimerCancelledEventImpl =
        new BPMNTimerCancelledEventImpl();

    // Assert
    assertNull(actualBpmnTimerCancelledEventImpl.getProcessDefinitionVersion());
    assertNull(actualBpmnTimerCancelledEventImpl.getBusinessKey());
    assertNull(actualBpmnTimerCancelledEventImpl.getParentProcessInstanceId());
    assertNull(actualBpmnTimerCancelledEventImpl.getProcessDefinitionId());
    assertNull(actualBpmnTimerCancelledEventImpl.getProcessDefinitionKey());
    assertNull(actualBpmnTimerCancelledEventImpl.getProcessInstanceId());
    assertNull(actualBpmnTimerCancelledEventImpl.getEntity());
    assertEquals(TimerEvents.TIMER_CANCELLED, actualBpmnTimerCancelledEventImpl.getEventType());
  }

  /**
   * Test {@link BPMNTimerCancelledEventImpl#BPMNTimerCancelledEventImpl(BPMNTimer)}.
   *
   * <p>Method under test: {@link
   * BPMNTimerCancelledEventImpl#BPMNTimerCancelledEventImpl(BPMNTimer)}
   */
  @Test
  @DisplayName("Test new BPMNTimerCancelledEventImpl(BPMNTimer)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BPMNTimerCancelledEventImpl.<init>(BPMNTimer)"})
  void testNewBPMNTimerCancelledEventImpl2() {
    // Arrange
    BPMNTimerImpl entity = new BPMNTimerImpl("42");

    // Act
    BPMNTimerCancelledEventImpl actualBpmnTimerCancelledEventImpl =
        new BPMNTimerCancelledEventImpl(entity);

    // Assert
    assertNull(actualBpmnTimerCancelledEventImpl.getProcessDefinitionVersion());
    assertNull(actualBpmnTimerCancelledEventImpl.getBusinessKey());
    assertNull(actualBpmnTimerCancelledEventImpl.getParentProcessInstanceId());
    assertNull(actualBpmnTimerCancelledEventImpl.getProcessDefinitionId());
    assertNull(actualBpmnTimerCancelledEventImpl.getProcessDefinitionKey());
    assertNull(actualBpmnTimerCancelledEventImpl.getProcessInstanceId());
    assertEquals(TimerEvents.TIMER_CANCELLED, actualBpmnTimerCancelledEventImpl.getEventType());
    assertSame(entity, actualBpmnTimerCancelledEventImpl.getEntity());
  }

  /**
   * Test {@link BPMNTimerCancelledEventImpl#getEventType()}.
   *
   * <p>Method under test: {@link BPMNTimerCancelledEventImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"TimerEvents BPMNTimerCancelledEventImpl.getEventType()"})
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(TimerEvents.TIMER_CANCELLED, new BPMNTimerCancelledEventImpl().getEventType());
  }
}
