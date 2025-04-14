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
import java.util.HashMap;
import java.util.Optional;
import org.activiti.api.process.model.events.BPMNSignalEvent;
import org.activiti.api.process.model.events.BPMNSignalEvent.SignalEvents;
import org.activiti.api.process.model.events.BPMNSignalReceivedEvent;
import org.activiti.api.runtime.event.impl.BPMNSignalReceivedEventImpl;
import org.activiti.api.runtime.model.impl.BPMNSignalImpl;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.ActivitiSignalEvent;
import org.activiti.engine.delegate.event.impl.ActivitiSignalEventImpl;
import org.activiti.runtime.api.model.impl.ToSignalConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ToSignalReceivedConverterDiffblueTest {
  /**
   * Test {@link ToSignalReceivedConverter#from(ActivitiSignalEvent)} with {@code ActivitiSignalEvent}.
   * <p>
   * Method under test: {@link ToSignalReceivedConverter#from(ActivitiSignalEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiSignalEvent) with 'ActivitiSignalEvent'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ToSignalReceivedConverter.from(ActivitiSignalEvent)"})
  void testFromWithActivitiSignalEvent() {
    // Arrange
    ToSignalReceivedConverter toSignalReceivedConverter = new ToSignalReceivedConverter(new ToSignalConverter());

    // Act
    Optional<BPMNSignalReceivedEvent> actualFromResult = toSignalReceivedConverter
        .from(new ActivitiSignalEventImpl(ActivitiEventType.ENTITY_CREATED));

    // Assert
    BPMNSignalReceivedEvent getResult = actualFromResult.get();
    assertTrue(getResult instanceof BPMNSignalReceivedEventImpl);
    assertTrue(getResult.getEntity() instanceof BPMNSignalImpl);
    assertNull(getResult.getProcessDefinitionVersion());
    assertNull(getResult.getBusinessKey());
    assertNull(getResult.getParentProcessInstanceId());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionKey());
    assertNull(getResult.getProcessInstanceId());
    assertEquals(SignalEvents.SIGNAL_RECEIVED, getResult.getEventType());
    assertTrue(actualFromResult.isPresent());
  }

  /**
   * Test {@link ToSignalReceivedConverter#from(ActivitiSignalEvent)} with {@code ActivitiSignalEvent}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ToSignalReceivedConverter#from(ActivitiSignalEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiSignalEvent) with 'ActivitiSignalEvent'; given HashMap()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ToSignalReceivedConverter.from(ActivitiSignalEvent)"})
  void testFromWithActivitiSignalEvent_givenHashMap() {
    // Arrange
    ToSignalReceivedConverter toSignalReceivedConverter = new ToSignalReceivedConverter(new ToSignalConverter());

    ActivitiSignalEventImpl internalEvent = new ActivitiSignalEventImpl(ActivitiEventType.ENTITY_CREATED);
    internalEvent.setSignalData(new HashMap<>());

    // Act
    Optional<BPMNSignalReceivedEvent> actualFromResult = toSignalReceivedConverter.from(internalEvent);

    // Assert
    BPMNSignalReceivedEvent getResult = actualFromResult.get();
    assertTrue(getResult instanceof BPMNSignalReceivedEventImpl);
    assertTrue(getResult.getEntity() instanceof BPMNSignalImpl);
    assertNull(getResult.getProcessDefinitionVersion());
    assertNull(getResult.getBusinessKey());
    assertNull(getResult.getParentProcessInstanceId());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionKey());
    assertNull(getResult.getProcessInstanceId());
    assertEquals(SignalEvents.SIGNAL_RECEIVED, getResult.getEventType());
    assertTrue(actualFromResult.isPresent());
  }
}
