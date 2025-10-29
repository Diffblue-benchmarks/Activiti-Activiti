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
import static org.mockito.Mockito.mock;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.activiti.api.process.model.BPMNTimer;
import org.activiti.api.process.model.events.BPMNTimerEvent;
import org.activiti.api.process.model.payloads.TimerPayload;
import org.activiti.api.runtime.model.impl.BPMNTimerImpl;
import org.junit.jupiter.api.Test;

class BPMNTimerScheduledEventImplDiffblueTest {
  /**
   * Method under test: {@link BPMNTimerScheduledEventImpl#getEventType()}
   */
  @Test
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(BPMNTimerEvent.TimerEvents.TIMER_SCHEDULED, (new BPMNTimerScheduledEventImpl()).getEventType());
  }

  /**
   * Method under test:
   * {@link BPMNTimerScheduledEventImpl#BPMNTimerScheduledEventImpl()}
   */
  @Test
  void testNewBPMNTimerScheduledEventImpl() {
    // Arrange and Act
    BPMNTimerScheduledEventImpl actualBpmnTimerScheduledEventImpl = new BPMNTimerScheduledEventImpl();

    // Assert
    assertNull(actualBpmnTimerScheduledEventImpl.getProcessDefinitionVersion());
    assertNull(actualBpmnTimerScheduledEventImpl.getBusinessKey());
    assertNull(actualBpmnTimerScheduledEventImpl.getParentProcessInstanceId());
    assertNull(actualBpmnTimerScheduledEventImpl.getProcessDefinitionId());
    assertNull(actualBpmnTimerScheduledEventImpl.getProcessDefinitionKey());
    assertNull(actualBpmnTimerScheduledEventImpl.getProcessInstanceId());
    assertNull(actualBpmnTimerScheduledEventImpl.getEntity());
    assertEquals(BPMNTimerEvent.TimerEvents.TIMER_SCHEDULED, actualBpmnTimerScheduledEventImpl.getEventType());
  }

  /**
   * Method under test:
   * {@link BPMNTimerScheduledEventImpl#BPMNTimerScheduledEventImpl(BPMNTimer)}
   */
  @Test
  void testNewBPMNTimerScheduledEventImpl2() {
    // Arrange
    BPMNTimerImpl entity = new BPMNTimerImpl("42");

    // Act
    BPMNTimerScheduledEventImpl actualBpmnTimerScheduledEventImpl = new BPMNTimerScheduledEventImpl(entity);

    // Assert
    assertNull(actualBpmnTimerScheduledEventImpl.getProcessDefinitionVersion());
    assertNull(actualBpmnTimerScheduledEventImpl.getBusinessKey());
    assertNull(actualBpmnTimerScheduledEventImpl.getParentProcessInstanceId());
    assertNull(actualBpmnTimerScheduledEventImpl.getProcessDefinitionId());
    assertNull(actualBpmnTimerScheduledEventImpl.getProcessDefinitionKey());
    assertNull(actualBpmnTimerScheduledEventImpl.getProcessInstanceId());
    assertEquals(BPMNTimerEvent.TimerEvents.TIMER_SCHEDULED, actualBpmnTimerScheduledEventImpl.getEventType());
    assertSame(entity, actualBpmnTimerScheduledEventImpl.getEntity());
  }

  /**
   * Method under test:
   * {@link BPMNTimerScheduledEventImpl#BPMNTimerScheduledEventImpl(BPMNTimer)}
   */
  @Test
  void testNewBPMNTimerScheduledEventImpl3() {
    // Arrange
    TimerPayload timerPayload = new TimerPayload();
    timerPayload.setDuedate(mock(java.sql.Date.class));
    timerPayload
        .setEndDate(java.util.Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload.setExceptionMessage("An error occurred");
    timerPayload.setMaxIterations(3);
    timerPayload.setRepeat("Repeat");
    timerPayload.setRetries(1);

    BPMNTimerImpl entity = new BPMNTimerImpl("42");
    entity.setTimerPayload(timerPayload);

    // Act
    BPMNTimerScheduledEventImpl actualBpmnTimerScheduledEventImpl = new BPMNTimerScheduledEventImpl(entity);

    // Assert
    assertNull(actualBpmnTimerScheduledEventImpl.getProcessDefinitionVersion());
    assertNull(actualBpmnTimerScheduledEventImpl.getBusinessKey());
    assertNull(actualBpmnTimerScheduledEventImpl.getParentProcessInstanceId());
    assertNull(actualBpmnTimerScheduledEventImpl.getProcessDefinitionId());
    assertNull(actualBpmnTimerScheduledEventImpl.getProcessDefinitionKey());
    assertNull(actualBpmnTimerScheduledEventImpl.getProcessInstanceId());
    assertEquals(BPMNTimerEvent.TimerEvents.TIMER_SCHEDULED, actualBpmnTimerScheduledEventImpl.getEventType());
    assertSame(entity, actualBpmnTimerScheduledEventImpl.getEntity());
  }
}
