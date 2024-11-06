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
package org.activiti.bpmn.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.Test;

public class EventSubProcessDiffblueTest {
  /**
   * Test new {@link EventSubProcess} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link EventSubProcess}
   */
  @Test
  public void testNewEventSubProcess() {
    // Arrange and Act
    EventSubProcess actualEventSubProcess = new EventSubProcess();

    // Assert
    assertTrue(actualEventSubProcess.getArtifacts() instanceof List);
    assertTrue(actualEventSubProcess.getFlowElements() instanceof List);
    assertNull(actualEventSubProcess.getBehavior());
    assertNull(actualEventSubProcess.getDefaultFlow());
    assertNull(actualEventSubProcess.getFailedJobRetryTimeCycleValue());
    assertNull(actualEventSubProcess.getId());
    assertNull(actualEventSubProcess.getDocumentation());
    assertNull(actualEventSubProcess.getName());
    assertNull(actualEventSubProcess.getParentContainer());
    assertNull(actualEventSubProcess.getIoSpecification());
    assertNull(actualEventSubProcess.getLoopCharacteristics());
    assertEquals(0, actualEventSubProcess.getXmlColumnNumber());
    assertEquals(0, actualEventSubProcess.getXmlRowNumber());
    assertFalse(actualEventSubProcess.isForCompensation());
    assertFalse(actualEventSubProcess.isAsynchronous());
    assertFalse(actualEventSubProcess.isNotExclusive());
    assertTrue(actualEventSubProcess.getBoundaryEvents().isEmpty());
    assertTrue(actualEventSubProcess.getDataInputAssociations().isEmpty());
    assertTrue(actualEventSubProcess.getDataOutputAssociations().isEmpty());
    assertTrue(actualEventSubProcess.getMapExceptions().isEmpty());
    assertTrue(actualEventSubProcess.getExecutionListeners().isEmpty());
    assertTrue(actualEventSubProcess.getIncomingFlows().isEmpty());
    assertTrue(actualEventSubProcess.getOutgoingFlows().isEmpty());
    assertTrue(actualEventSubProcess.getDataObjects().isEmpty());
    assertTrue(actualEventSubProcess.getAttributes().isEmpty());
    assertTrue(actualEventSubProcess.getExtensionElements().isEmpty());
    assertTrue(actualEventSubProcess.getFlowElementMap().isEmpty());
  }
}
