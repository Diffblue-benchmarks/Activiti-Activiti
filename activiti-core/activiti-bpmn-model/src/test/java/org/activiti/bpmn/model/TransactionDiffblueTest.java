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

public class TransactionDiffblueTest {
  /**
   * Test new {@link Transaction} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link Transaction}
   */
  @Test
  public void testNewTransaction() {
    // Arrange and Act
    Transaction actualTransaction = new Transaction();

    // Assert
    assertTrue(actualTransaction.getArtifacts() instanceof List);
    assertTrue(actualTransaction.getFlowElements() instanceof List);
    assertNull(actualTransaction.getBehavior());
    assertNull(actualTransaction.getDefaultFlow());
    assertNull(actualTransaction.getFailedJobRetryTimeCycleValue());
    assertNull(actualTransaction.getId());
    assertNull(actualTransaction.getDocumentation());
    assertNull(actualTransaction.getName());
    assertNull(actualTransaction.getParentContainer());
    assertNull(actualTransaction.getIoSpecification());
    assertNull(actualTransaction.getLoopCharacteristics());
    assertEquals(0, actualTransaction.getXmlColumnNumber());
    assertEquals(0, actualTransaction.getXmlRowNumber());
    assertFalse(actualTransaction.isForCompensation());
    assertFalse(actualTransaction.isAsynchronous());
    assertFalse(actualTransaction.isNotExclusive());
    assertTrue(actualTransaction.getBoundaryEvents().isEmpty());
    assertTrue(actualTransaction.getDataInputAssociations().isEmpty());
    assertTrue(actualTransaction.getDataOutputAssociations().isEmpty());
    assertTrue(actualTransaction.getMapExceptions().isEmpty());
    assertTrue(actualTransaction.getExecutionListeners().isEmpty());
    assertTrue(actualTransaction.getIncomingFlows().isEmpty());
    assertTrue(actualTransaction.getOutgoingFlows().isEmpty());
    assertTrue(actualTransaction.getDataObjects().isEmpty());
    assertTrue(actualTransaction.getAttributes().isEmpty());
    assertTrue(actualTransaction.getExtensionElements().isEmpty());
    assertTrue(actualTransaction.getFlowElementMap().isEmpty());
  }
}
