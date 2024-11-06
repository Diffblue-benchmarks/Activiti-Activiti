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
package org.activiti.api.runtime.event.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.activiti.api.process.model.BPMNSequenceFlow;
import org.activiti.api.process.model.events.SequenceFlowEvent;
import org.activiti.api.runtime.model.impl.BPMNSequenceFlowImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BPMNSequenceFlowTakenImplDiffblueTest {
  /**
   * Test {@link BPMNSequenceFlowTakenImpl#BPMNSequenceFlowTakenImpl()}.
   * <p>
   * Method under test:
   * {@link BPMNSequenceFlowTakenImpl#BPMNSequenceFlowTakenImpl()}
   */
  @Test
  @DisplayName("Test new BPMNSequenceFlowTakenImpl()")
  void testNewBPMNSequenceFlowTakenImpl() {
    // Arrange and Act
    BPMNSequenceFlowTakenImpl actualBpmnSequenceFlowTakenImpl = new BPMNSequenceFlowTakenImpl();

    // Assert
    assertNull(actualBpmnSequenceFlowTakenImpl.getProcessDefinitionVersion());
    assertNull(actualBpmnSequenceFlowTakenImpl.getBusinessKey());
    assertNull(actualBpmnSequenceFlowTakenImpl.getParentProcessInstanceId());
    assertNull(actualBpmnSequenceFlowTakenImpl.getProcessDefinitionId());
    assertNull(actualBpmnSequenceFlowTakenImpl.getProcessDefinitionKey());
    assertNull(actualBpmnSequenceFlowTakenImpl.getProcessInstanceId());
    assertNull(actualBpmnSequenceFlowTakenImpl.getEntity());
    assertEquals(SequenceFlowEvent.SequenceFlowEvents.SEQUENCE_FLOW_TAKEN,
        actualBpmnSequenceFlowTakenImpl.getEventType());
  }

  /**
   * Test
   * {@link BPMNSequenceFlowTakenImpl#BPMNSequenceFlowTakenImpl(BPMNSequenceFlow)}.
   * <p>
   * Method under test:
   * {@link BPMNSequenceFlowTakenImpl#BPMNSequenceFlowTakenImpl(BPMNSequenceFlow)}
   */
  @Test
  @DisplayName("Test new BPMNSequenceFlowTakenImpl(BPMNSequenceFlow)")
  void testNewBPMNSequenceFlowTakenImpl2() {
    // Arrange
    BPMNSequenceFlowImpl entity = new BPMNSequenceFlowImpl("42", "42", "42");

    // Act
    BPMNSequenceFlowTakenImpl actualBpmnSequenceFlowTakenImpl = new BPMNSequenceFlowTakenImpl(entity);

    // Assert
    assertNull(actualBpmnSequenceFlowTakenImpl.getProcessDefinitionVersion());
    assertNull(actualBpmnSequenceFlowTakenImpl.getBusinessKey());
    assertNull(actualBpmnSequenceFlowTakenImpl.getParentProcessInstanceId());
    assertNull(actualBpmnSequenceFlowTakenImpl.getProcessDefinitionId());
    assertNull(actualBpmnSequenceFlowTakenImpl.getProcessDefinitionKey());
    assertNull(actualBpmnSequenceFlowTakenImpl.getProcessInstanceId());
    assertEquals(SequenceFlowEvent.SequenceFlowEvents.SEQUENCE_FLOW_TAKEN,
        actualBpmnSequenceFlowTakenImpl.getEventType());
    assertSame(entity, actualBpmnSequenceFlowTakenImpl.getEntity());
  }

  /**
   * Test {@link BPMNSequenceFlowTakenImpl#getEventType()}.
   * <p>
   * Method under test: {@link BPMNSequenceFlowTakenImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType()")
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(SequenceFlowEvent.SequenceFlowEvents.SEQUENCE_FLOW_TAKEN,
        (new BPMNSequenceFlowTakenImpl()).getEventType());
  }
}
