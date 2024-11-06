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
import org.activiti.api.process.model.ProcessCandidateStarterGroup;
import org.activiti.api.process.model.events.ProcessCandidateStarterGroupEvent;
import org.activiti.api.runtime.model.impl.ProcessCandidateStarterGroupImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProcessCandidateStarterGroupAddedEventImplDiffblueTest {
  /**
   * Test
   * {@link ProcessCandidateStarterGroupAddedEventImpl#ProcessCandidateStarterGroupAddedEventImpl()}.
   * <p>
   * Method under test:
   * {@link ProcessCandidateStarterGroupAddedEventImpl#ProcessCandidateStarterGroupAddedEventImpl()}
   */
  @Test
  @DisplayName("Test new ProcessCandidateStarterGroupAddedEventImpl()")
  void testNewProcessCandidateStarterGroupAddedEventImpl() {
    // Arrange and Act
    ProcessCandidateStarterGroupAddedEventImpl actualProcessCandidateStarterGroupAddedEventImpl = new ProcessCandidateStarterGroupAddedEventImpl();

    // Assert
    assertNull(actualProcessCandidateStarterGroupAddedEventImpl.getProcessDefinitionVersion());
    assertNull(actualProcessCandidateStarterGroupAddedEventImpl.getBusinessKey());
    assertNull(actualProcessCandidateStarterGroupAddedEventImpl.getParentProcessInstanceId());
    assertNull(actualProcessCandidateStarterGroupAddedEventImpl.getProcessDefinitionId());
    assertNull(actualProcessCandidateStarterGroupAddedEventImpl.getProcessDefinitionKey());
    assertNull(actualProcessCandidateStarterGroupAddedEventImpl.getProcessInstanceId());
    assertNull(actualProcessCandidateStarterGroupAddedEventImpl.getEntity());
    assertEquals(
        ProcessCandidateStarterGroupEvent.ProcessCandidateStarterGroupEvents.PROCESS_CANDIDATE_STARTER_GROUP_ADDED,
        actualProcessCandidateStarterGroupAddedEventImpl.getEventType());
  }

  /**
   * Test
   * {@link ProcessCandidateStarterGroupAddedEventImpl#ProcessCandidateStarterGroupAddedEventImpl(ProcessCandidateStarterGroup)}.
   * <p>
   * Method under test:
   * {@link ProcessCandidateStarterGroupAddedEventImpl#ProcessCandidateStarterGroupAddedEventImpl(ProcessCandidateStarterGroup)}
   */
  @Test
  @DisplayName("Test new ProcessCandidateStarterGroupAddedEventImpl(ProcessCandidateStarterGroup)")
  void testNewProcessCandidateStarterGroupAddedEventImpl2() {
    // Arrange
    ProcessCandidateStarterGroupImpl entity = new ProcessCandidateStarterGroupImpl("42", "42");

    // Act
    ProcessCandidateStarterGroupAddedEventImpl actualProcessCandidateStarterGroupAddedEventImpl = new ProcessCandidateStarterGroupAddedEventImpl(
        entity);

    // Assert
    assertNull(actualProcessCandidateStarterGroupAddedEventImpl.getProcessDefinitionVersion());
    assertNull(actualProcessCandidateStarterGroupAddedEventImpl.getBusinessKey());
    assertNull(actualProcessCandidateStarterGroupAddedEventImpl.getParentProcessInstanceId());
    assertNull(actualProcessCandidateStarterGroupAddedEventImpl.getProcessDefinitionId());
    assertNull(actualProcessCandidateStarterGroupAddedEventImpl.getProcessDefinitionKey());
    assertNull(actualProcessCandidateStarterGroupAddedEventImpl.getProcessInstanceId());
    assertEquals(
        ProcessCandidateStarterGroupEvent.ProcessCandidateStarterGroupEvents.PROCESS_CANDIDATE_STARTER_GROUP_ADDED,
        actualProcessCandidateStarterGroupAddedEventImpl.getEventType());
    assertSame(entity, actualProcessCandidateStarterGroupAddedEventImpl.getEntity());
  }

  /**
   * Test {@link ProcessCandidateStarterGroupAddedEventImpl#getEventType()}.
   * <p>
   * Method under test:
   * {@link ProcessCandidateStarterGroupAddedEventImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType()")
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(
        ProcessCandidateStarterGroupEvent.ProcessCandidateStarterGroupEvents.PROCESS_CANDIDATE_STARTER_GROUP_ADDED,
        (new ProcessCandidateStarterGroupAddedEventImpl()).getEventType());
  }
}
