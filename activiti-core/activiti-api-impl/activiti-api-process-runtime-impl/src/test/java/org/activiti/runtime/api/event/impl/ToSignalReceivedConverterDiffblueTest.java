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
import java.util.HashMap;
import java.util.Optional;
import org.activiti.api.process.model.BPMNSignal;
import org.activiti.api.process.model.events.BPMNSignalEvent;
import org.activiti.api.process.model.events.BPMNSignalReceivedEvent;
import org.activiti.api.process.model.payloads.SignalPayload;
import org.activiti.api.runtime.event.impl.BPMNSignalReceivedEventImpl;
import org.activiti.api.runtime.model.impl.BPMNSignalImpl;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.ActivitiSignalEvent;
import org.activiti.engine.delegate.event.impl.ActivitiSignalEventImpl;
import org.activiti.runtime.api.model.impl.ToSignalConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ToSignalReceivedConverterDiffblueTest {
  /**
   * Test {@link ToSignalReceivedConverter#from(ActivitiSignalEvent)} with
   * {@code ActivitiSignalEvent}.
   * <ul>
   *   <li>Then return {@link Optional#get()} Entity SignalPayload Variables
   * Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ToSignalReceivedConverter#from(ActivitiSignalEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiSignalEvent) with 'ActivitiSignalEvent'; then return get() Entity SignalPayload Variables Empty")
  void testFromWithActivitiSignalEvent_thenReturnGetEntitySignalPayloadVariablesEmpty() {
    // Arrange
    ToSignalReceivedConverter toSignalReceivedConverter = new ToSignalReceivedConverter(new ToSignalConverter());

    ActivitiSignalEventImpl internalEvent = new ActivitiSignalEventImpl(ActivitiEventType.ENTITY_CREATED);
    internalEvent.setSignalData(new HashMap<>());

    // Act
    Optional<BPMNSignalReceivedEvent> actualFromResult = toSignalReceivedConverter.from(internalEvent);

    // Assert
    BPMNSignalReceivedEvent getResult = actualFromResult.get();
    assertTrue(getResult instanceof BPMNSignalReceivedEventImpl);
    BPMNSignal entity = getResult.getEntity();
    assertTrue(entity instanceof BPMNSignalImpl);
    assertNull(getResult.getProcessDefinitionVersion());
    assertNull(getResult.getBusinessKey());
    assertNull(getResult.getParentProcessInstanceId());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionKey());
    assertNull(getResult.getProcessInstanceId());
    assertNull(entity.getElementId());
    assertNull(entity.getProcessDefinitionId());
    assertNull(entity.getProcessInstanceId());
    SignalPayload signalPayload = entity.getSignalPayload();
    assertNull(signalPayload.getName());
    assertEquals(BPMNSignalEvent.SignalEvents.SIGNAL_RECEIVED, getResult.getEventType());
    assertTrue(signalPayload.getVariables().isEmpty());
    assertTrue(actualFromResult.isPresent());
  }

  /**
   * Test {@link ToSignalReceivedConverter#from(ActivitiSignalEvent)} with
   * {@code ActivitiSignalEvent}.
   * <ul>
   *   <li>Then return {@link Optional#get()} Entity SignalPayload Variables is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ToSignalReceivedConverter#from(ActivitiSignalEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiSignalEvent) with 'ActivitiSignalEvent'; then return get() Entity SignalPayload Variables is 'null'")
  void testFromWithActivitiSignalEvent_thenReturnGetEntitySignalPayloadVariablesIsNull() {
    // Arrange
    ToSignalReceivedConverter toSignalReceivedConverter = new ToSignalReceivedConverter(new ToSignalConverter());

    // Act
    Optional<BPMNSignalReceivedEvent> actualFromResult = toSignalReceivedConverter
        .from(new ActivitiSignalEventImpl(ActivitiEventType.ENTITY_CREATED));

    // Assert
    BPMNSignalReceivedEvent getResult = actualFromResult.get();
    assertTrue(getResult instanceof BPMNSignalReceivedEventImpl);
    BPMNSignal entity = getResult.getEntity();
    assertTrue(entity instanceof BPMNSignalImpl);
    assertNull(getResult.getProcessDefinitionVersion());
    assertNull(getResult.getBusinessKey());
    assertNull(getResult.getParentProcessInstanceId());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionKey());
    assertNull(getResult.getProcessInstanceId());
    assertNull(entity.getElementId());
    assertNull(entity.getProcessDefinitionId());
    assertNull(entity.getProcessInstanceId());
    SignalPayload signalPayload = entity.getSignalPayload();
    assertNull(signalPayload.getName());
    assertNull(signalPayload.getVariables());
    assertEquals(BPMNSignalEvent.SignalEvents.SIGNAL_RECEIVED, getResult.getEventType());
    assertTrue(actualFromResult.isPresent());
  }
}
