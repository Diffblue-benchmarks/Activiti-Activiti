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

class BPMNTimerFailedEventImplDiffblueTest {
  /**
   * Test {@link BPMNTimerFailedEventImpl#BPMNTimerFailedEventImpl()}.
   *
   * <p>Method under test: {@link BPMNTimerFailedEventImpl#BPMNTimerFailedEventImpl()}
   */
  @Test
  @DisplayName("Test new BPMNTimerFailedEventImpl()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BPMNTimerFailedEventImpl.<init>()"})
  void testNewBPMNTimerFailedEventImpl() {
    // Arrange and Act
    BPMNTimerFailedEventImpl actualBpmnTimerFailedEventImpl = new BPMNTimerFailedEventImpl();

    // Assert
    assertNull(actualBpmnTimerFailedEventImpl.getProcessDefinitionVersion());
    assertNull(actualBpmnTimerFailedEventImpl.getBusinessKey());
    assertNull(actualBpmnTimerFailedEventImpl.getParentProcessInstanceId());
    assertNull(actualBpmnTimerFailedEventImpl.getProcessDefinitionId());
    assertNull(actualBpmnTimerFailedEventImpl.getProcessDefinitionKey());
    assertNull(actualBpmnTimerFailedEventImpl.getProcessInstanceId());
    assertNull(actualBpmnTimerFailedEventImpl.getEntity());
    assertEquals(TimerEvents.TIMER_FAILED, actualBpmnTimerFailedEventImpl.getEventType());
  }

  /**
   * Test {@link BPMNTimerFailedEventImpl#BPMNTimerFailedEventImpl(BPMNTimer)}.
   *
   * <p>Method under test: {@link BPMNTimerFailedEventImpl#BPMNTimerFailedEventImpl(BPMNTimer)}
   */
  @Test
  @DisplayName("Test new BPMNTimerFailedEventImpl(BPMNTimer)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BPMNTimerFailedEventImpl.<init>(BPMNTimer)"})
  void testNewBPMNTimerFailedEventImpl2() {
    // Arrange
    BPMNTimerImpl entity = new BPMNTimerImpl("42");

    // Act
    BPMNTimerFailedEventImpl actualBpmnTimerFailedEventImpl = new BPMNTimerFailedEventImpl(entity);

    // Assert
    assertNull(actualBpmnTimerFailedEventImpl.getProcessDefinitionVersion());
    assertNull(actualBpmnTimerFailedEventImpl.getBusinessKey());
    assertNull(actualBpmnTimerFailedEventImpl.getParentProcessInstanceId());
    assertNull(actualBpmnTimerFailedEventImpl.getProcessDefinitionId());
    assertNull(actualBpmnTimerFailedEventImpl.getProcessDefinitionKey());
    assertNull(actualBpmnTimerFailedEventImpl.getProcessInstanceId());
    assertEquals(TimerEvents.TIMER_FAILED, actualBpmnTimerFailedEventImpl.getEventType());
    assertSame(entity, actualBpmnTimerFailedEventImpl.getEntity());
  }

  /**
   * Test {@link BPMNTimerFailedEventImpl#getEventType()}.
   *
   * <p>Method under test: {@link BPMNTimerFailedEventImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"TimerEvents BPMNTimerFailedEventImpl.getEventType()"})
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(TimerEvents.TIMER_FAILED, new BPMNTimerFailedEventImpl().getEventType());
  }
}
