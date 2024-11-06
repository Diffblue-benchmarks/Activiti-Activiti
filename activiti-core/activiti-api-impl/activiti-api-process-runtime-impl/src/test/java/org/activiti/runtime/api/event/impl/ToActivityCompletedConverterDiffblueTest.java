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
import org.activiti.api.process.model.events.BPMNActivityCompletedEvent;
import org.activiti.api.process.model.events.BPMNActivityEvent;
import org.activiti.api.runtime.event.impl.BPMNActivityCompletedEventImpl;
import org.activiti.api.runtime.model.impl.BPMNActivityImpl;
import org.activiti.engine.delegate.event.ActivitiActivityEvent;
import org.activiti.engine.delegate.event.impl.ActivitiActivityCancelledEventImpl;
import org.activiti.runtime.api.model.impl.ToActivityConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ToActivityCompletedConverterDiffblueTest {
  /**
   * Test {@link ToActivityCompletedConverter#from(ActivitiActivityEvent)} with
   * {@code ActivitiActivityEvent}.
   * <ul>
   *   <li>Then {@link Optional#get()} return
   * {@link BPMNActivityCompletedEventImpl}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ToActivityCompletedConverter#from(ActivitiActivityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiActivityEvent) with 'ActivitiActivityEvent'; then get() return BPMNActivityCompletedEventImpl")
  void testFromWithActivitiActivityEvent_thenGetReturnBPMNActivityCompletedEventImpl() {
    // Arrange
    ToActivityCompletedConverter toActivityCompletedConverter = new ToActivityCompletedConverter(
        new ToActivityConverter());

    // Act
    Optional<BPMNActivityCompletedEvent> actualFromResult = toActivityCompletedConverter
        .from(new ActivitiActivityCancelledEventImpl());

    // Assert
    BPMNActivityCompletedEvent getResult = actualFromResult.get();
    assertTrue(getResult instanceof BPMNActivityCompletedEventImpl);
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
    assertEquals(BPMNActivityEvent.ActivityEvents.ACTIVITY_COMPLETED, getResult.getEventType());
    assertTrue(actualFromResult.isPresent());
  }
}
