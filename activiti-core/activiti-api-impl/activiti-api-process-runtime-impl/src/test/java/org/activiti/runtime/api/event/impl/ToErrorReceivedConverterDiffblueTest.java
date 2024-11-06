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
import org.activiti.api.process.model.BPMNError;
import org.activiti.api.process.model.events.BPMNErrorReceivedEvent;
import org.activiti.api.runtime.event.impl.BPMNErrorReceivedEventImpl;
import org.activiti.api.runtime.model.impl.BPMNErrorImpl;
import org.activiti.engine.delegate.event.ActivitiErrorEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiErrorEventImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ToErrorReceivedConverterDiffblueTest {
  /**
   * Test {@link ToErrorReceivedConverter#from(ActivitiErrorEvent)} with
   * {@code ActivitiErrorEvent}.
   * <ul>
   *   <li>Then {@link Optional#get()} return
   * {@link BPMNErrorReceivedEventImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ToErrorReceivedConverter#from(ActivitiErrorEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiErrorEvent) with 'ActivitiErrorEvent'; then get() return BPMNErrorReceivedEventImpl")
  void testFromWithActivitiErrorEvent_thenGetReturnBPMNErrorReceivedEventImpl() {
    // Arrange
    ToErrorReceivedConverter toErrorReceivedConverter = new ToErrorReceivedConverter(new BPMNErrorConverter());

    // Act
    Optional<BPMNErrorReceivedEvent> actualFromResult = toErrorReceivedConverter
        .from(new ActivitiErrorEventImpl(ActivitiEventType.ENTITY_CREATED));

    // Assert
    BPMNErrorReceivedEvent getResult = actualFromResult.get();
    assertTrue(getResult instanceof BPMNErrorReceivedEventImpl);
    BPMNError entity = getResult.getEntity();
    assertTrue(entity instanceof BPMNErrorImpl);
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
    assertNull(entity.getErrorCode());
    assertNull(entity.getErrorId());
    assertEquals(BPMNErrorReceivedEvent.ErrorEvents.ERROR_RECEIVED, getResult.getEventType());
    assertTrue(actualFromResult.isPresent());
  }
}
