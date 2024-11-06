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

class ProcessCandidateStarterGroupRemovedEventImplDiffblueTest {
  /**
   * Test
   * {@link ProcessCandidateStarterGroupRemovedEventImpl#ProcessCandidateStarterGroupRemovedEventImpl()}.
   * <p>
   * Method under test:
   * {@link ProcessCandidateStarterGroupRemovedEventImpl#ProcessCandidateStarterGroupRemovedEventImpl()}
   */
  @Test
  @DisplayName("Test new ProcessCandidateStarterGroupRemovedEventImpl()")
  void testNewProcessCandidateStarterGroupRemovedEventImpl() {
    // Arrange and Act
    ProcessCandidateStarterGroupRemovedEventImpl actualProcessCandidateStarterGroupRemovedEventImpl = new ProcessCandidateStarterGroupRemovedEventImpl();

    // Assert
    assertNull(actualProcessCandidateStarterGroupRemovedEventImpl.getProcessDefinitionVersion());
    assertNull(actualProcessCandidateStarterGroupRemovedEventImpl.getBusinessKey());
    assertNull(actualProcessCandidateStarterGroupRemovedEventImpl.getParentProcessInstanceId());
    assertNull(actualProcessCandidateStarterGroupRemovedEventImpl.getProcessDefinitionId());
    assertNull(actualProcessCandidateStarterGroupRemovedEventImpl.getProcessDefinitionKey());
    assertNull(actualProcessCandidateStarterGroupRemovedEventImpl.getProcessInstanceId());
    assertNull(actualProcessCandidateStarterGroupRemovedEventImpl.getEntity());
    assertEquals(
        ProcessCandidateStarterGroupEvent.ProcessCandidateStarterGroupEvents.PROCESS_CANDIDATE_STARTER_GROUP_REMOVED,
        actualProcessCandidateStarterGroupRemovedEventImpl.getEventType());
  }

  /**
   * Test
   * {@link ProcessCandidateStarterGroupRemovedEventImpl#ProcessCandidateStarterGroupRemovedEventImpl(ProcessCandidateStarterGroup)}.
   * <p>
   * Method under test:
   * {@link ProcessCandidateStarterGroupRemovedEventImpl#ProcessCandidateStarterGroupRemovedEventImpl(ProcessCandidateStarterGroup)}
   */
  @Test
  @DisplayName("Test new ProcessCandidateStarterGroupRemovedEventImpl(ProcessCandidateStarterGroup)")
  void testNewProcessCandidateStarterGroupRemovedEventImpl2() {
    // Arrange
    ProcessCandidateStarterGroupImpl entity = new ProcessCandidateStarterGroupImpl("42", "42");

    // Act
    ProcessCandidateStarterGroupRemovedEventImpl actualProcessCandidateStarterGroupRemovedEventImpl = new ProcessCandidateStarterGroupRemovedEventImpl(
        entity);

    // Assert
    assertNull(actualProcessCandidateStarterGroupRemovedEventImpl.getProcessDefinitionVersion());
    assertNull(actualProcessCandidateStarterGroupRemovedEventImpl.getBusinessKey());
    assertNull(actualProcessCandidateStarterGroupRemovedEventImpl.getParentProcessInstanceId());
    assertNull(actualProcessCandidateStarterGroupRemovedEventImpl.getProcessDefinitionId());
    assertNull(actualProcessCandidateStarterGroupRemovedEventImpl.getProcessDefinitionKey());
    assertNull(actualProcessCandidateStarterGroupRemovedEventImpl.getProcessInstanceId());
    assertEquals(
        ProcessCandidateStarterGroupEvent.ProcessCandidateStarterGroupEvents.PROCESS_CANDIDATE_STARTER_GROUP_REMOVED,
        actualProcessCandidateStarterGroupRemovedEventImpl.getEventType());
    assertSame(entity, actualProcessCandidateStarterGroupRemovedEventImpl.getEntity());
  }

  /**
   * Test {@link ProcessCandidateStarterGroupRemovedEventImpl#getEventType()}.
   * <p>
   * Method under test:
   * {@link ProcessCandidateStarterGroupRemovedEventImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType()")
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(
        ProcessCandidateStarterGroupEvent.ProcessCandidateStarterGroupEvents.PROCESS_CANDIDATE_STARTER_GROUP_REMOVED,
        (new ProcessCandidateStarterGroupRemovedEventImpl()).getEventType());
  }
}
