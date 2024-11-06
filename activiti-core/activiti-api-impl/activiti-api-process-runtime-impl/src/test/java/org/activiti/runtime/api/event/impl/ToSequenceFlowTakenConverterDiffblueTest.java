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
import org.activiti.api.process.model.BPMNSequenceFlow;
import org.activiti.api.process.model.events.BPMNSequenceFlowTakenEvent;
import org.activiti.api.process.model.events.SequenceFlowEvent;
import org.activiti.api.runtime.event.impl.BPMNSequenceFlowTakenImpl;
import org.activiti.api.runtime.model.impl.BPMNSequenceFlowImpl;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.ActivitiSequenceFlowTakenEvent;
import org.activiti.engine.delegate.event.impl.ActivitiSequenceFlowTakenEventImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ToSequenceFlowTakenConverterDiffblueTest {
  /**
   * Test
   * {@link ToSequenceFlowTakenConverter#from(ActivitiSequenceFlowTakenEvent)}
   * with {@code ActivitiSequenceFlowTakenEvent}.
   * <p>
   * Method under test:
   * {@link ToSequenceFlowTakenConverter#from(ActivitiSequenceFlowTakenEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiSequenceFlowTakenEvent) with 'ActivitiSequenceFlowTakenEvent'")
  void testFromWithActivitiSequenceFlowTakenEvent() {
    // Arrange
    ToSequenceFlowTakenConverter toSequenceFlowTakenConverter = new ToSequenceFlowTakenConverter();

    // Act
    Optional<BPMNSequenceFlowTakenEvent> actualFromResult = toSequenceFlowTakenConverter
        .from(new ActivitiSequenceFlowTakenEventImpl(ActivitiEventType.ENTITY_CREATED));

    // Assert
    BPMNSequenceFlowTakenEvent getResult = actualFromResult.get();
    assertTrue(getResult instanceof BPMNSequenceFlowTakenImpl);
    BPMNSequenceFlow entity = getResult.getEntity();
    assertTrue(entity instanceof BPMNSequenceFlowImpl);
    assertNull(getResult.getProcessDefinitionVersion());
    assertNull(getResult.getBusinessKey());
    assertNull(getResult.getParentProcessInstanceId());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionKey());
    assertNull(getResult.getProcessInstanceId());
    assertNull(entity.getElementId());
    assertNull(entity.getProcessDefinitionId());
    assertNull(entity.getProcessInstanceId());
    assertNull(entity.getSourceActivityElementId());
    assertNull(entity.getSourceActivityName());
    assertNull(entity.getSourceActivityType());
    assertNull(entity.getTargetActivityElementId());
    assertNull(entity.getTargetActivityName());
    assertNull(entity.getTargetActivityType());
    assertEquals(SequenceFlowEvent.SequenceFlowEvents.SEQUENCE_FLOW_TAKEN, getResult.getEventType());
    assertTrue(actualFromResult.isPresent());
  }
}
