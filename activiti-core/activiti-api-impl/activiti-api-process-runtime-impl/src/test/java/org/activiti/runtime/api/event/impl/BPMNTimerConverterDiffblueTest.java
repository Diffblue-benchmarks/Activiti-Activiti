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
package org.activiti.runtime.api.event.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.activiti.api.process.model.payloads.TimerPayload;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiActivityCancelledEventImpl;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.impl.persistence.entity.AbstractJobEntity;
import org.activiti.engine.impl.persistence.entity.DeadLetterJobEntityImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {BPMNTimerConverter.class})
@ExtendWith(SpringExtension.class)
class BPMNTimerConverterDiffblueTest {
  @Autowired
  private BPMNTimerConverter bPMNTimerConverter;

  /**
   * Test {@link BPMNTimerConverter#convertToTimerPayload(AbstractJobEntity)}.
   * <ul>
   *   <li>Given three.</li>
   *   <li>Then return ExceptionMessage is {@code An error occurred}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BPMNTimerConverter#convertToTimerPayload(AbstractJobEntity)}
   */
  @Test
  @DisplayName("Test convertToTimerPayload(AbstractJobEntity); given three; then return ExceptionMessage is 'An error occurred'")
  void testConvertToTimerPayload_givenThree_thenReturnExceptionMessageIsAnErrorOccurred() {
    // Arrange
    DeadLetterJobEntityImpl jobEntity = mock(DeadLetterJobEntityImpl.class);
    when(jobEntity.getMaxIterations()).thenReturn(3);
    when(jobEntity.getRetries()).thenReturn(1);
    when(jobEntity.getExceptionMessage()).thenReturn("An error occurred");
    when(jobEntity.getRepeat()).thenReturn("Repeat");
    when(jobEntity.getDuedate())
        .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(jobEntity.getEndDate())
        .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Act
    TimerPayload actualConvertToTimerPayloadResult = bPMNTimerConverter.convertToTimerPayload(jobEntity);

    // Assert
    verify(jobEntity).getDuedate();
    verify(jobEntity).getEndDate();
    verify(jobEntity).getExceptionMessage();
    verify(jobEntity).getMaxIterations();
    verify(jobEntity).getRepeat();
    verify(jobEntity).getRetries();
    assertEquals("An error occurred", actualConvertToTimerPayloadResult.getExceptionMessage());
    assertEquals("Repeat", actualConvertToTimerPayloadResult.getRepeat());
    assertEquals(1, actualConvertToTimerPayloadResult.getRetries());
    assertEquals(3, actualConvertToTimerPayloadResult.getMaxIterations());
  }

  /**
   * Test {@link BPMNTimerConverter#convertToTimerPayload(AbstractJobEntity)}.
   * <ul>
   *   <li>Then return ExceptionMessage is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BPMNTimerConverter#convertToTimerPayload(AbstractJobEntity)}
   */
  @Test
  @DisplayName("Test convertToTimerPayload(AbstractJobEntity); then return ExceptionMessage is 'null'")
  void testConvertToTimerPayload_thenReturnExceptionMessageIsNull() {
    // Arrange and Act
    TimerPayload actualConvertToTimerPayloadResult = bPMNTimerConverter
        .convertToTimerPayload(new DeadLetterJobEntityImpl());

    // Assert
    assertNull(actualConvertToTimerPayloadResult.getExceptionMessage());
    assertNull(actualConvertToTimerPayloadResult.getRepeat());
    assertNull(actualConvertToTimerPayloadResult.getDuedate());
    assertNull(actualConvertToTimerPayloadResult.getEndDate());
    assertEquals(0, actualConvertToTimerPayloadResult.getMaxIterations());
    assertEquals(0, actualConvertToTimerPayloadResult.getRetries());
  }

  /**
   * Test {@link BPMNTimerConverter#isTimerRelatedEvent(ActivitiEvent)}.
   * <p>
   * Method under test:
   * {@link BPMNTimerConverter#isTimerRelatedEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test isTimerRelatedEvent(ActivitiEvent)")
  void testIsTimerRelatedEvent() {
    // Arrange, Act and Assert
    assertFalse(bPMNTimerConverter
        .isTimerRelatedEvent(new ActivitiEntityEventImpl("Entity", ActivitiEventType.ENTITY_CREATED)));
  }

  /**
   * Test {@link BPMNTimerConverter#isTimerRelatedEvent(ActivitiEvent)}.
   * <ul>
   *   <li>Given {@code Entity}.</li>
   *   <li>Then calls {@link ActivitiEntityEventImpl#getEntity()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BPMNTimerConverter#isTimerRelatedEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test isTimerRelatedEvent(ActivitiEvent); given 'Entity'; then calls getEntity()")
  void testIsTimerRelatedEvent_givenEntity_thenCallsGetEntity() {
    // Arrange
    ActivitiEntityEventImpl event = mock(ActivitiEntityEventImpl.class);
    when(event.getEntity()).thenReturn("Entity");

    // Act
    boolean actualIsTimerRelatedEventResult = bPMNTimerConverter.isTimerRelatedEvent(event);

    // Assert
    verify(event).getEntity();
    assertFalse(actualIsTimerRelatedEventResult);
  }

  /**
   * Test {@link BPMNTimerConverter#isTimerRelatedEvent(ActivitiEvent)}.
   * <ul>
   *   <li>When {@link ActivitiActivityCancelledEventImpl} (default
   * constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BPMNTimerConverter#isTimerRelatedEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test isTimerRelatedEvent(ActivitiEvent); when ActivitiActivityCancelledEventImpl (default constructor); then return 'false'")
  void testIsTimerRelatedEvent_whenActivitiActivityCancelledEventImpl_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(bPMNTimerConverter.isTimerRelatedEvent(new ActivitiActivityCancelledEventImpl()));
  }
}
