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
import static org.mockito.Mockito.mock;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.activiti.api.process.model.BPMNTimer;
import org.activiti.api.process.model.events.BPMNTimerEvent;
import org.activiti.api.process.model.payloads.TimerPayload;
import org.activiti.api.runtime.model.impl.BPMNTimerImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BPMNTimerRetriesDecrementedEventImplDiffblueTest {
  /**
   * Test
   * {@link BPMNTimerRetriesDecrementedEventImpl#BPMNTimerRetriesDecrementedEventImpl()}.
   * <p>
   * Method under test:
   * {@link BPMNTimerRetriesDecrementedEventImpl#BPMNTimerRetriesDecrementedEventImpl()}
   */
  @Test
  @DisplayName("Test new BPMNTimerRetriesDecrementedEventImpl()")
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
    assertEquals(BPMNTimerEvent.TimerEvents.TIMER_RETRIES_DECREMENTED,
        actualBpmnTimerRetriesDecrementedEventImpl.getEventType());
  }

  /**
   * Test
   * {@link BPMNTimerRetriesDecrementedEventImpl#BPMNTimerRetriesDecrementedEventImpl(BPMNTimer)}.
   * <p>
   * Method under test:
   * {@link BPMNTimerRetriesDecrementedEventImpl#BPMNTimerRetriesDecrementedEventImpl(BPMNTimer)}
   */
  @Test
  @DisplayName("Test new BPMNTimerRetriesDecrementedEventImpl(BPMNTimer)")
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
    assertEquals(BPMNTimerEvent.TimerEvents.TIMER_RETRIES_DECREMENTED,
        actualBpmnTimerRetriesDecrementedEventImpl.getEventType());
    assertSame(entity, actualBpmnTimerRetriesDecrementedEventImpl.getEntity());
  }

  /**
   * Test
   * {@link BPMNTimerRetriesDecrementedEventImpl#BPMNTimerRetriesDecrementedEventImpl(BPMNTimer)}.
   * <ul>
   *   <li>Then Entity return {@link BPMNTimerImpl}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BPMNTimerRetriesDecrementedEventImpl#BPMNTimerRetriesDecrementedEventImpl(BPMNTimer)}
   */
  @Test
  @DisplayName("Test new BPMNTimerRetriesDecrementedEventImpl(BPMNTimer); then Entity return BPMNTimerImpl")
  void testNewBPMNTimerRetriesDecrementedEventImpl_thenEntityReturnBPMNTimerImpl() {
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

    // Act and Assert
    BPMNTimer entity2 = (new BPMNTimerRetriesDecrementedEventImpl(entity)).getEntity();
    assertTrue(entity2 instanceof BPMNTimerImpl);
    assertSame(timerPayload, entity2.getTimerPayload());
  }

  /**
   * Test {@link BPMNTimerRetriesDecrementedEventImpl#getEventType()}.
   * <p>
   * Method under test:
   * {@link BPMNTimerRetriesDecrementedEventImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType()")
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(BPMNTimerEvent.TimerEvents.TIMER_RETRIES_DECREMENTED,
        (new BPMNTimerRetriesDecrementedEventImpl()).getEventType());
  }
}
