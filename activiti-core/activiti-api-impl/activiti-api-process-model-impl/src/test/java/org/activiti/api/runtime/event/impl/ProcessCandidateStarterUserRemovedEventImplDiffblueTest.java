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
import org.activiti.api.process.model.ProcessCandidateStarterUser;
import org.activiti.api.process.model.events.ProcessCandidateStarterUserEvent;
import org.activiti.api.runtime.model.impl.ProcessCandidateStarterUserImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProcessCandidateStarterUserRemovedEventImplDiffblueTest {
  /**
   * Test
   * {@link ProcessCandidateStarterUserRemovedEventImpl#ProcessCandidateStarterUserRemovedEventImpl()}.
   * <p>
   * Method under test:
   * {@link ProcessCandidateStarterUserRemovedEventImpl#ProcessCandidateStarterUserRemovedEventImpl()}
   */
  @Test
  @DisplayName("Test new ProcessCandidateStarterUserRemovedEventImpl()")
  void testNewProcessCandidateStarterUserRemovedEventImpl() {
    // Arrange and Act
    ProcessCandidateStarterUserRemovedEventImpl actualProcessCandidateStarterUserRemovedEventImpl = new ProcessCandidateStarterUserRemovedEventImpl();

    // Assert
    assertNull(actualProcessCandidateStarterUserRemovedEventImpl.getProcessDefinitionVersion());
    assertNull(actualProcessCandidateStarterUserRemovedEventImpl.getBusinessKey());
    assertNull(actualProcessCandidateStarterUserRemovedEventImpl.getParentProcessInstanceId());
    assertNull(actualProcessCandidateStarterUserRemovedEventImpl.getProcessDefinitionId());
    assertNull(actualProcessCandidateStarterUserRemovedEventImpl.getProcessDefinitionKey());
    assertNull(actualProcessCandidateStarterUserRemovedEventImpl.getProcessInstanceId());
    assertNull(actualProcessCandidateStarterUserRemovedEventImpl.getEntity());
    assertEquals(
        ProcessCandidateStarterUserEvent.ProcessCandidateStarterUserEvents.PROCESS_CANDIDATE_STARTER_USER_REMOVED,
        actualProcessCandidateStarterUserRemovedEventImpl.getEventType());
  }

  /**
   * Test
   * {@link ProcessCandidateStarterUserRemovedEventImpl#ProcessCandidateStarterUserRemovedEventImpl(ProcessCandidateStarterUser)}.
   * <p>
   * Method under test:
   * {@link ProcessCandidateStarterUserRemovedEventImpl#ProcessCandidateStarterUserRemovedEventImpl(ProcessCandidateStarterUser)}
   */
  @Test
  @DisplayName("Test new ProcessCandidateStarterUserRemovedEventImpl(ProcessCandidateStarterUser)")
  void testNewProcessCandidateStarterUserRemovedEventImpl2() {
    // Arrange
    ProcessCandidateStarterUserImpl entity = new ProcessCandidateStarterUserImpl("42", "42");

    // Act
    ProcessCandidateStarterUserRemovedEventImpl actualProcessCandidateStarterUserRemovedEventImpl = new ProcessCandidateStarterUserRemovedEventImpl(
        entity);

    // Assert
    assertNull(actualProcessCandidateStarterUserRemovedEventImpl.getProcessDefinitionVersion());
    assertNull(actualProcessCandidateStarterUserRemovedEventImpl.getBusinessKey());
    assertNull(actualProcessCandidateStarterUserRemovedEventImpl.getParentProcessInstanceId());
    assertNull(actualProcessCandidateStarterUserRemovedEventImpl.getProcessDefinitionId());
    assertNull(actualProcessCandidateStarterUserRemovedEventImpl.getProcessDefinitionKey());
    assertNull(actualProcessCandidateStarterUserRemovedEventImpl.getProcessInstanceId());
    assertEquals(
        ProcessCandidateStarterUserEvent.ProcessCandidateStarterUserEvents.PROCESS_CANDIDATE_STARTER_USER_REMOVED,
        actualProcessCandidateStarterUserRemovedEventImpl.getEventType());
    assertSame(entity, actualProcessCandidateStarterUserRemovedEventImpl.getEntity());
  }

  /**
   * Test {@link ProcessCandidateStarterUserRemovedEventImpl#getEventType()}.
   * <p>
   * Method under test:
   * {@link ProcessCandidateStarterUserRemovedEventImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType()")
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(
        ProcessCandidateStarterUserEvent.ProcessCandidateStarterUserEvents.PROCESS_CANDIDATE_STARTER_USER_REMOVED,
        (new ProcessCandidateStarterUserRemovedEventImpl()).getEventType());
  }
}
