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
import java.util.Optional;
import org.activiti.api.process.model.events.BPMNActivityCancelledEvent;
import org.activiti.api.process.model.events.BPMNActivityEvent;
import org.activiti.api.process.model.events.BPMNActivityEvent.ActivityEvents;
import org.activiti.api.runtime.event.impl.BPMNActivityCancelledEventImpl;
import org.activiti.api.runtime.model.impl.BPMNActivityImpl;
import org.activiti.engine.delegate.event.ActivitiActivityEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiActivityCancelledEventImpl;
import org.activiti.runtime.api.model.impl.ToActivityConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ToActivityCancelledConverterDiffblueTest {
  /**
   * Test {@link ToActivityCancelledConverter#from(ActivitiActivityEvent)} with {@code
   * ActivitiActivityEvent}.
   *
   * <ul>
   *   <li>Given {@code Activity Name}.
   * </ul>
   *
   * <p>Method under test: {@link ToActivityCancelledConverter#from(ActivitiActivityEvent)}
   */
  @Test
  @DisplayName(
      "Test from(ActivitiActivityEvent) with 'ActivitiActivityEvent'; given 'Activity Name'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional ToActivityCancelledConverter.from(ActivitiActivityEvent)"})
  void testFromWithActivitiActivityEvent_givenActivityName() {
    // Arrange
    ToActivityCancelledConverter toActivityCancelledConverter =
        new ToActivityCancelledConverter(new ToActivityConverter());

    ActivitiActivityCancelledEventImpl internalEvent = new ActivitiActivityCancelledEventImpl();
    internalEvent.setActivityName("Activity Name");
    internalEvent.setActivityType("Activity Type");
    internalEvent.setBehaviorClass("Behavior Class");
    internalEvent.setCause("Cause");
    internalEvent.setExecutionId("42");
    internalEvent.setProcessDefinitionId("42");
    internalEvent.setProcessInstanceId("42");
    internalEvent.setReason("Just cause");
    internalEvent.setType(ActivitiEventType.ENTITY_CREATED);
    internalEvent.setActivityId("");

    // Act and Assert
    assertFalse(toActivityCancelledConverter.from(internalEvent).isPresent());
  }

  /**
   * Test {@link ToActivityCancelledConverter#from(ActivitiActivityEvent)} with {@code
   * ActivitiActivityEvent}.
   *
   * <ul>
   *   <li>Then {@link Optional#get()} return {@link BPMNActivityCancelledEventImpl}.
   * </ul>
   *
   * <p>Method under test: {@link ToActivityCancelledConverter#from(ActivitiActivityEvent)}
   */
  @Test
  @DisplayName(
      "Test from(ActivitiActivityEvent) with 'ActivitiActivityEvent'; then get() return BPMNActivityCancelledEventImpl")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional ToActivityCancelledConverter.from(ActivitiActivityEvent)"})
  void testFromWithActivitiActivityEvent_thenGetReturnBPMNActivityCancelledEventImpl() {
    // Arrange
    ToActivityCancelledConverter toActivityCancelledConverter =
        new ToActivityCancelledConverter(new ToActivityConverter());

    ActivitiActivityCancelledEventImpl internalEvent = new ActivitiActivityCancelledEventImpl();
    internalEvent.setActivityId("42");

    // Act
    Optional<BPMNActivityCancelledEvent> actualFromResult =
        toActivityCancelledConverter.from(internalEvent);

    // Assert
    BPMNActivityCancelledEvent getResult = actualFromResult.get();
    assertTrue(getResult instanceof BPMNActivityCancelledEventImpl);
    assertTrue(getResult.getEntity() instanceof BPMNActivityImpl);
    assertNull(getResult.getProcessDefinitionVersion());
    assertNull(getResult.getBusinessKey());
    assertNull(getResult.getParentProcessInstanceId());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionKey());
    assertNull(getResult.getProcessInstanceId());
    assertEquals(ActivityEvents.ACTIVITY_CANCELLED, getResult.getEventType());
    assertTrue(actualFromResult.isPresent());
  }

  /**
   * Test {@link ToActivityCancelledConverter#from(ActivitiActivityEvent)} with {@code
   * ActivitiActivityEvent}.
   *
   * <ul>
   *   <li>When {@link ActivitiActivityCancelledEventImpl} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ToActivityCancelledConverter#from(ActivitiActivityEvent)}
   */
  @Test
  @DisplayName(
      "Test from(ActivitiActivityEvent) with 'ActivitiActivityEvent'; when ActivitiActivityCancelledEventImpl (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional ToActivityCancelledConverter.from(ActivitiActivityEvent)"})
  void testFromWithActivitiActivityEvent_whenActivitiActivityCancelledEventImpl() {
    // Arrange
    ToActivityCancelledConverter toActivityCancelledConverter =
        new ToActivityCancelledConverter(new ToActivityConverter());

    // Act and Assert
    assertFalse(
        toActivityCancelledConverter.from(new ActivitiActivityCancelledEventImpl()).isPresent());
  }
}
