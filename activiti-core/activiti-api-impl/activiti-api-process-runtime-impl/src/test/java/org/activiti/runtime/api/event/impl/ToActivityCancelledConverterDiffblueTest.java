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
import java.util.Optional;
import org.activiti.api.process.model.BPMNActivity;
import org.activiti.api.process.model.events.BPMNActivityCancelledEvent;
import org.activiti.api.process.model.events.BPMNActivityEvent;
import org.activiti.api.runtime.event.impl.BPMNActivityCancelledEventImpl;
import org.activiti.api.runtime.model.impl.BPMNActivityImpl;
import org.activiti.engine.delegate.event.ActivitiActivityEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiActivityCancelledEventImpl;
import org.activiti.runtime.api.model.impl.ToActivityConverter;
import org.junit.jupiter.api.Test;

class ToActivityCancelledConverterDiffblueTest {
  /**
   * Method under test:
   * {@link ToActivityCancelledConverter#from(ActivitiActivityEvent)}
   */
  @Test
  void testFrom() {
    // Arrange
    ToActivityCancelledConverter toActivityCancelledConverter = new ToActivityCancelledConverter(
        new ToActivityConverter());

    // Act and Assert
    assertFalse(toActivityCancelledConverter.from(new ActivitiActivityCancelledEventImpl()).isPresent());
  }

  /**
   * Method under test:
   * {@link ToActivityCancelledConverter#from(ActivitiActivityEvent)}
   */
  @Test
  void testFrom2() {
    // Arrange
    ToActivityCancelledConverter toActivityCancelledConverter = new ToActivityCancelledConverter(
        new ToActivityConverter());

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
   * Method under test:
   * {@link ToActivityCancelledConverter#from(ActivitiActivityEvent)}
   */
  @Test
  void testFrom3() {
    // Arrange
    ToActivityCancelledConverter toActivityCancelledConverter = new ToActivityCancelledConverter(
        new ToActivityConverter());

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
    internalEvent.setActivityId("Internal Event");

    // Act
    Optional<BPMNActivityCancelledEvent> actualFromResult = toActivityCancelledConverter.from(internalEvent);

    // Assert
    BPMNActivityCancelledEvent getResult = actualFromResult.get();
    assertTrue(getResult instanceof BPMNActivityCancelledEventImpl);
    BPMNActivity entity = getResult.getEntity();
    assertTrue(entity instanceof BPMNActivityImpl);
    assertEquals("42", entity.getExecutionId());
    assertEquals("42", entity.getProcessDefinitionId());
    assertEquals("42", entity.getProcessInstanceId());
    assertEquals("Activity Name", entity.getActivityName());
    assertEquals("Activity Type", entity.getActivityType());
    assertEquals("Internal Event", entity.getElementId());
    assertNull(getResult.getProcessDefinitionVersion());
    assertNull(getResult.getBusinessKey());
    assertNull(getResult.getParentProcessInstanceId());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionKey());
    assertNull(getResult.getProcessInstanceId());
    assertEquals(BPMNActivityEvent.ActivityEvents.ACTIVITY_CANCELLED, getResult.getEventType());
    assertTrue(actualFromResult.isPresent());
  }
}
