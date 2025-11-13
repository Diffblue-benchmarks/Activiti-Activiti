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

class BPMNTimerExecutedEventImplDiffblueTest {
  /**
   * Test {@link BPMNTimerExecutedEventImpl#BPMNTimerExecutedEventImpl()}.
   *
   * <p>Method under test: {@link BPMNTimerExecutedEventImpl#BPMNTimerExecutedEventImpl()}
   */
  @Test
  @DisplayName("Test new BPMNTimerExecutedEventImpl()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BPMNTimerExecutedEventImpl.<init>()"})
  void testNewBPMNTimerExecutedEventImpl() {
    // Arrange and Act
    BPMNTimerExecutedEventImpl actualBpmnTimerExecutedEventImpl = new BPMNTimerExecutedEventImpl();

    // Assert
    assertNull(actualBpmnTimerExecutedEventImpl.getProcessDefinitionVersion());
    assertNull(actualBpmnTimerExecutedEventImpl.getBusinessKey());
    assertNull(actualBpmnTimerExecutedEventImpl.getParentProcessInstanceId());
    assertNull(actualBpmnTimerExecutedEventImpl.getProcessDefinitionId());
    assertNull(actualBpmnTimerExecutedEventImpl.getProcessDefinitionKey());
    assertNull(actualBpmnTimerExecutedEventImpl.getProcessInstanceId());
    assertNull(actualBpmnTimerExecutedEventImpl.getEntity());
    assertEquals(TimerEvents.TIMER_EXECUTED, actualBpmnTimerExecutedEventImpl.getEventType());
  }

  /**
   * Test {@link BPMNTimerExecutedEventImpl#BPMNTimerExecutedEventImpl(BPMNTimer)}.
   *
   * <p>Method under test: {@link BPMNTimerExecutedEventImpl#BPMNTimerExecutedEventImpl(BPMNTimer)}
   */
  @Test
  @DisplayName("Test new BPMNTimerExecutedEventImpl(BPMNTimer)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BPMNTimerExecutedEventImpl.<init>(BPMNTimer)"})
  void testNewBPMNTimerExecutedEventImpl2() {
    // Arrange
    BPMNTimerImpl entity = new BPMNTimerImpl("42");

    // Act
    BPMNTimerExecutedEventImpl actualBpmnTimerExecutedEventImpl =
        new BPMNTimerExecutedEventImpl(entity);

    // Assert
    assertNull(actualBpmnTimerExecutedEventImpl.getProcessDefinitionVersion());
    assertNull(actualBpmnTimerExecutedEventImpl.getBusinessKey());
    assertNull(actualBpmnTimerExecutedEventImpl.getParentProcessInstanceId());
    assertNull(actualBpmnTimerExecutedEventImpl.getProcessDefinitionId());
    assertNull(actualBpmnTimerExecutedEventImpl.getProcessDefinitionKey());
    assertNull(actualBpmnTimerExecutedEventImpl.getProcessInstanceId());
    assertEquals(TimerEvents.TIMER_EXECUTED, actualBpmnTimerExecutedEventImpl.getEventType());
    assertSame(entity, actualBpmnTimerExecutedEventImpl.getEntity());
  }

  /**
   * Test {@link BPMNTimerExecutedEventImpl#getEventType()}.
   *
   * <p>Method under test: {@link BPMNTimerExecutedEventImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"TimerEvents BPMNTimerExecutedEventImpl.getEventType()"})
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(TimerEvents.TIMER_EXECUTED, new BPMNTimerExecutedEventImpl().getEventType());
  }
}
