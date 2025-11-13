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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.activiti.api.process.model.payloads.TimerPayload;
import org.activiti.api.runtime.model.impl.BPMNTimerImpl;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiActivityCancelledEventImpl;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.impl.persistence.entity.AbstractJobEntity;
import org.activiti.engine.impl.persistence.entity.DeadLetterJobEntityImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {BPMNTimerConverter.class})
@ExtendWith(SpringExtension.class)
class BPMNTimerConverterDiffblueTest {
  @Autowired private BPMNTimerConverter bPMNTimerConverter;

  /**
   * Test {@link BPMNTimerConverter#convertToTimerPayload(AbstractJobEntity)}.
   *
   * <ul>
   *   <li>Then return ExceptionMessage is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BPMNTimerConverter#convertToTimerPayload(AbstractJobEntity)}
   */
  @Test
  @DisplayName(
      "Test convertToTimerPayload(AbstractJobEntity); then return ExceptionMessage is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"TimerPayload BPMNTimerConverter.convertToTimerPayload(AbstractJobEntity)"})
  void testConvertToTimerPayload_thenReturnExceptionMessageIsNull() {
    // Arrange and Act
    TimerPayload actualConvertToTimerPayloadResult =
        bPMNTimerConverter.convertToTimerPayload(new DeadLetterJobEntityImpl());

    // Assert
    assertNull(actualConvertToTimerPayloadResult.getExceptionMessage());
    assertNull(actualConvertToTimerPayloadResult.getRepeat());
    assertNull(actualConvertToTimerPayloadResult.getDuedate());
    assertNull(actualConvertToTimerPayloadResult.getEndDate());
    assertEquals(0, actualConvertToTimerPayloadResult.getMaxIterations());
    assertEquals(0, actualConvertToTimerPayloadResult.getRetries());
  }

  /**
   * Test {@link BPMNTimerConverter#convertToBPMNTimer(ActivitiEntityEvent)}.
   *
   * <ul>
   *   <li>Then return ProcessDefinitionId is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link BPMNTimerConverter#convertToBPMNTimer(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName(
      "Test convertToBPMNTimer(ActivitiEntityEvent); then return ProcessDefinitionId is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BPMNTimerImpl BPMNTimerConverter.convertToBPMNTimer(ActivitiEntityEvent)"})
  void testConvertToBPMNTimer_thenReturnProcessDefinitionIdIs42() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setJobHandlerConfiguration("Job Handler Configuration");

    ActivitiEntityEvent internalEvent = mock(ActivitiEntityEvent.class);
    when(internalEvent.getProcessDefinitionId()).thenReturn("42");
    when(internalEvent.getProcessInstanceId()).thenReturn("42");
    when(internalEvent.getEntity()).thenReturn(deadLetterJobEntityImpl);

    // Act
    BPMNTimerImpl actualConvertToBPMNTimerResult =
        bPMNTimerConverter.convertToBPMNTimer(internalEvent);

    // Assert
    verify(internalEvent).getEntity();
    verify(internalEvent).getProcessDefinitionId();
    verify(internalEvent).getProcessInstanceId();
    assertEquals("42", actualConvertToBPMNTimerResult.getProcessDefinitionId());
    assertEquals("42", actualConvertToBPMNTimerResult.getProcessInstanceId());
    assertEquals("Job Handler Configuration", actualConvertToBPMNTimerResult.getElementId());
    TimerPayload timerPayload = actualConvertToBPMNTimerResult.getTimerPayload();
    assertNull(timerPayload.getExceptionMessage());
    assertNull(timerPayload.getRepeat());
    assertNull(timerPayload.getDuedate());
    assertNull(timerPayload.getEndDate());
    assertEquals(0, timerPayload.getMaxIterations());
    assertEquals(0, timerPayload.getRetries());
  }

  /**
   * Test {@link BPMNTimerConverter#isTimerRelatedEvent(ActivitiEvent)}.
   *
   * <p>Method under test: {@link BPMNTimerConverter#isTimerRelatedEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test isTimerRelatedEvent(ActivitiEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BPMNTimerConverter.isTimerRelatedEvent(ActivitiEvent)"})
  void testIsTimerRelatedEvent() {
    // Arrange, Act and Assert
    assertFalse(
        bPMNTimerConverter.isTimerRelatedEvent(
            new ActivitiEntityEventImpl("Entity", ActivitiEventType.ENTITY_CREATED)));
  }

  /**
   * Test {@link BPMNTimerConverter#isTimerRelatedEvent(ActivitiEvent)}.
   *
   * <ul>
   *   <li>Given {@code Job Type}.
   *   <li>When {@link DeadLetterJobEntityImpl} (default constructor) JobType is {@code Job Type}.
   * </ul>
   *
   * <p>Method under test: {@link BPMNTimerConverter#isTimerRelatedEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName(
      "Test isTimerRelatedEvent(ActivitiEvent); given 'Job Type'; when DeadLetterJobEntityImpl (default constructor) JobType is 'Job Type'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BPMNTimerConverter.isTimerRelatedEvent(ActivitiEvent)"})
  void testIsTimerRelatedEvent_givenJobType_whenDeadLetterJobEntityImplJobTypeIsJobType() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDeleted(true);
    deadLetterJobEntityImpl.setDuedate(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    deadLetterJobEntityImpl.setEndDate(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    deadLetterJobEntityImpl.setExceptionMessage("An error occurred");
    deadLetterJobEntityImpl.setExclusive(true);
    deadLetterJobEntityImpl.setExecutionId("42");
    deadLetterJobEntityImpl.setId("42");
    deadLetterJobEntityImpl.setInserted(true);
    deadLetterJobEntityImpl.setJobHandlerConfiguration("Job Handler Configuration");
    deadLetterJobEntityImpl.setJobHandlerType("Job Handler Type");
    deadLetterJobEntityImpl.setMaxIterations(3);
    deadLetterJobEntityImpl.setProcessDefinitionId("42");
    deadLetterJobEntityImpl.setProcessInstanceId("42");
    deadLetterJobEntityImpl.setRepeat("Repeat");
    deadLetterJobEntityImpl.setRetries(1);
    deadLetterJobEntityImpl.setRevision(1);
    deadLetterJobEntityImpl.setTenantId("42");
    deadLetterJobEntityImpl.setUpdated(true);
    deadLetterJobEntityImpl.setJobType("Job Type");

    // Act and Assert
    assertFalse(
        bPMNTimerConverter.isTimerRelatedEvent(
            new ActivitiEntityEventImpl(
                deadLetterJobEntityImpl, ActivitiEventType.ENTITY_CREATED)));
  }

  /**
   * Test {@link BPMNTimerConverter#isTimerRelatedEvent(ActivitiEvent)}.
   *
   * <ul>
   *   <li>Given {@code timer}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link BPMNTimerConverter#isTimerRelatedEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test isTimerRelatedEvent(ActivitiEvent); given 'timer'; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BPMNTimerConverter.isTimerRelatedEvent(ActivitiEvent)"})
  void testIsTimerRelatedEvent_givenTimer_thenReturnTrue() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDeleted(true);
    deadLetterJobEntityImpl.setDuedate(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    deadLetterJobEntityImpl.setEndDate(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    deadLetterJobEntityImpl.setExceptionMessage("An error occurred");
    deadLetterJobEntityImpl.setExclusive(true);
    deadLetterJobEntityImpl.setExecutionId("42");
    deadLetterJobEntityImpl.setId("42");
    deadLetterJobEntityImpl.setInserted(true);
    deadLetterJobEntityImpl.setJobHandlerConfiguration("Job Handler Configuration");
    deadLetterJobEntityImpl.setJobHandlerType("Job Handler Type");
    deadLetterJobEntityImpl.setMaxIterations(3);
    deadLetterJobEntityImpl.setProcessDefinitionId("42");
    deadLetterJobEntityImpl.setProcessInstanceId("42");
    deadLetterJobEntityImpl.setRepeat("Repeat");
    deadLetterJobEntityImpl.setRetries(1);
    deadLetterJobEntityImpl.setRevision(1);
    deadLetterJobEntityImpl.setTenantId("42");
    deadLetterJobEntityImpl.setUpdated(true);
    deadLetterJobEntityImpl.setJobType("timer");

    // Act and Assert
    assertTrue(
        bPMNTimerConverter.isTimerRelatedEvent(
            new ActivitiEntityEventImpl(
                deadLetterJobEntityImpl, ActivitiEventType.ENTITY_CREATED)));
  }

  /**
   * Test {@link BPMNTimerConverter#isTimerRelatedEvent(ActivitiEvent)}.
   *
   * <ul>
   *   <li>When {@link ActivitiActivityCancelledEventImpl} (default constructor).
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BPMNTimerConverter#isTimerRelatedEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName(
      "Test isTimerRelatedEvent(ActivitiEvent); when ActivitiActivityCancelledEventImpl (default constructor); then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BPMNTimerConverter.isTimerRelatedEvent(ActivitiEvent)"})
  void testIsTimerRelatedEvent_whenActivitiActivityCancelledEventImpl_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(bPMNTimerConverter.isTimerRelatedEvent(new ActivitiActivityCancelledEventImpl()));
  }
}
