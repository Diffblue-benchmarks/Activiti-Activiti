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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;
import org.activiti.api.process.model.events.BPMNTimerEvent;
import org.activiti.api.process.model.events.BPMNTimerEvent.TimerEvents;
import org.activiti.api.process.model.events.BPMNTimerFiredEvent;
import org.activiti.api.runtime.event.impl.BPMNTimerFiredEventImpl;
import org.activiti.api.runtime.model.impl.BPMNTimerImpl;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiActivityCancelledEventImpl;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.impl.persistence.entity.DeadLetterJobEntityImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ToTimerFiredConverterDiffblueTest {
  /**
   * Test {@link ToTimerFiredConverter#from(ActivitiEvent)}.
   * <ul>
   *   <li>Given {@code A JSONObject text must begin with '{'}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ToTimerFiredConverter#from(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEvent); given 'A JSONObject text must begin with '{''")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional ToTimerFiredConverter.from(ActivitiEvent)"})
  void testFrom_givenAJSONObjectTextMustBeginWith() {
    // Arrange
    ToTimerFiredConverter toTimerFiredConverter =
        new ToTimerFiredConverter(new BPMNTimerConverter());

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
    deadLetterJobEntityImpl.setJobType("A JSONObject text must begin with '{'");

    // Act and Assert
    assertFalse(
        toTimerFiredConverter
            .from(
                new ActivitiEntityEventImpl(
                    deadLetterJobEntityImpl, ActivitiEventType.ENTITY_CREATED))
            .isPresent());
  }

  /**
   * Test {@link ToTimerFiredConverter#from(ActivitiEvent)}.
   *
   * <ul>
   *   <li>Given {@code timer}.
   *   <li>Then {@link Optional#get()} return {@link BPMNTimerFiredEventImpl}.
   * </ul>
   *
   * <p>Method under test: {@link ToTimerFiredConverter#from(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEvent); given 'timer'; then get() return BPMNTimerFiredEventImpl")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional ToTimerFiredConverter.from(ActivitiEvent)"})
  void testFrom_givenTimer_thenGetReturnBPMNTimerFiredEventImpl() {
    // Arrange
    ToTimerFiredConverter toTimerFiredConverter =
        new ToTimerFiredConverter(new BPMNTimerConverter());

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

    // Act
    Optional<BPMNTimerFiredEvent> actualFromResult =
        toTimerFiredConverter.from(
            new ActivitiEntityEventImpl(deadLetterJobEntityImpl, ActivitiEventType.ENTITY_CREATED));

    // Assert
    BPMNTimerFiredEvent getResult = actualFromResult.get();
    assertTrue(getResult instanceof BPMNTimerFiredEventImpl);
    assertTrue(getResult.getEntity() instanceof BPMNTimerImpl);
    assertNull(getResult.getProcessDefinitionVersion());
    assertNull(getResult.getBusinessKey());
    assertNull(getResult.getParentProcessInstanceId());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionKey());
    assertNull(getResult.getProcessInstanceId());
    assertEquals(TimerEvents.TIMER_FIRED, getResult.getEventType());
    assertTrue(actualFromResult.isPresent());
  }

  /**
   * Test {@link ToTimerFiredConverter#from(ActivitiEvent)}.
   *
   * <ul>
   *   <li>When {@link ActivitiActivityCancelledEventImpl} (default constructor).
   *   <li>Then return not Present.
   * </ul>
   *
   * <p>Method under test: {@link ToTimerFiredConverter#from(ActivitiEvent)}
   */
  @Test
  @DisplayName(
      "Test from(ActivitiEvent); when ActivitiActivityCancelledEventImpl (default constructor); then return not Present")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional ToTimerFiredConverter.from(ActivitiEvent)"})
  void testFrom_whenActivitiActivityCancelledEventImpl_thenReturnNotPresent() {
    // Arrange
    ToTimerFiredConverter toTimerFiredConverter =
        new ToTimerFiredConverter(new BPMNTimerConverter());

    // Act and Assert
    assertFalse(toTimerFiredConverter.from(new ActivitiActivityCancelledEventImpl()).isPresent());
  }

  /**
   * Test {@link ToTimerFiredConverter#from(ActivitiEvent)}.
   *
   * <ul>
   *   <li>When {@link ActivitiEntityEventImpl#ActivitiEntityEventImpl(Object, ActivitiEventType)}
   *       with {@code Entity} and type is {@code ENTITY_CREATED}.
   * </ul>
   *
   * <p>Method under test: {@link ToTimerFiredConverter#from(ActivitiEvent)}
   */
  @Test
  @DisplayName(
      "Test from(ActivitiEvent); when ActivitiEntityEventImpl(Object, ActivitiEventType) with 'Entity' and type is 'ENTITY_CREATED'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional ToTimerFiredConverter.from(ActivitiEvent)"})
  void testFrom_whenActivitiEntityEventImplWithEntityAndTypeIsEntityCreated() {
    // Arrange
    ToTimerFiredConverter toTimerFiredConverter =
        new ToTimerFiredConverter(new BPMNTimerConverter());

    // Act and Assert
    assertFalse(
        toTimerFiredConverter
            .from(new ActivitiEntityEventImpl("Entity", ActivitiEventType.ENTITY_CREATED))
            .isPresent());
  }
}
