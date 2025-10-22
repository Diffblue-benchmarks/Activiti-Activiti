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

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import org.activiti.api.process.model.BPMNSignal;
import org.activiti.api.process.model.payloads.SignalPayload;
import org.activiti.api.runtime.model.impl.BPMNSignalImpl;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.ActivitiSignalEvent;
import org.activiti.engine.delegate.event.impl.ActivitiSignalEventImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
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
   *   <li>Then return SignalPayload Variables Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ToSignalConverter#from(ActivitiSignalEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiSignalEvent); given HashMap(); then return SignalPayload Variables Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BPMNSignal ToSignalConverter.from(ActivitiSignalEvent)"})
  void testFrom_givenHashMap_thenReturnSignalPayloadVariablesEmpty() {
    // Arrange
    ActivitiSignalEventImpl internalEvent = new ActivitiSignalEventImpl(ActivitiEventType.ENTITY_CREATED);
    internalEvent.setSignalData(new HashMap<>());

    // Act
    BPMNSignal actualFromResult = toSignalConverter.from(internalEvent);

    // Assert
    assertTrue(actualFromResult instanceof BPMNSignalImpl);
    assertNull(actualFromResult.getElementId());
    assertNull(actualFromResult.getProcessDefinitionId());
    assertNull(actualFromResult.getProcessInstanceId());
    SignalPayload signalPayload = actualFromResult.getSignalPayload();
    assertNull(signalPayload.getName());
    assertTrue(signalPayload.getVariables().isEmpty());
  }

  /**
   * Test {@link ToSignalConverter#from(ActivitiSignalEvent)}.
   * <ul>
   *   <li>Then return SignalPayload Variables is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ToSignalConverter#from(ActivitiSignalEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiSignalEvent); then return SignalPayload Variables is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BPMNSignal ToSignalConverter.from(ActivitiSignalEvent)"})
  void testFrom_thenReturnSignalPayloadVariablesIsNull() {
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
