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

class BPMNTimerFiredEventImplDiffblueTest {
  /**
   * Test {@link BPMNTimerFiredEventImpl#BPMNTimerFiredEventImpl()}.
   * <p>
   * Method under test: {@link BPMNTimerFiredEventImpl#BPMNTimerFiredEventImpl()}
   */
  @Test
  @DisplayName("Test new BPMNTimerFiredEventImpl()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BPMNTimerFiredEventImpl.<init>()"})
  void testNewBPMNTimerFiredEventImpl() {
    // Arrange and Act
    BPMNTimerFiredEventImpl actualBpmnTimerFiredEventImpl = new BPMNTimerFiredEventImpl();

    // Assert
    assertNull(actualBpmnTimerFiredEventImpl.getProcessDefinitionVersion());
    assertNull(actualBpmnTimerFiredEventImpl.getBusinessKey());
    assertNull(actualBpmnTimerFiredEventImpl.getParentProcessInstanceId());
    assertNull(actualBpmnTimerFiredEventImpl.getProcessDefinitionId());
    assertNull(actualBpmnTimerFiredEventImpl.getProcessDefinitionKey());
    assertNull(actualBpmnTimerFiredEventImpl.getProcessInstanceId());
    assertNull(actualBpmnTimerFiredEventImpl.getEntity());
    assertEquals(TimerEvents.TIMER_FIRED, actualBpmnTimerFiredEventImpl.getEventType());
  }

  /**
   * Test {@link BPMNTimerFiredEventImpl#BPMNTimerFiredEventImpl(BPMNTimer)}.
   * <p>
   * Method under test: {@link BPMNTimerFiredEventImpl#BPMNTimerFiredEventImpl(BPMNTimer)}
   */
  @Test
  @DisplayName("Test new BPMNTimerFiredEventImpl(BPMNTimer)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BPMNTimerFiredEventImpl.<init>(BPMNTimer)"})
  void testNewBPMNTimerFiredEventImpl2() {
    // Arrange
    BPMNTimerImpl entity = new BPMNTimerImpl("42");

    // Act
    BPMNTimerFiredEventImpl actualBpmnTimerFiredEventImpl = new BPMNTimerFiredEventImpl(entity);

    // Assert
    assertNull(actualBpmnTimerFiredEventImpl.getProcessDefinitionVersion());
    assertNull(actualBpmnTimerFiredEventImpl.getBusinessKey());
    assertNull(actualBpmnTimerFiredEventImpl.getParentProcessInstanceId());
    assertNull(actualBpmnTimerFiredEventImpl.getProcessDefinitionId());
    assertNull(actualBpmnTimerFiredEventImpl.getProcessDefinitionKey());
    assertNull(actualBpmnTimerFiredEventImpl.getProcessInstanceId());
    assertEquals(TimerEvents.TIMER_FIRED, actualBpmnTimerFiredEventImpl.getEventType());
    assertSame(entity, actualBpmnTimerFiredEventImpl.getEntity());
  }

  /**
   * Test {@link BPMNTimerFiredEventImpl#getEventType()}.
   * <p>
   * Method under test: {@link BPMNTimerFiredEventImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"TimerEvents BPMNTimerFiredEventImpl.getEventType()"})
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(TimerEvents.TIMER_FIRED, (new BPMNTimerFiredEventImpl()).getEventType());
  }
}
