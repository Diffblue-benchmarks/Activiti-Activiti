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
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;
import org.activiti.api.process.model.events.BPMNTimerEvent;
import org.activiti.api.process.model.events.BPMNTimerEvent.TimerEvents;
import org.activiti.api.process.model.events.BPMNTimerExecutedEvent;
import org.activiti.api.runtime.event.impl.BPMNTimerExecutedEventImpl;
import org.activiti.api.runtime.model.impl.BPMNTimerImpl;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiActivityCancelledEventImpl;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.impl.persistence.entity.JobEntityImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ToTimerExecutedConverterDiffblueTest {
  /**
   * Test {@link ToTimerExecutedConverter#from(ActivitiEvent)}.
   * <ul>
   *   <li>Given {@code A JSONObject text must begin with '{'}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ToTimerExecutedConverter#from(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEvent); given 'A JSONObject text must begin with '{''")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ToTimerExecutedConverter.from(ActivitiEvent)"})
  void testFrom_givenAJSONObjectTextMustBeginWith() {
    // Arrange
    ToTimerExecutedConverter toTimerExecutedConverter = new ToTimerExecutedConverter(new BPMNTimerConverter());

    JobEntityImpl jobEntityImpl = new JobEntityImpl();
    jobEntityImpl.setDeleted(true);
    jobEntityImpl.setDuedate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    jobEntityImpl.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    jobEntityImpl.setExceptionMessage("An error occurred");
    jobEntityImpl.setExclusive(true);
    jobEntityImpl.setExecutionId("42");
    jobEntityImpl.setId("42");
    jobEntityImpl.setInserted(true);
    jobEntityImpl.setJobHandlerConfiguration("timer");
    jobEntityImpl.setJobHandlerType("timer");
    jobEntityImpl.setJobType("timer");
    jobEntityImpl
        .setLockExpirationTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    jobEntityImpl.setLockOwner("timer");
    jobEntityImpl.setMaxIterations(3);
    jobEntityImpl.setProcessDefinitionId("42");
    jobEntityImpl.setProcessInstanceId("42");
    jobEntityImpl.setRepeat("timer");
    jobEntityImpl.setRetries(1);
    jobEntityImpl.setRevision(1);
    jobEntityImpl.setTenantId("42");
    jobEntityImpl.setUpdated(true);
    jobEntityImpl.setJobType("A JSONObject text must begin with '{'");

    // Act and Assert
    assertFalse(
        toTimerExecutedConverter.from(new ActivitiEntityEventImpl(jobEntityImpl, ActivitiEventType.ENTITY_CREATED))
            .isPresent());
  }

  /**
   * Test {@link ToTimerExecutedConverter#from(ActivitiEvent)}.
   * <ul>
   *   <li>Given {@code true}.</li>
   *   <li>Then {@link Optional#get()} return {@link BPMNTimerExecutedEventImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ToTimerExecutedConverter#from(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEvent); given 'true'; then get() return BPMNTimerExecutedEventImpl")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ToTimerExecutedConverter.from(ActivitiEvent)"})
  void testFrom_givenTrue_thenGetReturnBPMNTimerExecutedEventImpl() {
    // Arrange
    ToTimerExecutedConverter toTimerExecutedConverter = new ToTimerExecutedConverter(new BPMNTimerConverter());

    JobEntityImpl jobEntityImpl = new JobEntityImpl();
    jobEntityImpl.setDeleted(true);
    jobEntityImpl.setDuedate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    jobEntityImpl.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    jobEntityImpl.setExceptionMessage("An error occurred");
    jobEntityImpl.setExclusive(true);
    jobEntityImpl.setExecutionId("42");
    jobEntityImpl.setId("42");
    jobEntityImpl.setInserted(true);
    jobEntityImpl.setJobHandlerConfiguration("timer");
    jobEntityImpl.setJobHandlerType("timer");
    jobEntityImpl.setJobType("timer");
    jobEntityImpl
        .setLockExpirationTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    jobEntityImpl.setLockOwner("timer");
    jobEntityImpl.setMaxIterations(3);
    jobEntityImpl.setProcessDefinitionId("42");
    jobEntityImpl.setProcessInstanceId("42");
    jobEntityImpl.setRepeat("timer");
    jobEntityImpl.setRetries(1);
    jobEntityImpl.setRevision(1);
    jobEntityImpl.setTenantId("42");
    jobEntityImpl.setUpdated(true);
    jobEntityImpl.setJobType("timer");

    // Act
    Optional<BPMNTimerExecutedEvent> actualFromResult = toTimerExecutedConverter
        .from(new ActivitiEntityEventImpl(jobEntityImpl, ActivitiEventType.ENTITY_CREATED));

    // Assert
    BPMNTimerExecutedEvent getResult = actualFromResult.get();
    assertTrue(getResult instanceof BPMNTimerExecutedEventImpl);
    assertTrue(getResult.getEntity() instanceof BPMNTimerImpl);
    assertNull(getResult.getProcessDefinitionVersion());
    assertNull(getResult.getBusinessKey());
    assertNull(getResult.getParentProcessInstanceId());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionKey());
    assertNull(getResult.getProcessInstanceId());
    assertEquals(TimerEvents.TIMER_EXECUTED, getResult.getEventType());
    assertTrue(actualFromResult.isPresent());
  }

  /**
   * Test {@link ToTimerExecutedConverter#from(ActivitiEvent)}.
   * <ul>
   *   <li>When {@link ActivitiActivityCancelledEventImpl} (default constructor).</li>
   *   <li>Then return not Present.</li>
   * </ul>
   * <p>
   * Method under test: {@link ToTimerExecutedConverter#from(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEvent); when ActivitiActivityCancelledEventImpl (default constructor); then return not Present")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ToTimerExecutedConverter.from(ActivitiEvent)"})
  void testFrom_whenActivitiActivityCancelledEventImpl_thenReturnNotPresent() {
    // Arrange
    ToTimerExecutedConverter toTimerExecutedConverter = new ToTimerExecutedConverter(new BPMNTimerConverter());

    // Act and Assert
    assertFalse(toTimerExecutedConverter.from(new ActivitiActivityCancelledEventImpl()).isPresent());
  }

  /**
   * Test {@link ToTimerExecutedConverter#from(ActivitiEvent)}.
   * <ul>
   *   <li>When {@link ActivitiEntityEventImpl#ActivitiEntityEventImpl(Object, ActivitiEventType)} with {@code Entity} and type is {@code ENTITY_CREATED}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ToTimerExecutedConverter#from(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEvent); when ActivitiEntityEventImpl(Object, ActivitiEventType) with 'Entity' and type is 'ENTITY_CREATED'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ToTimerExecutedConverter.from(ActivitiEvent)"})
  void testFrom_whenActivitiEntityEventImplWithEntityAndTypeIsEntityCreated() {
    // Arrange
    ToTimerExecutedConverter toTimerExecutedConverter = new ToTimerExecutedConverter(new BPMNTimerConverter());

    // Act and Assert
    assertFalse(toTimerExecutedConverter.from(new ActivitiEntityEventImpl("Entity", ActivitiEventType.ENTITY_CREATED))
        .isPresent());
  }
}
