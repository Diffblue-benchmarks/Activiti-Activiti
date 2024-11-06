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
package org.activiti.runtime.api.model.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import org.activiti.api.process.model.BPMNSignal;
import org.activiti.api.process.model.payloads.SignalPayload;
import org.activiti.api.runtime.model.impl.BPMNSignalImpl;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.ActivitiSignalEvent;
import org.activiti.engine.delegate.event.impl.ActivitiSignalEventImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ToSignalConverter.class})
@ExtendWith(SpringExtension.class)
class ToSignalConverterDiffblueTest {
  @Autowired
  private ToSignalConverter toSignalConverter;

  /**
   * Test {@link ToSignalConverter#from(ActivitiSignalEvent)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.</li>
   *   <li>Then return ElementId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ToSignalConverter#from(ActivitiSignalEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiSignalEvent); given HashMap(); then return ElementId is '42'")
  void testFrom_givenHashMap_thenReturnElementIdIs42() {
    // Arrange
    ActivitiSignalEventImpl internalEvent = mock(ActivitiSignalEventImpl.class);
    when(internalEvent.getSignalData()).thenReturn(new HashMap<>());
    when(internalEvent.getActivityId()).thenReturn("42");
    when(internalEvent.getProcessDefinitionId()).thenReturn("42");
    when(internalEvent.getProcessInstanceId()).thenReturn("42");
    when(internalEvent.getSignalName()).thenReturn("Signal Name");

    // Act
    BPMNSignal actualFromResult = toSignalConverter.from(internalEvent);

    // Assert
    verify(internalEvent).getActivityId();
    verify(internalEvent).getProcessDefinitionId();
    verify(internalEvent).getProcessInstanceId();
    verify(internalEvent, atLeast(1)).getSignalData();
    verify(internalEvent).getSignalName();
    assertTrue(actualFromResult instanceof BPMNSignalImpl);
    assertEquals("42", actualFromResult.getElementId());
    assertEquals("42", actualFromResult.getProcessDefinitionId());
    assertEquals("42", actualFromResult.getProcessInstanceId());
    SignalPayload signalPayload = actualFromResult.getSignalPayload();
    assertEquals("Signal Name", signalPayload.getName());
    assertTrue(signalPayload.getVariables().isEmpty());
  }

  /**
   * Test {@link ToSignalConverter#from(ActivitiSignalEvent)}.
   * <ul>
   *   <li>Then return ElementId is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ToSignalConverter#from(ActivitiSignalEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiSignalEvent); then return ElementId is 'null'")
  void testFrom_thenReturnElementIdIsNull() {
    // Arrange and Act
    BPMNSignal actualFromResult = toSignalConverter.from(new ActivitiSignalEventImpl(ActivitiEventType.ENTITY_CREATED));

    // Assert
    assertTrue(actualFromResult instanceof BPMNSignalImpl);
    assertNull(actualFromResult.getElementId());
    assertNull(actualFromResult.getProcessDefinitionId());
    assertNull(actualFromResult.getProcessInstanceId());
    SignalPayload signalPayload = actualFromResult.getSignalPayload();
    assertNull(signalPayload.getName());
    assertNull(signalPayload.getVariables());
  }
}
