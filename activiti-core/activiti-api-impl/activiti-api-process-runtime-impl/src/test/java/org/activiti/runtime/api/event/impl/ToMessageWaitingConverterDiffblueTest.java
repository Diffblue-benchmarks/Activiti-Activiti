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
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.Optional;
import org.activiti.api.process.model.events.BPMNMessageEvent;
import org.activiti.api.process.model.events.BPMNMessageEvent.MessageEvents;
import org.activiti.api.process.model.events.BPMNMessageWaitingEvent;
import org.activiti.api.runtime.event.impl.BPMNMessageWaitingEventImpl;
import org.activiti.api.runtime.model.impl.BPMNMessageImpl;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.ActivitiMessageEvent;
import org.activiti.engine.delegate.event.impl.ActivitiMessageEventImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ToMessageWaitingConverterDiffblueTest {
  /**
   * Test {@link ToMessageWaitingConverter#from(ActivitiMessageEvent)} with {@code ActivitiMessageEvent}.
   * <ul>
   *   <li>Then {@link Optional#get()} return {@link BPMNMessageWaitingEventImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ToMessageWaitingConverter#from(ActivitiMessageEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiMessageEvent) with 'ActivitiMessageEvent'; then get() return BPMNMessageWaitingEventImpl")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ToMessageWaitingConverter.from(ActivitiMessageEvent)"})
  void testFromWithActivitiMessageEvent_thenGetReturnBPMNMessageWaitingEventImpl() {
    // Arrange
    ToMessageWaitingConverter toMessageWaitingConverter = new ToMessageWaitingConverter(new BPMNMessageConverter());

    // Act
    Optional<BPMNMessageWaitingEvent> actualFromResult = toMessageWaitingConverter
        .from(new ActivitiMessageEventImpl(ActivitiEventType.ENTITY_CREATED));

    // Assert
    BPMNMessageWaitingEvent getResult = actualFromResult.get();
    assertTrue(getResult instanceof BPMNMessageWaitingEventImpl);
    assertTrue(getResult.getEntity() instanceof BPMNMessageImpl);
    assertNull(getResult.getProcessDefinitionVersion());
    assertNull(getResult.getBusinessKey());
    assertNull(getResult.getParentProcessInstanceId());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionKey());
    assertNull(getResult.getProcessInstanceId());
    assertEquals(MessageEvents.MESSAGE_WAITING, getResult.getEventType());
    assertTrue(actualFromResult.isPresent());
  }
}
