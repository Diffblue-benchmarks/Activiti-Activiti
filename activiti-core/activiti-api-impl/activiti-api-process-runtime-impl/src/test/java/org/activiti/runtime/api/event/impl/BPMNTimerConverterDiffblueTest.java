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
import static org.junit.jupiter.api.Assertions.assertSame;
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
   * Method under test:
   * {@link BPMNTimerConverter#convertToTimerPayload(AbstractJobEntity)}
   */
  @Test
  void testConvertToTimerPayload() {
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
   * Method under test:
   * {@link BPMNTimerConverter#convertToTimerPayload(AbstractJobEntity)}
   */
  @Test
  void testConvertToTimerPayload2() {
    // Arrange
    DeadLetterJobEntityImpl jobEntity = mock(DeadLetterJobEntityImpl.class);
    when(jobEntity.getMaxIterations()).thenReturn(3);
    when(jobEntity.getRetries()).thenReturn(1);
    when(jobEntity.getExceptionMessage()).thenReturn("An error occurred");
    when(jobEntity.getRepeat()).thenReturn("Repeat");
    Date fromResult = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    when(jobEntity.getDuedate()).thenReturn(fromResult);
    Date fromResult2 = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    when(jobEntity.getEndDate()).thenReturn(fromResult2);

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
    assertSame(fromResult, actualConvertToTimerPayloadResult.getDuedate());
    assertSame(fromResult2, actualConvertToTimerPayloadResult.getEndDate());
  }

  /**
   * Method under test:
   * {@link BPMNTimerConverter#isTimerRelatedEvent(ActivitiEvent)}
   */
  @Test
  void testIsTimerRelatedEvent() {
    // Arrange, Act and Assert
    assertFalse(bPMNTimerConverter.isTimerRelatedEvent(new ActivitiActivityCancelledEventImpl()));
    assertFalse(bPMNTimerConverter
        .isTimerRelatedEvent(new ActivitiEntityEventImpl("Entity", ActivitiEventType.ENTITY_CREATED)));
  }

  /**
   * Method under test:
   * {@link BPMNTimerConverter#isTimerRelatedEvent(ActivitiEvent)}
   */
  @Test
  void testIsTimerRelatedEvent2() {
    // Arrange
    ActivitiEntityEventImpl event = mock(ActivitiEntityEventImpl.class);
    when(event.getEntity()).thenReturn("Entity");

    // Act
    boolean actualIsTimerRelatedEventResult = bPMNTimerConverter.isTimerRelatedEvent(event);

    // Assert
    verify(event).getEntity();
    assertFalse(actualIsTimerRelatedEventResult);
  }
}
