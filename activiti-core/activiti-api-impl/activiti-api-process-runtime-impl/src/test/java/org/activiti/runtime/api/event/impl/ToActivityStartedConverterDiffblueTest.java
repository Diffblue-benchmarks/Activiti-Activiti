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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Optional;
import org.activiti.api.process.model.BPMNActivity;
import org.activiti.api.process.model.events.BPMNActivityEvent;
import org.activiti.api.process.model.events.BPMNActivityStartedEvent;
import org.activiti.api.runtime.event.impl.BPMNActivityStartedEventImpl;
import org.activiti.api.runtime.model.impl.BPMNActivityImpl;
import org.activiti.engine.delegate.event.ActivitiActivityEvent;
import org.activiti.engine.delegate.event.impl.ActivitiActivityCancelledEventImpl;
import org.activiti.runtime.api.model.impl.ToActivityConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ToActivityStartedConverterDiffblueTest {
  /**
   * Test {@link ToActivityStartedConverter#from(ActivitiActivityEvent)} with
   * {@code ActivitiActivityEvent}.
   * <ul>
   *   <li>Then {@link Optional#get()} return
   * {@link BPMNActivityStartedEventImpl}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ToActivityStartedConverter#from(ActivitiActivityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiActivityEvent) with 'ActivitiActivityEvent'; then get() return BPMNActivityStartedEventImpl")
  void testFromWithActivitiActivityEvent_thenGetReturnBPMNActivityStartedEventImpl() {
    // Arrange
    ToActivityStartedConverter toActivityStartedConverter = new ToActivityStartedConverter(new ToActivityConverter());

    // Act
    Optional<BPMNActivityStartedEvent> actualFromResult = toActivityStartedConverter
        .from(new ActivitiActivityCancelledEventImpl());

    // Assert
    BPMNActivityStartedEvent getResult = actualFromResult.get();
    assertTrue(getResult instanceof BPMNActivityStartedEventImpl);
    BPMNActivity entity = getResult.getEntity();
    assertTrue(entity instanceof BPMNActivityImpl);
    assertNull(getResult.getProcessDefinitionVersion());
    assertNull(getResult.getBusinessKey());
    assertNull(getResult.getParentProcessInstanceId());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionKey());
    assertNull(getResult.getProcessInstanceId());
    assertNull(entity.getActivityName());
    assertNull(entity.getActivityType());
    assertNull(entity.getExecutionId());
    assertNull(entity.getElementId());
    assertNull(entity.getProcessDefinitionId());
    assertNull(entity.getProcessInstanceId());
    assertEquals(BPMNActivityEvent.ActivityEvents.ACTIVITY_STARTED, getResult.getEventType());
    assertTrue(actualFromResult.isPresent());
  }
}
