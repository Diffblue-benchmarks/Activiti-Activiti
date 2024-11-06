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

class BPMNTimerFiredEventImplDiffblueTest {
  /**
   * Test {@link BPMNTimerFiredEventImpl#BPMNTimerFiredEventImpl()}.
   * <p>
   * Method under test: {@link BPMNTimerFiredEventImpl#BPMNTimerFiredEventImpl()}
   */
  @Test
  @DisplayName("Test new BPMNTimerFiredEventImpl()")
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
    assertEquals(BPMNTimerEvent.TimerEvents.TIMER_FIRED, actualBpmnTimerFiredEventImpl.getEventType());
  }

  /**
   * Test {@link BPMNTimerFiredEventImpl#BPMNTimerFiredEventImpl(BPMNTimer)}.
   * <ul>
   *   <li>Then Entity return {@link BPMNTimerImpl}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BPMNTimerFiredEventImpl#BPMNTimerFiredEventImpl(BPMNTimer)}
   */
  @Test
  @DisplayName("Test new BPMNTimerFiredEventImpl(BPMNTimer); then Entity return BPMNTimerImpl")
  void testNewBPMNTimerFiredEventImpl_thenEntityReturnBPMNTimerImpl() {
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
    BPMNTimer entity2 = (new BPMNTimerFiredEventImpl(entity)).getEntity();
    assertTrue(entity2 instanceof BPMNTimerImpl);
    assertSame(timerPayload, entity2.getTimerPayload());
  }

  /**
   * Test {@link BPMNTimerFiredEventImpl#BPMNTimerFiredEventImpl(BPMNTimer)}.
   * <ul>
   *   <li>Then return ProcessDefinitionVersion is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BPMNTimerFiredEventImpl#BPMNTimerFiredEventImpl(BPMNTimer)}
   */
  @Test
  @DisplayName("Test new BPMNTimerFiredEventImpl(BPMNTimer); then return ProcessDefinitionVersion is 'null'")
  void testNewBPMNTimerFiredEventImpl_thenReturnProcessDefinitionVersionIsNull() {
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
    assertEquals(BPMNTimerEvent.TimerEvents.TIMER_FIRED, actualBpmnTimerFiredEventImpl.getEventType());
    assertSame(entity, actualBpmnTimerFiredEventImpl.getEntity());
  }

  /**
   * Test {@link BPMNTimerFiredEventImpl#getEventType()}.
   * <p>
   * Method under test: {@link BPMNTimerFiredEventImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType()")
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(BPMNTimerEvent.TimerEvents.TIMER_FIRED, (new BPMNTimerFiredEventImpl()).getEventType());
  }
}
