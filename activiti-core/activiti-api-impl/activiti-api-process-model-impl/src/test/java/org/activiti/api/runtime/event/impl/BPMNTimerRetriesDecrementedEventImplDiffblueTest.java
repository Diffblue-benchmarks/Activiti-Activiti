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
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.api.process.model.BPMNTimer;
import org.activiti.api.process.model.events.BPMNTimerEvent;
import org.activiti.api.process.model.events.BPMNTimerEvent.TimerEvents;
import org.activiti.api.runtime.model.impl.BPMNTimerImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class BPMNTimerRetriesDecrementedEventImplDiffblueTest {
  /**
   * Test {@link BPMNTimerRetriesDecrementedEventImpl#BPMNTimerRetriesDecrementedEventImpl()}.
   * <p>
   * Method under test: {@link BPMNTimerRetriesDecrementedEventImpl#BPMNTimerRetriesDecrementedEventImpl()}
   */
  @Test
  @DisplayName("Test new BPMNTimerRetriesDecrementedEventImpl()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BPMNTimerRetriesDecrementedEventImpl.<init>()"})
  void testNewBPMNTimerRetriesDecrementedEventImpl() {
    // Arrange and Act
    BPMNTimerRetriesDecrementedEventImpl actualBpmnTimerRetriesDecrementedEventImpl = new BPMNTimerRetriesDecrementedEventImpl();

    // Assert
    assertNull(actualBpmnTimerRetriesDecrementedEventImpl.getProcessDefinitionVersion());
    assertNull(actualBpmnTimerRetriesDecrementedEventImpl.getBusinessKey());
    assertNull(actualBpmnTimerRetriesDecrementedEventImpl.getParentProcessInstanceId());
    assertNull(actualBpmnTimerRetriesDecrementedEventImpl.getProcessDefinitionId());
    assertNull(actualBpmnTimerRetriesDecrementedEventImpl.getProcessDefinitionKey());
    assertNull(actualBpmnTimerRetriesDecrementedEventImpl.getProcessInstanceId());
    assertNull(actualBpmnTimerRetriesDecrementedEventImpl.getEntity());
    assertEquals(TimerEvents.TIMER_RETRIES_DECREMENTED, actualBpmnTimerRetriesDecrementedEventImpl.getEventType());
  }

  /**
   * Test {@link BPMNTimerRetriesDecrementedEventImpl#BPMNTimerRetriesDecrementedEventImpl(BPMNTimer)}.
   * <p>
   * Method under test: {@link BPMNTimerRetriesDecrementedEventImpl#BPMNTimerRetriesDecrementedEventImpl(BPMNTimer)}
   */
  @Test
  @DisplayName("Test new BPMNTimerRetriesDecrementedEventImpl(BPMNTimer)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BPMNTimerRetriesDecrementedEventImpl.<init>(BPMNTimer)"})
  void testNewBPMNTimerRetriesDecrementedEventImpl2() {
    // Arrange
    BPMNTimerImpl entity = new BPMNTimerImpl("42");

    // Act
    BPMNTimerRetriesDecrementedEventImpl actualBpmnTimerRetriesDecrementedEventImpl = new BPMNTimerRetriesDecrementedEventImpl(
        entity);

    // Assert
    assertNull(actualBpmnTimerRetriesDecrementedEventImpl.getProcessDefinitionVersion());
    assertNull(actualBpmnTimerRetriesDecrementedEventImpl.getBusinessKey());
    assertNull(actualBpmnTimerRetriesDecrementedEventImpl.getParentProcessInstanceId());
    assertNull(actualBpmnTimerRetriesDecrementedEventImpl.getProcessDefinitionId());
    assertNull(actualBpmnTimerRetriesDecrementedEventImpl.getProcessDefinitionKey());
    assertNull(actualBpmnTimerRetriesDecrementedEventImpl.getProcessInstanceId());
    assertEquals(TimerEvents.TIMER_RETRIES_DECREMENTED, actualBpmnTimerRetriesDecrementedEventImpl.getEventType());
    assertSame(entity, actualBpmnTimerRetriesDecrementedEventImpl.getEntity());
  }

  /**
   * Test {@link BPMNTimerRetriesDecrementedEventImpl#getEventType()}.
   * <p>
   * Method under test: {@link BPMNTimerRetriesDecrementedEventImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"TimerEvents BPMNTimerRetriesDecrementedEventImpl.getEventType()"})
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(TimerEvents.TIMER_RETRIES_DECREMENTED, (new BPMNTimerRetriesDecrementedEventImpl()).getEventType());
  }
}
